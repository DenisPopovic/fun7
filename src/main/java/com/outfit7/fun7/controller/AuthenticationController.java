package com.outfit7.fun7.controller;

import com.outfit7.fun7.model.dto.request.AuthenticationRequestDto;
import com.outfit7.fun7.service.auth.AuthenticationService;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  @PostMapping("/authenticate")
  public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequestDto request) {

    try {
      String token = authenticationService.authenticateUser(request);
      return ResponseEntity.ok(token);
    } catch (AccessDeniedException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
  }
}
