package com.templateproject.api.controller;

import com.templateproject.api.entity.Collection;
import com.templateproject.api.repository.CollectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private final CollectionRepository collectionRepository;

    public CollectionController(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @GetMapping("")
    public @ResponseBody List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Collection getCollectionById(@PathVariable(value = "id") Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public @ResponseBody Collection createCollection(@RequestBody Collection collection) {
        return collectionRepository.save(collection);
    }

    @PutMapping("/{id}")
    public @ResponseBody Collection updateCollection(@PathVariable(value = "id") Long id, @RequestBody Collection collection) {
        Optional<Collection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            Collection updatedCollection = optionalCollection.get();
            updatedCollection.setTitle(collection.getTitle());
            updatedCollection.setDescription(collection.getDescription());
            updatedCollection.setColor(collection.getColor());
            updatedCollection.setFavorite(collection.isFavorite());
            return collectionRepository.save(updatedCollection);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collection non trouvée");
    }

    @PatchMapping("/{id}")
    public @ResponseBody Collection updateIsFavorite(@PathVariable(value="id") Long id, @RequestBody boolean isFavorite) {
        Optional<Collection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            Collection collection = optionalCollection.get();
            collection.setFavorite(isFavorite);
            return collectionRepository.save(collection);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteCollection(@PathVariable(value = "id") Long id) {
        collectionRepository.deleteById(id);
    }

    @PostMapping("/search")
    public @ResponseBody List<Collection> searchCollections(@RequestBody Map<String, String> body) {
        String collection = body.get("text");
        return collectionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(collection, collection);
    }
}
