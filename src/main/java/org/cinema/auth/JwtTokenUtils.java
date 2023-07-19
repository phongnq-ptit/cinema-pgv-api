package org.cinema.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.crypto.spec.SecretKeySpec;
import org.cinema.models.dto.UserDto;
import org.cinema.models.enums.UserRole;
import org.springframework.beans.factory.annotation.Value;

@ApplicationScoped
public class JwtTokenUtils {

  public static final String ID = "uuid";
  public static final String NAME = "email";
  public static final String ROLE = "role";

  private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final Key key;
  private final String issuer;
  private final String secret;

  public JwtTokenUtils(@Value(value = "${jwt-token-key}") String key,
      @Value(value = "${jwt-token-issuer}") String issuer) {
    byte[] secretBytes = DatatypeConverter.parseBase64Binary(key);
    this.key = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
    this.issuer = issuer;
    this.secret = key;
  }

  public String generateToken(UserDto user, long expiredTime) {
    LocalDateTime now = LocalDateTime.now();
    return Jwts.builder()
        .claim(ID, user.getUuid().toString())
        .claim(NAME, user.getEmail())
        .claim(ROLE, user.getRole())
        .setIssuedAt(convertLocalDateTimeToDate(now))
        .signWith(signatureAlgorithm, this.key)
        .setExpiration(convertLocalDateTimeToDate(now.plusMinutes(expiredTime)))
        .compact();
  }

  public UserDto verifyToken(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
        .parseClaimsJws(token).getBody();

    return UserDto.builder()
        .uuid(UUID.fromString(claims.get(ID).toString()))
        .email(claims.get(NAME).toString())
        .role(UserRole.valueOf(claims.get(ROLE).toString()))
        .build();
  }

  private Date convertLocalDateTimeToDate(LocalDateTime now) {
    return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
  }
}
