package com.templateproject.api.entity;

import jakarta.persistence.*;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long title;
    private long url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;

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

    public long getUrl() {
        return url;
    }

    public void setUrl(long url) {
        this.url = url;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
