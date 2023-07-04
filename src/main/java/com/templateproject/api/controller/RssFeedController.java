package com.templateproject.api.controller;

import com.templateproject.api.entity.RssFeed;
import com.templateproject.api.repository.RssFeedRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    public RssFeedController(RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;
    }
    @GetMapping("/rssFeeds")
    public List<RssFeed> index() {
        return rssFeedRepository.findAll();
    }

    @GetMapping("/rssFeeds/{id}")
    public RssFeed show(@PathVariable int id) {
        return  rssFeedRepository.findById((long) id).get();
    }

    @PostMapping("/rssFeeds")
    public RssFeed create(@RequestBody RssFeed rssFeed) {
        return rssFeedRepository.save(rssFeed);
    }

    @PutMapping("/rssFeeds/{id}")
    public RssFeed update(@PathVariable int id, @RequestBody RssFeed rssFeed) {
        RssFeed rssFeedToUpdate = rssFeedRepository.findById((long) id).get();
        rssFeedToUpdate.setUrl(rssFeed.getUrl());
        rssFeedToUpdate.setFavorite(rssFeed.isFavorite());
        return rssFeedRepository.save(rssFeedToUpdate);
    }

    @DeleteMapping("/rssFeeds/{id}")
    public boolean delete(@PathVariable int id) {
        rssFeedRepository.deleteById((long) id);
        return true;
    }

    @GetMapping("/rssFeeds/favorites")
    public List<RssFeed> getAllFavorite() {
        return rssFeedRepository.findByIsFavoriteTrue();
    }
}
