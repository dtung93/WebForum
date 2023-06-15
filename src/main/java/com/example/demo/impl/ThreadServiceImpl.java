package com.example.demo.impl;

import com.example.demo.dto.NewThreadDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.enums.ThreadCategory;
import com.example.demo.model.Post;
import com.example.demo.model.Thread;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.ThreadRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ThreadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class ThreadServiceImpl implements ThreadService {
  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PostRepo postRepo;

  @Autowired
  private ThreadRepo threadRepo;

  private static final String error = "error";
  ModelMapper mapper = new ModelMapper();

  public Map<String, Object> createThreadMethod(NewThreadDTO request) {
    User user = userRepo.findByUsername(request.getAuthor());
    Map<String, Object> result = new HashMap<>();
    if (Objects.nonNull(user)) {
      Thread thread = new Thread();
      thread.setCategory(request.getCategory());
      thread.setTitle(request.getTitle());
      thread.setUser(user);
      thread.setViews(0L);
      thread.setLastReplied(user.getUsername());
      thread.setCreatedBy(user.getUsername());
      thread.setCreatedDate(new Date());
      thread.setUpdatedBy(user.getUsername());
      thread.setUpdatedDate(thread.getCreatedDate());
      thread.setRemovalFlag(false);
      Thread savedThread = threadRepo.save(thread);
      if (Objects.nonNull(savedThread)) {
        Post post = new Post();
        post.setUser(user);
        post.setThread(savedThread);
        post.setContent(request.getContent());
        post.setCreatedDate(savedThread.getCreatedDate());
        post.setCreatedBy(user.getUsername());
        post.setUpdatedBy(user.getUsername());
        post.setUpdatedDate(savedThread.getCreatedDate());
        post.setRemovalFlag(false);
        Post savedPost = postRepo.save(post);
        if (Objects.nonNull(savedPost)) {
          savedThread.getPosts().add(savedPost);
          var resultThread = threadRepo.save(savedThread);
          ModelMapper mapper = new ModelMapper();
          ThreadDTO finalResult = mapper.map(resultThread, ThreadDTO.class);
          finalResult.setAuthor(request.getAuthor());
          finalResult.setCategory(resultThread.getCategory());
          finalResult.setReplies(0);
          finalResult.setViews(0);
          result.put("thread", finalResult);
        } else
          result.put(error, "Failed to save post!");
        return result;
      } else
        result.put(error, "Failed to save thread!");
      return result;
    } else
      result.put(error, "Username " + request.getAuthor() + " not found!");
    return result;
  }


  @Override
  public Map<String,Object> getThreadByCategory(ThreadCategory category, int pageNumber, int pageSize) {
    int offset = pageNumber * pageSize;
    Map<String,Object> output = new HashMap<>();
    Pageable pageable = new PageDataOffset(offset, pageSize, Sort.by("updatedDate").descending());
    Page<Thread> pageThread= threadRepo.getThreadByCategory(category,pageable);
    Page<ThreadDTO> result= pageThread.map((item)->mapper.map(item, ThreadDTO.class));
    output.put("currentPage",result.getPageable().getPageNumber());
    output.put("pageSize",result.getSize());
    output.put("totalPages",result.getTotalPages());
    output.put("lastPage",result.isLast());
    output.put("firstPage",result.isFirst());
    output.put("offset",result.getPageable().getOffset());
    output.put("items",result.getContent());
    return output;
  }

  @Transactional
  public Map<String, Object> createThread(NewThreadDTO request) {
    return createThreadMethod(request);
  }
}
