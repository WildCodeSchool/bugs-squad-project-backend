package com.templateproject.api.controller;

import com.templateproject.api.entity.RssFeed;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.RssFeedRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rssFeeds")
public class RssFeedController {

    private final RssFeedRepository rssFeedRepository;
    private final UserRepository userRepository;

    public RssFeedController(RssFeedRepository rssFeedRepository, UserRepository userRepository) {
        this.rssFeedRepository = rssFeedRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<RssFeed> index() {
        return rssFeedRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<RssFeed> getRssFeedsByUserId(@PathVariable Long userId) {
        return rssFeedRepository.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public RssFeed show(@PathVariable int id) {
        return rssFeedRepository.findById((long) id).get();
    }

    @PostMapping("")
    public RssFeed create(@RequestBody RssFeed rssFeed) {
        rssFeed.setFavorite(false);
        return rssFeedRepository.save(rssFeed);
    }

    @PostMapping("/user/{userId}")
    public RssFeed createRssFeedForUser(@PathVariable Long userId, @RequestBody RssFeed rssFeed) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found");
        }
        User user = optionalUser.get();
        rssFeed.setUser(user);
        rssFeed.setFavorite(false);
        return rssFeedRepository.save(rssFeed);
    }

    @PutMapping("/{id}")
    public RssFeed update(@PathVariable int id, @RequestBody RssFeed rssFeed) {
        Optional<RssFeed> optionalFeedRss = rssFeedRepository.findById((long) id);
        if (optionalFeedRss.isPresent()) {
            RssFeed rssFeedToUpdate = optionalFeedRss.get();
            rssFeedToUpdate.setUrl(rssFeed.getUrl());
            rssFeedToUpdate.setFavorite(rssFeed.isFavorite());
            rssFeedToUpdate.setTitle(rssFeed.getTitle());
            return rssFeedRepository.save(rssFeedToUpdate);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Rss Feed Not Found");
        }
    }

    @PutMapping("/user/{userId}/{rssFeedId}")
    public ResponseEntity<RssFeed> updateRssFeedForUser(
            @PathVariable Long userId,
            @PathVariable Long rssFeedId,
            @RequestBody RssFeed updatedRssFeedData
    ) {
        Optional<RssFeed> optionalRssFeed = rssFeedRepository.findById(rssFeedId);
        if (optionalRssFeed.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RssFeed rssFeed = optionalRssFeed.get();
        if (!rssFeed.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        rssFeed.setUrl(updatedRssFeedData.getUrl());
        rssFeed.setTitle(updatedRssFeedData.getTitle());
        rssFeed.setFavorite(updatedRssFeedData.isFavorite()); // Make sure to set the 'favorite' property based on the updated data

        rssFeed = rssFeedRepository.save(rssFeed);
        return ResponseEntity.ok(rssFeed);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void delete(@PathVariable int id) {
        rssFeedRepository.deleteById((long) id);
    }

    @DeleteMapping("/user/{userId}/{rssFeedId}")
    public ResponseEntity<?> deleteRssFeedForUser(@PathVariable Long userId, @PathVariable Long rssFeedId) {
        Optional<RssFeed> optionalRssFeed = this.rssFeedRepository.findById(rssFeedId);
        if (optionalRssFeed.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RssFeed rssFeed = optionalRssFeed.get();
        if (!rssFeed.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ce flux RSS n'appartient pas à cet utilisateur");
        }
        rssFeedRepository.delete(rssFeed);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorites/user/{userId}")
    public ResponseEntity<List<RssFeed>> getFavoriteRssFeedsForUser(@PathVariable Long userId) {
        List<RssFeed> favoriteRssFeeds = rssFeedRepository.findByIsFavoriteTrueAndUser_Id(userId);
        return ResponseEntity.ok(favoriteRssFeeds);
    }
}
