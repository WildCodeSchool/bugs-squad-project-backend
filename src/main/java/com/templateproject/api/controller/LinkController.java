package com.templateproject.api.controller;

import com.templateproject.api.entity.Link;
import com.templateproject.api.repository.LinkRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LinkController {

    private final LinkRepository linkRepository;

    public LinkController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("/links")
    public @ResponseBody List<Link> getAllLinks() {
        return linkRepository.findAll();
    }

    @GetMapping("/links/{id}")
    public @ResponseBody Link getLinkById(@PathVariable(value = "id") Long id) {
        return linkRepository.findById(id).orElse(null);
    }

    @PostMapping("/links")
    public @ResponseBody Link createLink(@RequestBody Link link) {
        return linkRepository.save(link);
    }

    @PutMapping("/links/{id}")
    public @ResponseBody Link updateLink(@PathVariable(value = "id") Long id, @RequestBody Link link) {
        Optional<Link> optionalLink = linkRepository.findById(id);
        if (optionalLink.isPresent()) {
            Link updatedLink = optionalLink.get();
            updatedLink.setTitle(link.getTitle());
            updatedLink.setUrl(link.getUrl());
            updatedLink.setCollection(link.getCollection());
            return linkRepository.save(updatedLink);
        } else {
            return linkRepository.save(link);
        }
    }

    @DeleteMapping("/links/{id}")
    public @ResponseBody void deleteLink(@PathVariable(value = "id") Long id) {
        linkRepository.deleteById(id);
    }
}
