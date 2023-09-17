package com.outfit7.fun7.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvailableServicesResponseDto {

  private String multiplayer;
  private String userSupport;
  private String ads;

  public AvailableServicesResponseDto(String multiplayer, String userSupport, String ads) {
    this.multiplayer = multiplayer;
    this.userSupport = userSupport;
    this.ads = ads;
  }

  @Override
  public String toString() {
    return "AvailableServicesResponseDto{"
        + "multiplayer="
        + multiplayer
        + ", userSupport="
        + userSupport
        + ", ads="
        + ads
        + '}';
  }
}
