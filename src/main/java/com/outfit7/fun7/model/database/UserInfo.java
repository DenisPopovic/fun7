package com.outfit7.fun7.model.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fun7_user_info")
public class UserInfo extends Fun7DatabaseModel {

  @Column(name = "time_zone", nullable = false)
  private String timeZone;

  @Column(name = "country_code", nullable = false)
  private String countryCode;

  @Column(name = "login_count", nullable = false)
  private long loginCount;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(
      name = "uuid_fun7_user",
      nullable = false,
      unique = true,
      foreignKey = @ForeignKey(name = "fk_protege_imports_uuid_fun7_user"))
  private Fun7User fun7User;

  public UserInfo(String timeZone, String countryCode, Fun7User fun7User) {
    this.timeZone = timeZone;
    this.countryCode = countryCode;
    this.fun7User = fun7User;
  }

  public UserInfo(String timeZone, String countryCode, long loginCount, Fun7User fun7User) {
    this.timeZone = timeZone;
    this.countryCode = countryCode;
    this.loginCount = loginCount;
    this.fun7User = fun7User;
  }
}
