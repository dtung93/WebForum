package com.example.demo.impl;

import com.example.demo.dto.*;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.MailService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private RoleRepo roleRepo;

  @Autowired
  private MailService mailService;

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
  private static final String authenticationError = "authenticationError";
  private static final String invalidCredentials = "invalidCredentials";

  @Override
  public String authenticate(String username, String password) throws Exception {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return jwtTokenProvider.createToken(authentication);
    } catch (AuthenticationException e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Map<String, Object> signIn(AuthenticateDTO request) throws Exception {
    Map<String, Object> response = new HashMap<>();
    if (validateInputs(request)) {
      List<User> users = userRepo.findByUsernameOrEmail(request.getUsername());
      if (!users.isEmpty()) {
        ModelMapper modelMapper = new ModelMapper();
        User user = users.get(0);
        //Login attempt >=5 => Not allowed to sign in.
        if (user.getLoginAttempt() >= 5) {
          response.put(authenticationError, "You have exceeded your login attempt! Your account is now locked.");
          return response;
        }
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
          String accessToken = this.authenticate(user.getUsername(), request.getPassword());
          user.setLoginAttempt(0);
          userRepo.save(user);
          UserInfoDTO result = modelMapper.map(user, UserInfoDTO.class);
          result.setAccessToken(accessToken);
          response.put("userInfo", result);
          return response;
        } else
          user.increaseLoginAttempt();
        userRepo.save(user);
        response.put(authenticationError, "Incorrect username or password! You have" + " " + (5 - user.getLoginAttempt()) + " attempts more");
        return response;
      } else
        response.put(authenticationError, "User not found!");
      return response;
    } else
      response.put(authenticationError, "Username and password must not be empty");
    return response;
  }

  @Override
  public Map<String, Object> signUp(SignUpDTO request) throws Exception {
    Map<String, Object> response = new HashMap<>();
    if (Objects.isNull(request.getUsername()) || StringUtils.containsWhitespace(request.getUsername()) ||
        Objects.isNull(request.getEmail()) || StringUtils.containsWhitespace(request.getEmail()) ||
        Objects.isNull(request.getPassword()) || StringUtils.containsWhitespace(request.getPassword())) {
      response.put(invalidCredentials, "Username, email and password must not be empty");
      return response;
    }
    if (this.checkUserExistByUsername(request.getUsername())) {
      response.put(invalidCredentials, "Username already exists!");
      return response;
    }
    if (this.checkUserExistByEmail(request.getEmail())) {
      response.put(invalidCredentials, "Email already exists!");
      return response;
    }

    ModelMapper mapper = new ModelMapper();
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    Role role = roleRepo.findById(1L).orElse(null);
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(encodedPassword);
    user.getRoles().add(role);
    user.setBadge("New Member");
    user.setEmail(request.getEmail());
    user.setLoginAttempt(0);
    user.setPhone("PhoneNumber");
    user.setAddress("Location");
    user.setCreatedBy("ADMIN");
    user.setCreatedDate(new Date());
    user.setUpdatedBy("ADMIN");
    user.setUpdatedDate(new Date());
    user.setActivateToken(jwtTokenProvider.generateToken(1));
    user.setRemovalFlag(Boolean.FALSE);
    User savedUser = userRepo.save(user);
    UserInfoDTO result = mapper.map(savedUser, UserInfoDTO.class);
    response.put("userInfo", result);
    EmailDTO emailDTO = new EmailDTO();
    emailDTO.setEmail(user.getEmail());
    emailDTO.setUsername(user.getUsername());
    emailDTO.setToken(user.getActivateToken());
    mailService.sendConfirmationEmail(emailDTO);
    return response;
  }

  @Override
  public UserStatisticsDTO getUserStatistics(String username) {
    return null;
  }

  private boolean validateInputs(AuthenticateDTO request) {
    if (Objects.isNull(request.getUsername()) || StringUtils.containsWhitespace(request.getUsername()) ||
        Objects.isNull(request.getPassword()) || StringUtils.containsWhitespace(request.getPassword())) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean checkUserExistByUsername(String username) {
    if (!userRepo.userExistUsername(username).equals(0)) {
      return true;
    } else
      return false;
  }

  @Override
  public boolean checkUserExistByEmail(String email) {
    if (!userRepo.userExistEmail(email).equals(0)) {
      return true;
    } else
      return false;
  }

  //Check token expiration, type 1 is confirmation, 2 is changePassword
  @Override
  public boolean verifyConfirmAccount(String token) throws Exception {
    try {
      User user = userRepo.findByActivateToken(token);
      if (Objects.nonNull(user)) {
        boolean isTokenExpired = jwtTokenProvider.checkIsTokenExpired(user.getActivateToken());
        if (Boolean.FALSE.equals(isTokenExpired)) {
          user.setActivateToken(null);
          userRepo.save(user);
          return true;
        }
        else
          return false;
      } else
        return false;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }


  @Override
  public boolean verifyResetPassword(String token, String newPassword) throws Exception {
    try {
      User user = userRepo.findByChangePasswordToken(token);
      if (Objects.nonNull(user)) {
        boolean isTokenExpired = jwtTokenProvider.checkIsTokenExpired(user.getChangePasswordToken());
        if(Boolean.FALSE.equals(isTokenExpired)){
          user.setChangePasswordToken(null);
          String encodedPassword = passwordEncoder.encode(newPassword);
          user.setPassword(encodedPassword);
          userRepo.save(user);
          return true;
        }
        else
          return false;
      } else
        return false;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }

  }
}
