package com.outfit7.fun7.persistence.repository;

import com.outfit7.fun7.model.Role;
import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.model.database.UserInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private Fun7UserRepository fun7UserRepository;

    @AfterEach
    void tearDown() {
        userInfoRepository.deleteAll();
        fun7UserRepository.deleteAll();
    }

    private Fun7User createUserAndUserInfo() {
        Fun7User user = new Fun7User(
                "test@test.com",
                "$2y$10$TyrAZj4ZWXi/7d7P/cdVZOxc0shuPNY4GnJwXqtfz2sRmQURLcbMG",
                Role.USER
        );
        fun7UserRepository.save(user);

        UserInfo userInfo = new UserInfo(
                "CEST",
                "SL",
                0,
                user

        );
        userInfoRepository.save(userInfo);

        return user;
    }

    @Test
    void findUserInfoByUserUUIDExists() {
        // given
        Fun7User user = createUserAndUserInfo();

        // when
        boolean exists = userInfoRepository.findUserInfoByUserUUID(user.getUuid()).isPresent();

        // then
        assertThat(exists).isTrue();
    }


    @Test
    void findUserInfoByUserEmailExists() {
        // given
        Fun7User user = createUserAndUserInfo();

        // when
        boolean exists = userInfoRepository.findUserInfoByUserEmail(user.getEmail()).isPresent();

        // then
        assertThat(exists).isTrue();
    }


}