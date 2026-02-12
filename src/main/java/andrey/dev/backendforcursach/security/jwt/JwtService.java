package andrey.dev.backendforcursach.security.jwt;

import andrey.dev.backendforcursach.dto.JwtAuthenticationDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.token.secret}")
    private String secret;

    public JwtAuthenticationDto generateJwtAuthToken(String login) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(generateJwtToken(login));
        return jwtAuthenticationDto;
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT token");
        } catch (SecurityException e) {
            throw new RuntimeException("Security exception");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    private String generateJwtToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusMinutes(50).atZone(ZoneId.systemDefault()).toInstant());
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