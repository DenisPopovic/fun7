package com.outfit7.fun7.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvailableServicesRequestDto {
  private String timezone;
  private String userId;
  private String cc;
}
