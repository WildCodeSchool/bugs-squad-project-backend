package com.templateproject.api.controller;

import com.templateproject.api.entity.Collection;
import com.templateproject.api.entity.Link;
import com.templateproject.api.repository.CollectionRepository;
import com.templateproject.api.repository.LinkRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {

    private final LinkRepository linkRepository;
    private final CollectionRepository collectionRepository;

    public LinkController(LinkRepository linkRepository, CollectionRepository collectionRepository) {
        this.linkRepository = linkRepository;
        this.collectionRepository = collectionRepository;
    }

    @GetMapping("")
    public @ResponseBody List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Link getLinkById(@PathVariable(value = "id") Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @PostMapping("/{collectionId}")
    public @ResponseBody Link createLink(@PathVariable(value = "collectionId") Long collectionId, @RequestBody Link link) {
        Optional<Collection> optionalCollection = collectionRepository.findById(collectionId);
        if (optionalCollection.isPresent()) {
            Collection collection = optionalCollection.get();
            link.setCollection(collection);
            return linkRepository.save(link);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Collection non trouvée");
        }
    }

    @PatchMapping("/{id}")
    public @ResponseBody Link updateLink(@PathVariable(value = "id") Long id, @RequestBody Link link) {
        Optional<Link> optionalLink = linkRepository.findById(id);
        if (optionalLink.isPresent()) {
            Link updatedLink = optionalLink.get();
            updatedLink.setTitle(link.getTitle());
            updatedLink.setUrl(link.getUrl());
            return linkRepository.save(updatedLink);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lien non trouvé");
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteLink(@PathVariable(value = "id") Long id) {
        linkRepository.deleteById(id);
    }
}
