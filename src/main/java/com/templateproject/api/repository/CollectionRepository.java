package com.templateproject.api.repository;

import com.templateproject.api.entity.LinksCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<LinksCollection, Long> {
    List<LinksCollection> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
