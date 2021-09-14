package com.example.companyemployeespring.service;

import com.example.companyemployeespring.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailService {


    private final JavaMailSender javaMailSender;
    @Qualifier("emailTemplateEngine")
    private final TemplateEngine templateEngine;
    private final MailSender mailSender;

    @Async

    public void send(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);


    }

    @Async
    public void sendHtmlEmail(String to, String subject, Employee employee,
                              String link, String templateName,
                              Locale locale) throws MessagingException {
        final Context ctx = new Context(locale);
        ctx.setVariable("name", employee.getName());
        ctx.setVariable("url", link);
        final String htmlContent = templateEngine.process(templateName, ctx);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message =
                new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
        message.setSubject(subject);
        message.setTo(to);

        message.setText(htmlContent, true); // true = isHtml

        // Send mail
        this.javaMailSender.send(mimeMessage);
    }




}
