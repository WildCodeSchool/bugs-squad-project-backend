package com.templateproject.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long title;
    private long description;
    private long color;
    private boolean isFavorite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTitle() {
        return title;
    }

    public void setTitle(long title) {
        this.title = title;
    }

    public long getDescription() {
        return description;
    }

    public void setDescription(long description) {
        this.description = description;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
