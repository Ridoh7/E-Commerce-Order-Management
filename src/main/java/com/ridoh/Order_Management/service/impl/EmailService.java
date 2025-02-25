package com.ridoh.Order_Management.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * Service class responsible for sending emails.
 */
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * The email address used as the sender.
     * This value is injected from the application properties.
     */
    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Constructs an EmailService instance with a JavaMailSender.
     *
     * @param mailSender the JavaMailSender instance for sending emails
     */
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends an email with the specified recipient, subject, and body content.
     *
     * @param to      the recipient's email address
     * @param subject the subject of the email
     * @param body    the body content of the email (supports HTML)
     * @throws MessagingException if an error occurs while sending the email
     */
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // 'true' enables HTML content

        mailSender.send(message);
    }
}
