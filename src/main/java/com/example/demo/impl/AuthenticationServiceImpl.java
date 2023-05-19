package com.example.demo.impl;

import com.example.demo.dto.AuthenticateDTO;
import com.example.demo.dto.SignUpDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.dto.UserStatisticsDTO;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


  @Override
  public String authenticate(String username, String password) throws Exception {
    try {
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return jwtTokenProvider.createToken(authentication,2);
    } catch (AuthenticationException e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public UserInfoDTO signIn(AuthenticateDTO request) throws Exception {
    if (validateInputs(request)) {
      List<User> users = userRepo.findByUsernameOrEmail(request.getUsername());
      if (!users.isEmpty()) {
        ModelMapper modelMapper = new ModelMapper();
        User user = users.get(0);
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
          String accessToken = this.authenticate(user.getUsername(), request.getPassword());
          user.setLoginAttempt(0);
          UserInfoDTO result = modelMapper.map(user, UserInfoDTO.class);
          result.setAccessToken(accessToken);
          return result;
        } else
          user.increaseLoginAttempt();
          userRepo.save(user);
          return null;
      } else
        return null;
    } else
      return null;
  }

  @Override
  public UserInfoDTO signUp(SignUpDTO request) {
      ModelMapper mapper = new ModelMapper();
      String encodedPassword = passwordEncoder.encode(request.getPassword());
      Role role = roleRepo.findById(1);
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
      user.setRemovalFlag(Boolean.FALSE);
      User savedUser = userRepo.save(user);
      UserInfoDTO result = mapper.map(savedUser,UserInfoDTO.class);
      return result;
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
  public boolean checkUserExistByUsername(String username){
    if(!userRepo.userExistUsername(username).equals(0)){
      return true;
    }
    else
      return false;
  }

  @Override
  public boolean checkUserExistByEmail(String email){
    if(!userRepo.userExistEmail(email).equals(0)){
      return true;
    }
    else
      return false;
  }

  //True means less than 6, PASS OK
  @Override
  public boolean checkLoginAttempt(String username) {
    User user = userRepo.findByUsernameOrEmail(username).get(0);
    return user.getLoginAttempt() < 6;
  }
}
