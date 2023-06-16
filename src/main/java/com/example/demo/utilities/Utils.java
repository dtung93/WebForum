package com.example.demo.utilities;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.ExceptionDTO;
import com.example.demo.impl.PageDataOffset;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.CustomException;
import com.example.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Component
public class Utils {
  @Autowired
  private JavaMailSender javaMailSender;

  @Value("${special.characters.regex}")
  private String specialCharRegEx;

  @Value("${email.regex}")
  private String emailRegex;

  public boolean containsSpecialCharacters(Object dto) throws IllegalAccessException {
    Class<?> dtoClass = dto.getClass();
    Field[] fields = dtoClass.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      if (field.getName().equals("email")) {
        continue;
      }
      if (field.get(dto).toString().matches(".*" + specialCharRegEx + ".*")) {
        return true;
      }
    }
    return false;
  }

  public boolean isEmail(String input) {
    return input.matches(emailRegex) && input.length() >= 6 && input.length() <= 50;
  }

  public void sendEmail(EmailDTO request, String subject, String body) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    try {
      helper.setTo(request.getEmail());
      helper.setFrom("DXT_ADMIN");
      helper.setSubject(subject);
      helper.addInline("logo", new ClassPathResource("resources/static/logo.png"));
      helper.setText(body, true);
      javaMailSender.send(message);
    } catch (Exception e) {
      throw new MessagingException(e.getMessage());
    }
  }

  //  1 is bad request, 2 is internal error, 3 is no resource
  public ExceptionDTO handleError(HttpServletRequest http, int type, Exception e) {
    ExceptionDTO exceptionDTO = new ExceptionDTO();
    exceptionDTO.setError("Exception");
    exceptionDTO.setStatus(type == 1 ? HttpStatus.BAD_REQUEST.value() : (type == 2 ? HttpStatus.INTERNAL_SERVER_ERROR.value() : HttpStatus.NO_CONTENT.value()));
    exceptionDTO.setUrl(http.getRequestURI());
    exceptionDTO.setDescription(e.getMessage());
    exceptionDTO.setTimestamp(new Date().toString());
    return exceptionDTO;
  }

  public ExceptionDTO errorResult(HttpServletRequest http,String exception, String message){
    ExceptionDTO exceptionDTO = new ExceptionDTO();
    exceptionDTO.setError(exception);
    exceptionDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    exceptionDTO.setUrl(http.getRequestURI());
    exceptionDTO.setDescription(message);
    exceptionDTO.setTimestamp(new Date().toString());
    return exceptionDTO;
  }

  // Convert file size and display it to KB,MB,GB,TB in DTO response
  public String getFileSize(long size) {
    long n = 1024;
    String s = "";
    double kb = size / n;
    double mb = kb / n;
    double gb = mb / n;
    double tb = gb / n;
    if (size < n) {
      s = size + " Bytes";
    } else if (size >= n && size < (n * n)) {
      s = String.format("%.1f", kb) + " KB";
    } else if (size >= (n * n) && size < (n * n * n)) {
      s = String.format("%.1f", mb) + " MB";
    } else if (size >= (n * n * n) && size < (n * n * n * n)) {
      s = String.format("%.2f", gb) + " GB";
    } else if (size >= (n * n * n * n)) {
      s = String.format("%.2f", tb) + " TB";
    }
    return s;
  }

  //Get current authenticated username from Spring security context application
  public String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return Objects.nonNull(authentication) ? authentication.getName() : null;
  }

  //Get items and page info method
  public Map<String,Object> getPage(Page<?> page){
    Map<String,Object> result = new HashMap<>();
    result.put("currentPage",page.getPageable().getPageNumber());
    result.put("numberOfItemsCurrentPage", page.getNumberOfElements());
    result.put("pageSize",page.getSize());
    result.put("totalPages",page.getTotalPages());
    result.put("lastPage",page.isLast());
    result.put("firstPage",page.isFirst());
    result.put("offset",page.getPageable().getOffset());
    result.put("items",page.getContent());
    return result;
  }

}
