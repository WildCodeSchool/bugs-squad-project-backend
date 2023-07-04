package com.templateproject.api.service;


import com.templateproject.api.entity.Contact;
import com.templateproject.api.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact contactSave(Contact contact) {
        return contactRepository.save(contact);
    }
}