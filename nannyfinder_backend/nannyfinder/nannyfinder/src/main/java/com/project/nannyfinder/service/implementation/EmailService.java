package com.project.nannyfinder.service.implementation;

import com.project.nannyfinder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


//this class sends mail to the recipient
@Service("emailService")
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

//    public void sendEmail(String toEmail, String subject, String body){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("np01cp4s220072@islingtoncollege.edu.np");
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);
//
//        mailSender.send(message);
//
//        System.out.println("Mail sent successfully !");
//
//    }

    @Autowired
    public EmailService(JavaMailSender javaMailSender){
        this.mailSender=javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email){
        mailSender.send(email);
    }





}
