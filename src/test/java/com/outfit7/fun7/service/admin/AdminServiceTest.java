package com.outfit7.fun7.service.admin;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.outfit7.fun7.mapper.Fun7UserDtoMapper;
import com.outfit7.fun7.model.Role;
import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.service.impl.admin.AdminServiceImpl;
import com.outfit7.fun7.util.AuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class AdminServiceTest {

  @Mock public Fun7UserPersistenceService fun7UserPersistenceService;
  @Mock public Fun7UserDtoMapper fun7UserDtoMapper;
  @Mock private AuthUtils authUtils;

  private AdminService adminService;

  private Fun7User adminUser;
  private final String token = "token";

  @ExtendWith(MockitoExtension.class)
  @BeforeEach
  void setUp() {
    adminService = new AdminServiceImpl(fun7UserPersistenceService, fun7UserDtoMapper, authUtils);
  }

  @BeforeEach
  public void init() {
    adminUser = new Fun7User("test@test.com", "pass", Role.ADMIN);
  }
  @Test
  void deleteUserCaller() {
    // given
    String email = adminUser.getEmail();
    given(authUtils.extractEmail(token)).willReturn(email);
    given(fun7UserPersistenceService.getFun7UserByEmail(email)).willReturn(adminUser);

    // when
    // then
    assertThatThrownBy(() -> adminService.deleteUser(token, adminUser.getUuid())).isInstanceOf(IllegalArgumentException.class);
  }
}
