package com.outfit7.fun7.persistence;

import com.outfit7.fun7.mapper.UserMapper;
import com.outfit7.fun7.persistence.impl.Fun7UserPersistenceServiceImpl;
import com.outfit7.fun7.persistence.repository.Fun7UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
class Fun7UserPersistenceServiceTest {

    @Mock
    private Fun7UserRepository fun7UserRepository;
    @Mock
    private UserMapper userMapper;
    private Fun7UserPersistenceService fun7UserPersistenceService;

    private final String errorNotFound = "not found.";

    @ExtendWith(MockitoExtension.class)
    @BeforeEach
    void setUp() {
        fun7UserPersistenceService = new Fun7UserPersistenceServiceImpl(fun7UserRepository, userMapper);
    }

    @Test
    void deleteFun7User() {
        // given
        UUID userUUID = UUID.randomUUID();
        given(fun7UserRepository.existsById(userUUID)).willReturn(true);

        // when
        fun7UserPersistenceService.deleteFun7User(userUUID);

        // then
        ArgumentCaptor<UUID> fun7UserUUIDArgumentCaptor = ArgumentCaptor.forClass(UUID.class);
       verify(fun7UserRepository).deleteById(fun7UserUUIDArgumentCaptor.capture());
       UUID capturedFun7UserUUID  = fun7UserUUIDArgumentCaptor.getValue();
       assertThat(capturedFun7UserUUID).isEqualTo(userUUID);
    }

    @Test
    void throwWhenEmailTakenForDeletion() {
        // given
        UUID userUUID = UUID.randomUUID();

        given(fun7UserRepository.existsById(userUUID)).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> fun7UserPersistenceService.deleteFun7User(userUUID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageEndingWith(errorNotFound);
        verify(fun7UserRepository, never()).deleteById(any());
    }


    @Test
    void throwWhenByIdNotFoundForF7UserPS() {
        // given
        UUID userUUID = UUID.randomUUID();

        given(fun7UserRepository.findById(userUUID)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> fun7UserPersistenceService.getFun7UserByUUID(userUUID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageEndingWith(errorNotFound);
    }

    @Test
    void throwWhenByEmailNotFoundForF7UserPS() {
        // given
        String email = "test@test";

        given(fun7UserRepository.findUserByEmail(email)).willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> fun7UserPersistenceService.getFun7UserByEmail(email))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageEndingWith(errorNotFound);
    }

}