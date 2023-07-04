package com.templateproject.api.service;

import com.templateproject.api.entity.Contact;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Locale;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail (Contact contact, String subject){
        // Mail pour l'administrateur
        MimeMessagePreparator adminMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@votre-site-web.com");
            messageHelper.setTo(contact.getEmail());
            messageHelper.setSubject('[' + subject + "] Nouveau message de " + contact.getEmail());
            String content = templateEngine.process("EmailTemplateAdmin", new Context(Locale.getDefault(),
                    new HashMap<String, Object>() {{
                        put("contact", contact);
                    }}));
            messageHelper.setText(content, true);
        };
        javaMailSender.send(adminMessagePreparator);

        // Mail de confirmation pour l'utilisateur
        MimeMessagePreparator userMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@votre-site-web.com");
            messageHelper.setTo(contact.getEmail());
            messageHelper.setSubject("Confirmation de formulaire de contact");
            String content = templateEngine.process("EmailTemplateSender", new Context(Locale.getDefault(),
                    new HashMap<String, Object>() {{
                        put("contact", contact);
                    }}));
            messageHelper.setText(content, true);
        };
        javaMailSender.send(userMessagePreparator);
    }
}