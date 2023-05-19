package com.example.demo.utilities;

import com.example.demo.model.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Component
public class AuthenticationCheck implements org.springframework.security.web.AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    CustomException exception = new CustomException();
    exception.setStatus(HttpStatus.UNAUTHORIZED.value());
    exception.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
    exception.setUrl(request.getRequestURI());
    exception.setDescription("You are not authorized to access this resource");
    exception.setTimestamp(new Date().toString());
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getOutputStream(), exception);
  }
}
