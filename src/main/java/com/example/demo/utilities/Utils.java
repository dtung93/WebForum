package com.example.demo.utilities;

import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.ExceptionDTO;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.CustomException;
import com.example.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;
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


  public void sendEmail(EmailDTO request, String subject, String body) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
    try {
      helper.setTo(request.getEmail());
      helper.setFrom("DXT_ADMIN");
      helper.setSubject(subject);
      helper.setText(body, true);
      javaMailSender.send(message);
    } catch (Exception e) {
      throw new MessagingException(e.getMessage());
    }
  }


}
