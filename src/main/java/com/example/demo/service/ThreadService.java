package com.example.demo.service;

import com.example.demo.dto.NewThreadDTO;
import com.example.demo.enums.ThreadCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  Map<String,Object> createThread(NewThreadDTO request);
}
