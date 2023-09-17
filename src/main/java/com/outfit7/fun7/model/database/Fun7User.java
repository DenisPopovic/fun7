package com.outfit7.fun7.model.database;

import com.outfit7.fun7.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fun7_user")
public class Fun7User extends Fun7DatabaseModel {

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "hashed_password", nullable = false)
  private String password;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  public Fun7User(String email, String password, Role role) {
    this.email = email;
    this.password = password;
    this.role = role;
  }

  @Override
  public String toString() {
    return "Fun7User{"
        + "userName='"
        + email
        + '\''
        + ", password='"
        + password
        + '\''
        + ", role="
        + role
        + '}';
  }
}
