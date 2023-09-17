package com.outfit7.fun7.service.auth;

import com.outfit7.fun7.model.dto.request.AuthenticationRequestDto;
import java.nio.file.AccessDeniedException;

public interface AuthenticationService {
  String authenticateUser(AuthenticationRequestDto authenticationRequestDto)
      throws AccessDeniedException;
}
