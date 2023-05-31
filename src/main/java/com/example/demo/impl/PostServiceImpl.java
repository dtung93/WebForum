package com.example.demo.impl;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ThreadRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PostServiceImpl implements PostService {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private ThreadRepo threadRepo;

  @Override
  public PostDTO createPost(String username, byte[] content) {
      return null;
  }
}
