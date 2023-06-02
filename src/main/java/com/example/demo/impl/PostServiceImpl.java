package com.example.demo.impl;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.model.Thread;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ThreadRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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

  private static final String ERROR = "Invalid Credentials";
  private static final String INVALID_THREAD = "Invalid thread not found!";

  @Override
  public List<PostDTO> getPostByThread(Long threadId) {
    List<Post> listPosts = postRepo.getPostByThreadId(threadId);
    ModelMapper mapper = new ModelMapper();
    List<PostDTO> result = listPosts.stream().map(post -> mapper.map(post,PostDTO.class)).collect(Collectors.toList());
    return result;
  }

  @Override
  public Map<String, Object> comment(String username, Long threadId, String content) {
    Map<String, Object> result = new HashMap<>();
    if (Objects.isNull(username) || Objects.isNull(content) || Objects.isNull(threadId) || StringUtils.containsWhitespace(content)) {
      result.put(ERROR, "Username and content must not be null");
      return result;
    }
    User user = userRepo.findByUsername(username);
    if (Objects.nonNull(user)) {
      Thread thread = threadRepo.findById(threadId).orElse(null);
      if (Objects.nonNull(thread)) {
        Post post = new Post();
        post.setUser(user);
        post.setThread(thread);
        post.setRemovalFlag(false);
        post.setCreatedBy(user.getUsername());
        post.setUpdatedDate(new Date());
        post.setCreatedDate(new Date());
        post.setUpdatedBy(user.getUsername());
        post.setContent(content);
        Post savedPost = postRepo.save(post);
        if (Objects.nonNull(savedPost)) {
          result.put("New comment", savedPost);
          return result;
        }
        result.put("Exception when saving post", "Could not perform query operation");
      } else
        result.put(INVALID_THREAD, "Invalid thread Id");
      return result;
    } else
      result.put(ERROR, "Invalid username!");
    return result;
  }
}
