package com.publicvm.siburarenda.service.impl;

import com.publicvm.siburarenda.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.SendFailedException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    public final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void send(String message, String to) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Activation");
        msg.setText(message);
        try {
            emailSender.send(msg);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }
}
