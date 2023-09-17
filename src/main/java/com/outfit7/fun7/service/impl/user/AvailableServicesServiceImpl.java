package com.outfit7.fun7.service.impl.user;

import com.neovisionaries.i18n.CountryCode;
import com.outfit7.fun7.api.ads.AdsService;
import com.outfit7.fun7.model.ActivisionStatus;
import com.outfit7.fun7.model.dto.request.AvailableServicesRequestDto;
import com.outfit7.fun7.model.dto.response.AvailableServicesResponseDto;
import com.outfit7.fun7.persistence.UserInfoPersistenceService;
import com.outfit7.fun7.service.user.AvailableServicesService;
import com.outfit7.fun7.util.AuthUtils;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvailableServicesServiceImpl implements AvailableServicesService {

  private final AdsService adsService;
  private final AuthUtils authUtils;
  private final UserInfoPersistenceService userInfoPersistenceService;

  @Override
  public AvailableServicesResponseDto getAvailableServices(
      String token, AvailableServicesRequestDto availableServicesRequestDto) {
    ActivisionStatus multiplayerAvailable =
        isMultiplayerAvailable(token) ? ActivisionStatus.ENABLED : ActivisionStatus.DISABLED;
    ActivisionStatus supportAvailable =
        isSupportAvailable() ? ActivisionStatus.ENABLED : ActivisionStatus.DISABLED;

    ActivisionStatus adsAvailable = ActivisionStatus.DISABLED;
    CountryCode countryCode = CountryCode.getByAlpha2Code(availableServicesRequestDto.getCc());
    if (countryCode != null) {
      adsAvailable =
          adsService.adsEnabled(countryCode.getNumeric())
              ? ActivisionStatus.ENABLED
              : ActivisionStatus.DISABLED;
    }

    return new AvailableServicesResponseDto(
        multiplayerAvailable.getState(), supportAvailable.getState(), adsAvailable.getState());
  }

  private boolean isSupportAvailable() {

    // Get current time in Ljubljana
    ZoneId ljubljanaZone = ZoneId.of("Europe/Ljubljana");
    ZonedDateTime ljubljanaTime = ZonedDateTime.now(ljubljanaZone);

    // Working days
    DayOfWeek dayOfWeek = ljubljanaTime.getDayOfWeek();
    boolean isWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;

    final LocalTime supportStartTime = LocalTime.of(9, 0);
    final LocalTime supportEndTime = LocalTime.of(15, 0);

    LocalTime currentTime = ljubljanaTime.toLocalTime();

    return isWeekday
        && currentTime.isAfter(supportStartTime)
        && currentTime.isBefore(supportEndTime);
  }

  private boolean isMultiplayerAvailable(String token) {
    String email = authUtils.extractEmail(token);
    long loginCount = userInfoPersistenceService.getLoginCount(email);

    return loginCount > 5;
  }
}
