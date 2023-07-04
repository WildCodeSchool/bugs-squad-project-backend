package com.templateproject.api.controller;

import com.templateproject.api.entity.RssFeed;
import com.templateproject.api.repository.RssFeedRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rssFeeds")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    public RssFeedController(RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;
    }
    @GetMapping("")
    public List<RssFeed> index() {
        return rssFeedRepository.findAll();
    }

    @GetMapping("/{id}")
    public RssFeed show(@PathVariable int id) {
        return  rssFeedRepository.findById((long) id).get();
    }

    @PostMapping("")
    public RssFeed create(@RequestBody RssFeed rssFeed) {
        return rssFeedRepository.save(rssFeed);
    }

    @PutMapping("/{id}")
    public RssFeed update(@PathVariable int id, @RequestBody RssFeed rssFeed) {
        RssFeed rssFeedToUpdate = rssFeedRepository.findById((long) id).get();
        rssFeedToUpdate.setUrl(rssFeed.getUrl());
        rssFeedToUpdate.setFavorite(rssFeed.isFavorite());
        return rssFeedRepository.save(rssFeedToUpdate);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        rssFeedRepository.deleteById((long) id);
        return true;
    }

    @GetMapping("/favorites")
    public List<RssFeed> getAllFavorite() {
        return rssFeedRepository.findByIsFavoriteTrue();
    }
}
