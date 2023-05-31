package com.example.demo.service;

import com.example.demo.dto.EmailDTO;
import com.example.demo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Class
 *
 * @author Tung Dang
 * @function_Id:
 * @screen_ID:
 */
@Service
public interface MailService {

boolean sendConfirmationEmail(EmailDTO email) throws MessagingException;
boolean sendChangePasswordEmail(EmailDTO email) throws MessagingException;

}
