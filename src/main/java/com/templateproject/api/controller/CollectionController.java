package com.templateproject.api.controller;

import com.templateproject.api.entity.Collection;
import com.templateproject.api.repository.CollectionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
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

    @PostMapping("/collections")
    public @ResponseBody Collection createCollection(@RequestBody Collection collection) {
        return collectionRepository.save(collection);
    }

    @PutMapping("/collections/{id}")
    public @ResponseBody Collection updateCollection(@PathVariable(value = "id") Long id, @RequestBody Collection collection) {
        Optional<Collection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            Collection updatedCollection = optionalCollection.get();
            updatedCollection.setTitle(collection.getTitle());
            updatedCollection.setDescription(collection.getDescription());
            updatedCollection.setColor(collection.getColor());
            updatedCollection.setFavorite(collection.isFavorite());
            return collectionRepository.save(updatedCollection);
        } else {
            return collectionRepository.save(collection);
        }
    }

    @DeleteMapping("/collections/{id}")
    public @ResponseBody void deleteCollection(@PathVariable(value = "id") Long id) {
        collectionRepository.deleteById(id);
    }

    @PostMapping("/collections/search")
    public @ResponseBody List<Collection> searchCollections(@RequestBody Map<String, String> body) {
        String collection = body.get("text");
        return collectionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(collection, collection);
    }
}
