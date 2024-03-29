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
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
  ModelMapper mapper = new ModelMapper();

  @Override
  @Transactional
  public PostDTO editPost(Long postId, Post postRequest) {
    Post post = postRepo.getById(postId);
    if (Objects.nonNull(postRequest)) {
      post.setContent(postRequest.getContent());
      post.setPostImages(postRequest.getPostImages());
      post.setPostVideos(postRequest.getPostVideos());
      post.setUpdatedBy(utils.getCurrentUsername());
      post.setUpdatedDate(new Date());
      Post savedPost = postRepo.save(post);
      if (Objects.nonNull(savedPost)) {
        return mapper.map(savedPost, PostDTO.class);
      }
    } else
      return null;
    return null;
  }

  @Override
  @Transactional
  public boolean removePost(Long postId) {
    Post post = postRepo.getById(postId);
    Thread thread = postRepo.getThreadIdByPost(postId);
    thread.setPostCount(thread.getPostCount() - 1);
    post.setRemovalFlag(true);
    Post savedPost = postRepo.save(post);
    Thread savedThread = threadRepo.save(thread);
    if (Objects.nonNull(savedPost) && Objects.nonNull(savedThread)) {
      return true;
    } else
      return false;
  }

  @Override
  public Map<String, Object> getPostByThread(Long threadId, int pageNumber, int pageSize) {
    String cacheKey = "posts_threadId_" + threadId + "_pageNumber_" + pageNumber + "_pageSize_" + pageSize;
    var cacheResult = utils.getCacheValue(cacheKey);
    if (Objects.nonNull(cacheResult)) {
      return cacheResult;
    }
    int offset = pageNumber * pageSize;
    Pageable pageable = new PageDataOffset(offset, pageSize, Sort.by("createdDate").ascending());
    Page<Post> listPost = postRepo.getPostsByThreadId(threadId, pageable);
    List<String> usernames = new ArrayList<>();
    for (Post post : listPost.getContent()) {
      usernames.add(post.getCreatedBy());
    }
    List<Map<String, Object>> userRoles = userRepo.getUserRoles(usernames);
    Page<PostDTO> posts = listPost.map(post -> {
          PostDTO postDTO = mapper.map(post, PostDTO.class);
          postDTO.setAuthor(post.getCreatedBy());
          for(var userRole: userRoles){
            if(userRole.get("user_name").equals(post.getCreatedBy())){
              postDTO.setUserRole(userRole.get("role_name").toString());
            }
          }
          return postDTO;
        }
    );
    //Serialize the object to json for serialization;
    var memcachedValue = new Gson().toJson(utils.getPage(posts));
    utils.setCacheKey(cacheKey, memcachedValue);
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
        thread.setUpdatedDate(post.getUpdatedDate());
        thread.setUpdatedBy(post.getUpdatedBy());
        thread.setPostCount(thread.getPostCount() + 1);
        Thread savedThread = threadRepo.save(thread);
        Post savedPost = postRepo.save(post);
        if (Objects.nonNull(savedPost) && Objects.nonNull(savedThread)) {
          PostDTO output = mapper.map(savedPost, PostDTO.class);
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
