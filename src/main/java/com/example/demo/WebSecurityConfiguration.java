package com.example.demo;


import com.example.demo.dto.ExceptionDTO;
import com.example.demo.impl.CustomUserDetailsService;
import com.example.demo.jwt.JwtConfigurer;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.utilities.AuthenticationCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

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
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enable method-level security annotations
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationCheck authenticationCheck;

  private CustomUserDetailsService customUserDetailsService;

  private final JwtTokenProvider jwtTokenProvider;

  public WebSecurityConfiguration(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService customUserDetailsService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().ignoringAntMatchers("/api/auth/sign-in", "/api/auth/sign-up","/api/mail/**","/api/modify-user/**","/home/**","/api/post/**","/api/file/**","/api/dxt/mod/auth/**","/api/dxt/admin/auth/**").and().authorizeRequests()
        .antMatchers("/home/**").permitAll().
        antMatchers("/api/post/**").hasAnyRole("USER","MODERATOR","ADMIN").
        antMatchers("/api/file/**").hasAnyRole("USER","MODERATOR","ADMIN").
        antMatchers("/api/auth/sign-in", "/api/auth/sign-up").permitAll().
        antMatchers("/api/mail/**").permitAll().antMatchers("/api/modify-user/**").permitAll().
        antMatchers("/api/dxt/mod/auth/**").hasAnyRole("ADMIN","MODERATOR").
        antMatchers("/api/dxt/admin/auth/**").hasRole("ADMIN").
        anyRequest().authenticated().and().apply(new JwtConfigurer(jwtTokenProvider)).and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
  }
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, accessDeniedException) -> {
      ExceptionDTO exceptionDTO = new ExceptionDTO();
      exceptionDTO.setTimestamp(new Date().toString());
      exceptionDTO.setError("Access denied");
      exceptionDTO.setUrl(request.getRequestURI());
      exceptionDTO.setStatus(HttpStatus.FORBIDDEN.value());
      exceptionDTO.setDescription("You are not authorized to view this resource.");

      // Set the response status and write the exceptionDTO as the response body
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType("application/json");
      response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionDTO));
    };
  }


  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }

}
