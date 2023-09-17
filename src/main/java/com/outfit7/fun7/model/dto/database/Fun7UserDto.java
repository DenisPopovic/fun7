package com.outfit7.fun7.model.dto.database;

import com.outfit7.fun7.model.Role;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fun7UserDto {
  private UUID uuid;
  private String email;
  private Role role;

  public Fun7UserDto(UUID uuid, String email, Role role) {
    this.uuid = uuid;
    this.email = email;
    this.role = role;
  }
}
