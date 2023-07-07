package com.example.demo.service;

import com.example.demo.dto.NewThreadDTO;
import com.example.demo.dto.ThreadDTO;
import com.example.demo.enums.ThreadCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public interface ThreadService {
  Map<String,Object> getThreadByCategory(ThreadCategory category, int pageNumber,int pageSize);
  List<Map<String,Object>> getAllThread();


  Map<String,Object> createThread(NewThreadDTO request);
}
