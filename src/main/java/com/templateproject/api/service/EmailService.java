package com.templateproject.api.service;

import com.templateproject.api.entity.Contact;
import org.springframework.core.io.ClassPathResource;
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

    public void sendEmail (Contact contact, String subject, String body){
        // Mail pour l'administrateur
        MimeMessagePreparator adminMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
            ClassPathResource resource = new ClassPathResource("static/K.I.T.png");
            messageHelper.addAttachment("static/K.I.T.png", resource);
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
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // Set multipart flag to true
            ClassPathResource resource = new ClassPathResource("K.I.T.png");
            String resourceId = "resource";
            messageHelper.addInline(resourceId, resource);
            messageHelper.setFrom("noreply@votre-site-web.com");
            messageHelper.setTo(contact.getEmail());
            messageHelper.setSubject("Confirmation de formulaire de contact");
            Context context = new Context(Locale.getDefault());
            context.setVariable("logoUrl", "cid:" + resourceId); // Pass the logo URL as a variable
            String content = templateEngine.process("EmailTemplateSender", context); // Update the template rendering code
            messageHelper.setText(content, true);
        };
        javaMailSender.send(userMessagePreparator);
    }
}