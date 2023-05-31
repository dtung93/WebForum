package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface PostService {
  PostDTO createPost(String username, byte[]content);
}
