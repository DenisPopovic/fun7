package com.outfit7.fun7.service.impl.auth;

import com.outfit7.fun7.model.dto.request.AuthenticationRequestDto;
import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.persistence.UserInfoPersistenceService;
import com.outfit7.fun7.service.auth.AuthenticationService;
import com.outfit7.fun7.util.AuthUtils;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final Fun7UserPersistenceService fun7UserPersistenceService;
  private final UserInfoPersistenceService userInfoPersistenceService;
  private final AuthUtils authUtils;
  private final AuthenticationManager authenticationManager;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public String authenticateUser(AuthenticationRequestDto authenticationRequestDto)
      throws AccessDeniedException {
    try {
      String userEmail = authenticationRequestDto.getEmail();

      UserDetails userDetails = fun7UserPersistenceService.getUserByEmail(userEmail);
      if (userDetails != null) {
        final boolean passMatch =
            bCryptPasswordEncoder.matches(
                authenticationRequestDto.getPassword(), userDetails.getPassword());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationRequestDto.getEmail(), authenticationRequestDto.getPassword()));
        if (passMatch) {
          userInfoPersistenceService.updateUserInfo(userEmail);
          return authUtils.generateToken(userDetails);
        }
      }
    } catch (Exception e) {
      throw new AccessDeniedException(e.getMessage());
    }
    throw new AccessDeniedException(
        "User with email '%s' not found.".formatted(authenticationRequestDto.getEmail()));
  }
}
