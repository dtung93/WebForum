package com.example.demo.service;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import org.springframework.data.domain.Pageable;
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
  PostDTO editPost(Long postId, Post post);

  boolean removePost(Long postId);

  Map<String, Object> getPostByThread(Long threadId, int pageNumber, int pageSize);

  Map<String, Object> comment(String username, Long threadId, String content);
}
