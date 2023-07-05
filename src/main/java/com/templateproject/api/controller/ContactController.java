package com.templateproject.api.controller;

import com.templateproject.api.entity.Contact;
import com.templateproject.api.service.ContactService;
import com.templateproject.api.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;
    private final EmailService emailService;

    public ContactController(ContactService contactService, EmailService emailService) {
        this.contactService = contactService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact contactSave = contactService.contactSave(contact);
        emailService.sendEmail(contactSave, "Contact", contactSave.getText());
        return new ResponseEntity<>(contactSave, HttpStatus.CREATED);
    }
}