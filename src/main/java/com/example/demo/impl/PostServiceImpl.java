package com.example.demo.impl;

import com.example.demo.dto.PostDTO;
import com.example.demo.model.Post;
import com.example.demo.model.Thread;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ThreadRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PostService;
import com.example.demo.utilities.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
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

  @Autowired
  private Utils utils;

  private static final String ERROR = "Invalid Credentials";
  private static final String INVALID_THREAD = "Invalid thread not found!";
  ModelMapper mapper = new ModelMapper();

  @Override
  public Map<String,Object> getPostByThread(Long threadId, int pageNumber, int pageSize) {
    int offset = pageNumber * pageSize;
    Map<String,Object> output = new HashMap<>();
    Pageable pageable = new PageDataOffset(offset,pageSize, Sort.by("createdDate").ascending());
    Page<Post> listPost = postRepo.getPostsByThreadId(threadId,pageable);
    Page<PostDTO> posts = listPost.map(post -> {
      PostDTO postDTO =  mapper.map(post,PostDTO.class);
      postDTO.setAuthor(post.getCreatedBy());
      return postDTO;
        }
    );
    return utils.getPage(posts);
  }

  @Override
  @Transactional
  public Map<String, Object> comment(String username, Long threadId, String content) {
    Map<String, Object> result = new HashMap<>();
    if (Objects.isNull(username) || Objects.isNull(content) || Objects.isNull(threadId) || content.isEmpty()) {
      result.put(ERROR, "Username and content must not be null");
      return result;
    }
    User user = userRepo.findByUsername(username);
    if (Objects.nonNull(user)) {
      Optional<Thread> threadOptional = threadRepo.findById(threadId);
      Thread thread = threadOptional.orElse(null);
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
          PostDTO output = mapper.map(savedPost,PostDTO.class);
          output.setAuthor(savedPost.getCreatedBy());
          result.put("New comment", output);
          return result;
        }
        result.put("Invalid operation", "Could not perform save post operation");
        return result;
      }
      result.put(ERROR, "Invalid thread Id!");
      return result;

    } else
      result.put(ERROR, "Invalid username!");
    return result;
  }
}
