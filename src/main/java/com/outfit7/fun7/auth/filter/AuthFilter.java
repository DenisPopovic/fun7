package com.outfit7.fun7.auth.filter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.util.AuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

  private final Fun7UserPersistenceService fun7UserPersistenceService;
  private final AuthUtils authUtils;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    final String header = request.getHeader(AUTHORIZATION);
    final String email;
    final String accessToken;
    final String BEARER = "Bearer";

    if (header == null || !header.startsWith(BEARER)) {
      filterChain.doFilter(request, response);
      return;
    }

    accessToken = header.substring(BEARER.length() + 1);
    email = authUtils.extractEmail(accessToken);

    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = fun7UserPersistenceService.getUserByEmail(email);

      if (authUtils.validateToken(accessToken, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
