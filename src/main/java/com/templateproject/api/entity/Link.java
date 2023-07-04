package com.templateproject.api.entity;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long title;
    private long url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "collection_id", nullable = true)
    @JsonIgnore
    private Collection collection;

    @Transient
    private long collectionId;

    public Long getId() {
        return id;
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

    public long getCollectionId() {
        return collection.getId();
    }
}
