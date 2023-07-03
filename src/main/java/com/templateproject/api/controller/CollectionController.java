package com.templateproject.api.controller;

import com.templateproject.api.entity.Collection;
import com.templateproject.api.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CollectionController {

    @Autowired
    private CollectionRepository collectionRepository;

    @GetMapping("/collections")
    public @ResponseBody List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }
}
