package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    public void send(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @SuppressWarnings("null")
public void sendHeatmapMail(
            String to,
            String subject,
            String body,
            byte[] imageBytes) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true,
                        StandardCharsets.UTF_8.name()
                );

        helper.setFrom("mail"); // admin mail
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);

        helper.addAttachment(
                "heatmap.png",
                () -> new ByteArrayInputStream(imageBytes)
        );

        mailSender.send(message);
    }

    // ✉️ Text-only mail (sender)
    @SuppressWarnings("null")
public void sendTextMail(
            String to,
            String subject,
            String body) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        false,
                        StandardCharsets.UTF_8.name()
                );

        helper.setFrom("mail"); //admin mail
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);

        mailSender.send(message);
    }
    
}
