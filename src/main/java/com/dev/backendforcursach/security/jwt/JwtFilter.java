package com.dev.backendforcursach.security.jwt;

import com.dev.backendforcursach.exception.InvalidCredentialsException;
import com.dev.backendforcursach.security.CustomUserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final CustomUserServiceImpl customUserService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    var token = getTokenFromRequest(request);
    if (jwtService.validateJwtToken(token)) {
      setCustomUserDetailsToSecurityContextHolder(token);
    }
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    var bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    throw new InvalidCredentialsException("Headers to auth is empty ");
  }

  private void setCustomUserDetailsToSecurityContextHolder(String token) {
    var login = jwtService.getLoginFromToken(token);
    var customUserDetails = customUserService.loadUserByUsername(login);

    var authenticationToken =
        new UsernamePasswordAuthenticationToken(customUserDetails,
            null, customUserDetails.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }
}
