package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface PostService {
  List<PostDTO> getPostByThread(Long threadId);
  Map<String, Object> comment(String username, Long threadId, String content);
}
