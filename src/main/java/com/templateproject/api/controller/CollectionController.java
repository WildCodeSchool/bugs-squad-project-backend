package com.templateproject.api.controller;

import com.templateproject.api.entity.Collection;
import com.templateproject.api.repository.CollectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CollectionController {

    private final CollectionRepository collectionRepository;

    public CollectionController(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @GetMapping("/collections")
    public @ResponseBody List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    @GetMapping("/collections/{id}")
    public @ResponseBody Collection getCollectionById(@PathVariable(value = "id") Long id) {
        return collectionRepository.findById(id).orElse(null);
    }


}
