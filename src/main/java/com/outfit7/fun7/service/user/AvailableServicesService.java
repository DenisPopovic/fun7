package com.outfit7.fun7.service.user;

import com.outfit7.fun7.model.dto.request.AvailableServicesRequestDto;
import com.outfit7.fun7.model.dto.response.AvailableServicesResponseDto;

public interface AvailableServicesService {
    AvailableServicesResponseDto getAvailableServices(String token, AvailableServicesRequestDto availableServicesRequestDto);
}
