package com.outfit7.fun7.persistence.repository;

import com.outfit7.fun7.model.Role;
import com.outfit7.fun7.model.database.Fun7User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class Fun7UserRepositoryTest {

    @Autowired
    private Fun7UserRepository fun7UserRepository;

    @AfterEach
    void tearDown() {
        fun7UserRepository.deleteAll();
    }

    @Test
    void findUserByEmailExists() {
        // given
        String email = "test@test.com";
        Fun7User user = new Fun7User(
                email,
                "$2y$10$TyrAZj4ZWXi/7d7P/cdVZOxc0shuPNY4GnJwXqtfz2sRmQURLcbMG",
                Role.USER
        );
        fun7UserRepository.save(user);

        // when
        boolean exists = fun7UserRepository.findUserByEmail(email).isPresent();

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void findUserByEmailNotExist() {
        // given
        String email = "test@test.com";

        // when
        boolean exists = fun7UserRepository.findUserByEmail(email).isPresent();

        // then
        assertThat(exists).isFalse();
    }
}