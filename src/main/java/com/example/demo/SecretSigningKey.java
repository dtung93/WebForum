package com.example.demo;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
public class SecretSigningKey {
  private String secretKey;

  public String getSecretKey() {
    if (secretKey == null) {
      SecretKey signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
      secretKey = Base64.getEncoder().encodeToString(signingKey.getEncoded());
    }
    return secretKey;
  }
}
