package com.outfit7.fun7.controller.user;

import com.outfit7.fun7.model.dto.request.AvailableServicesRequestDto;
import com.outfit7.fun7.service.user.AvailableServicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/check-services")
@RequiredArgsConstructor
public class AvailableServicesController {
  private final AvailableServicesService availableServicesService;

  @GetMapping
  public ResponseEntity<?> getServicesStatus(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
      @RequestBody AvailableServicesRequestDto request) {
    final String BEARER = "Bearer";
    final String accessToken = authorizationHeader.substring(BEARER.length() + 1);
    return ResponseEntity.ok(availableServicesService.getAvailableServices(accessToken, request));
  }
}
