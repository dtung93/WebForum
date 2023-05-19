package com.example.demo;


import com.example.demo.impl.CustomUserDetailsService;
import com.example.demo.jwt.JwtConfigurer;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.utilities.AuthenticationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@EnableWebSecurity
@Configuration
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
  public AuthenticationManager authenticationManager () throws Exception{
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
      http.csrf().ignoringAntMatchers("/api/auth/sign-in","/api/auth/sign-up").and().authorizeRequests()
          .antMatchers("/","/home").permitAll()
              .antMatchers("/api/auth/sign-in","/api/auth/sign-up").permitAll().
          anyRequest().authenticated().and().apply(new JwtConfigurer(jwtTokenProvider));
  }

  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
  }
}
