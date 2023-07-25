package com.templateproject.api.controller;

import com.templateproject.api.entity.LinksCollection;
import com.templateproject.api.entity.Link;
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
    public @ResponseBody List<LinksCollection> getAllCollections(@RequestParam(value="isfavorite", required = false) boolean isFavorite) {
        return collectionRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody LinksCollection getCollectionById(@PathVariable(value = "id") Long id) {
        return collectionRepository.findById(id).orElse(null);
    }

    @PostMapping("")
    public @ResponseBody LinksCollection createCollection(@RequestBody LinksCollection linksCollection) {
        return collectionRepository.save(linksCollection);
    }

    @PutMapping("/{id}")
    public @ResponseBody LinksCollection updateCollection(@PathVariable(value = "id") Long id, @RequestBody LinksCollection linksCollection) {
        Optional<LinksCollection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            LinksCollection updatedLinksCollection = optionalCollection.get();
            updatedLinksCollection.setTitle(linksCollection.getTitle());
            updatedLinksCollection.setDescription(linksCollection.getDescription());
            updatedLinksCollection.setColor(linksCollection.getColor());
            updatedLinksCollection.setFavorite(linksCollection.isFavorite());
            return collectionRepository.save(updatedLinksCollection);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "LinksCollection non trouvée");
    }

    @PatchMapping("/{id}")
    public @ResponseBody LinksCollection updateIsFavorite(@PathVariable(value="id") Long id, @RequestBody boolean isFavorite) {
        Optional<LinksCollection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            LinksCollection linksCollection = optionalCollection.get();
            linksCollection.setFavorite(isFavorite);
            return collectionRepository.save(linksCollection);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
        }
    }

    @PatchMapping("/{id}/links")
    public LinksCollection updateLinksPosition(@PathVariable(value = "id") Long id, @RequestBody List<Link> links) {
        Optional<LinksCollection> optionalCollection = collectionRepository.findById(id);
        if (optionalCollection.isPresent()) {
            LinksCollection linksCollection = optionalCollection.get();
            for (Link link : links) {
                link.setCollection(linksCollection);
            }
            linksCollection.setLinks(links);
            return collectionRepository.save(linksCollection);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "LinksCollection non trouvée");
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteCollection(@PathVariable(value = "id") Long id) {
        collectionRepository.deleteById(id);
    }

    @PostMapping("/search")
    public @ResponseBody List<LinksCollection> searchCollections(@RequestBody Map<String, String> body) {
        String collection = body.get("text");
        return collectionRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(collection, collection);
    }
}
