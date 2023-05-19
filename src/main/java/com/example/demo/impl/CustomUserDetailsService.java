package com.example.demo.impl;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepo userRepo;

  public CustomUserDetailsService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByUsername(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Username " + username + " does not exist");
    } else {
      return new CustomUserDetails(user);
    }
  }
}
