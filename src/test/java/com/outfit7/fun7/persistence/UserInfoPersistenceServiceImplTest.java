package com.outfit7.fun7.persistence;

import com.outfit7.fun7.model.Role;
import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.model.database.UserInfo;
import com.outfit7.fun7.persistence.impl.UserInfoPersistenceServiceImpl;
import com.outfit7.fun7.persistence.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
class UserInfoPersistenceServiceImplTest {

    @Mock
    private UserInfoRepository userInfoRepository;
    private UserInfoPersistenceService userInfoPersistenceService;

    @ExtendWith(MockitoExtension.class)
    @BeforeEach
    void setUp() {
        userInfoPersistenceService = new UserInfoPersistenceServiceImpl(userInfoRepository);
    }

    @Test
    void getLoginCount() {
        // given
        String email = "test@test.com";
        Fun7User user = new Fun7User(
                email,
                "$2y$10$TyrAZj4ZWXi/7d7P/cdVZOxc0shuPNY4GnJwXqtfz2sRmQURLcbMG",
                Role.USER
        );
        UserInfo userInfo = new UserInfo(
                "CEST",
                "SL",
                6,
                user

        );
        given(userInfoRepository.findUserInfoByUserEmail(email)).willReturn(Optional.of(userInfo));

        // when
        long count = userInfoPersistenceService.getLoginCount(email);

        // then
        assertThat(count).isEqualTo(userInfo.getLoginCount());
    }

    @Test
    void updateUserInfo() {
        // given
        String email = "test@test.com";
        long loginCount = 6L;
        Fun7User user = new Fun7User(
                email,
                "$2y$10$TyrAZj4ZWXi/7d7P/cdVZOxc0shuPNY4GnJwXqtfz2sRmQURLcbMG",
                Role.USER
        );
        UserInfo userInfo = new UserInfo(
                "CEST",
                "SL",
                loginCount,
                user

        );
        given(userInfoRepository.findUserInfoByUserEmail(email)).willReturn(Optional.of(userInfo));

        // when
        userInfoPersistenceService.updateUserInfo(email);

        // then
        ArgumentCaptor<UserInfo> userInfoArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);
        verify(userInfoRepository).save(userInfoArgumentCaptor.capture());
        long capturedCount = userInfoArgumentCaptor.getValue().getLoginCount();
        assertThat(loginCount +1).isEqualTo(capturedCount);
    }

    @Test
    void throwWhenByEmailNotFoundForUserInfoPS() {
        // given
        String email = "test@test";
        String startOfError = "User Info for user with email";

        given(userInfoRepository.findUserInfoByUserEmail(email)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> userInfoPersistenceService.getLoginCount(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageStartingWith(startOfError);

        assertThatThrownBy(() -> userInfoPersistenceService.updateUserInfo(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageStartingWith(startOfError);
    }

}