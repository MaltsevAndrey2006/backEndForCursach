package com.dev.backendforcursach.security.jwt;

import com.dev.backendforcursach.model.dto.JwtAuthenticationDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtService {

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expiration}")
  private Integer expiration;

  public JwtAuthenticationDto generateJwtAuthToken(String login) {
    var token = generateJwtToken(login);
    return new JwtAuthenticationDto(token);
  }

  public boolean validateJwtToken(String token) {
    if (StringUtils.isEmpty(token)) {
      throw new RuntimeException("JWT token is null or empty");
    }
    try {
      Jwts.parser()
          .verifyWith(generateKey())
          .build()
          .parseSignedClaims(token)
          .getPayload();
      return Boolean.TRUE;
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired", e);
      throw new RuntimeException("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token", e);
      throw new RuntimeException("Unsupported JWT token");
    } catch (MalformedJwtException e) {
      log.error("Malformed JWT token", e);
      throw new RuntimeException("Malformed JWT token");
    } catch (SecurityException e) {
      log.error("Security exception while validating JWT", e);
      throw new RuntimeException("Security exception");
    } catch (Exception e) {
      log.error("Unexpected error while validating JWT", e);
      throw new RuntimeException("Unexpected error during JWT validation", e);
    }
  }

  public String getLoginFromToken(String token) {
    var claims = Jwts.parser()
        .verifyWith(generateKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
    return claims.getSubject();
  }

  private String generateJwtToken(String login) {
    var expirationDateTime = ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(expiration);
    var date = Date.from(expirationDateTime.toInstant());
    return Jwts.builder()
        .subject(login)
        .expiration(date)
        .signWith(generateKey())
        .compact();
  }

  private SecretKey generateKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}