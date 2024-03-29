package com.example.demo.jwt;

import com.example.demo.SecretSigningKey;
import com.example.demo.impl.CustomUserDetails;
import com.example.demo.model.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.*;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

  @Value("${jwt.secret.key}")
  private String secretKey;


  public String createToken(Authentication authentication) throws Exception {
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
    List<String> roles = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    Instant currentTime = Instant.now();
    Instant expiredInMinutes = currentTime.plus(Duration.ofMinutes(60 * 24));
    JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();
    jwtBuilder.subject(user.getUsername());
    jwtBuilder.claim("roles", roles);
    jwtBuilder.issueTime(Date.from(currentTime));
    jwtBuilder.expirationTime(Date.from(expiredInMinutes));

    JWSSigner signer;
    try {
      signer = new MACSigner(secretKey);
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtBuilder.build());
    try {
      signedJWT.sign(signer);
    } catch (JOSEException e) {
      throw new Exception(e.getMessage());
    }
    return signedJWT.serialize();
  }

  public String generateToken(int type) throws Exception {
    Instant currentTime = Instant.now();
    Instant expiredInMinutes = currentTime.plus(Duration.ofMinutes(type == 1 ? (60 * 24 * 3) : (type == 2 ? (60 * 24) : 60)));
    JWTClaimsSet.Builder jwtBuilder = new JWTClaimsSet.Builder();
    switch (type) {
      case 1:
        jwtBuilder.claim("type", "confirmation");
        break;
      case 2:
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
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), jwtBuilder.build());
    try {
      signedJWT.sign(signer);
    } catch (JOSEException e) {
      throw new Exception(e.getMessage());
    }
    return signedJWT.serialize();
  }

  public Authentication getAuthentication(String token) throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

    String username = claimsSet.getSubject();

    List<String> roles = new ArrayList<>();
    roles = (List<String>) claimsSet.getClaim("roles");
    List<GrantedAuthority> authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    return new UsernamePasswordAuthenticationToken(username, "", authorities);
  }

  public boolean validateToken(String token) {
    try {
      boolean isExpired = this.checkIsTokenExpired(token);
      if (Boolean.FALSE.equals(isExpired)) {
        return true;
      } else
        return false;
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

  //Return true if token is expired, false if token is still valid, otherwise will throw error jwt expiration
  public boolean checkIsTokenExpired(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
      Date expirationDate = claimsSet.getExpirationTime();
      Date currentDate = new Date();
      if (Objects.nonNull(expirationDate) && currentDate.before(expirationDate)) {
        JWSVerifier verifier = new MACVerifier(secretKey);
        if (Boolean.TRUE.equals(signedJWT.verify(verifier))) {
          //Token is still VALID(OK-PASS)
          return false;
        }
      }
      //Token is INVALID-EXPIRED
      return true;
    } catch (Exception e) {
      throw new JwtException("Invalid token", e);
    }
  }
}

