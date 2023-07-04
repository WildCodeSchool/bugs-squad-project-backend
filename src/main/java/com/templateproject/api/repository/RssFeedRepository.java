package com.templateproject.api.repository;

import com.templateproject.api.entity.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
    List<RssFeed> findByIsFavoriteTrue();
}
