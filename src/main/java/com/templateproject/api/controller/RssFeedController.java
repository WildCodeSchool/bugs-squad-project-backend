package com.templateproject.api.controller;

import com.templateproject.api.entity.RssFeed;
import com.templateproject.api.repository.RssFeedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        Optional<RssFeed> optionalFeedRss = rssFeedRepository.findById((long) id);
        if(optionalFeedRss.isPresent()) {
            RssFeed rssFeedToUpdate = optionalFeedRss.get();
            rssFeedToUpdate.setUrl(rssFeed.getUrl());
            rssFeedToUpdate.setFavorite(rssFeed.isFavorite());
            return rssFeedRepository.save(rssFeedToUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rss Feed Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void delete(@PathVariable int id) {
        rssFeedRepository.deleteById((long) id);
    }

    @GetMapping("/favorites")
    public List<RssFeed> getAllFavorite() {
        return rssFeedRepository.findByIsFavoriteTrue();
    }
}
