package com.example.demo.jwt;

import com.example.demo.SecretSigningKey;
import com.example.demo.impl.CustomUserDetails;
import com.example.demo.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.Instant;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Component
public class JwtTokenProvider {
  SecretSigningKey signingKey = new SecretSigningKey();
  String secretKey = signingKey.getSecretKey();

  //type 1 is activation, 3 days
  //type 2 is authentication, 1 day
  //type 3 is change_email, 1 hour
  public String createToken(Authentication authentication, int type) throws Exception {
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    List<String> roles = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    Instant currentTime = Instant.now();
    Instant expiredInMinutes = currentTime.plus(Duration.ofMinutes(type == 1 ? (60 * 24 * 3) : (type == 2 ? (60 * 24) : 60)));
    JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();
    jwtBuilder.subject(user.getUsername());
    switch (type) {
      case 1:
        jwtBuilder.claim("type", "confirmation");
        break;
      case 2:
        jwtBuilder.claim("roles", roles);
        break;
      case 3:
        jwtBuilder.claim("type", "change email");
        break;
      default:
        break;
    }
    jwtBuilder.issueTime(Date.from(currentTime));
    jwtBuilder.expirationTime(Date.from(expiredInMinutes));

    JWSSigner signer;
    try {
      signer = new MACSigner(secretKey);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), jwtBuilder.build());
    try {
      signedJWT.sign(signer);
    } catch (JOSEException e) {
      throw new Exception(e.getMessage());
    }
    return signedJWT.serialize();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody();

    String username = claims.getSubject();

    List<GrantedAuthority> authorities = new ArrayList<>();
    return new UsernamePasswordAuthenticationToken(username, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtException("Invalid token");
    }
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }
}
