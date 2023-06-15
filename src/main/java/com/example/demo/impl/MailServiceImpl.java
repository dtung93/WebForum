package com.example.demo.impl;

import com.example.demo.dto.EmailDTO;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.MailService;
import com.example.demo.utilities.Utils;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public class MailServiceImpl implements MailService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private Utils utils;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Override
  public void sendConfirmationEmail(EmailDTO request) throws MessagingException {
    try {
      String subject = "DXT Forum account confirmation";
      String content = "Hello " + request.getUsername() + ", <br>" +
          "On behalf of the DXT team, we would like to thank you sincerely for joing us at DXT. We are certain that great things await us!" +
          "<br>" + "To finish your registration, please click on this link to verify your account: " + "<a href='http://localhost:8080/api/mail/verify?activationToken=" +
          request.getToken() + "'>" + "confirmation_link" + "</a>" + ". This link will expire in <span style='font-weight:bold'>3</span> days" +
          "<br>" +
          "<hr>" +
          "<br>" +
          "<img src='cid:logo'>";
      utils.sendEmail(request, subject, content);
    } catch (Exception e) {
      throw new MessagingException(e.getMessage());
    }
  }

  @Override
  public boolean sendChangePasswordEmail(EmailDTO request) throws MessagingException {
    try {
      User user = userRepo.findByEmail(request.getEmail());
      if (Objects.nonNull(user)) {
        String changePasswordToken = jwtTokenProvider.generateToken(2);
        user.setChangePasswordToken(changePasswordToken);
        userRepo.save(user);
        String subject = "DXT Forum password recovery";
        String content = "Hello " + user.getUsername() + ", <br><br>" +
            "We have received your request for password recovery associated with this account" +
            "<br><br>" + "Please click on this link and follow the instructions to reset your password " + "<a href='http://localhost:8080/api/mail/recover-password?changePasswordToken=" +
            changePasswordToken + "'>" + "reset_password" + "</a>" + ". This link will expire in <span style='font-weight:bold'>1</span> days" +
            "<br>" +
            "<hr>" +
            "<br>" +
            "<img src='cid:logo'>";
        utils.sendEmail(request, subject, content);
        return true;
      } else {
        return false;
      }
    } catch (Exception e) {
      throw new MessagingException(e.getMessage());
    }
  }
}
