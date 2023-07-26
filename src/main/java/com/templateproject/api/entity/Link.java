package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @NotBlank(message = "URL is mandatory")
    private String url;
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "collection_id", nullable = true)
    @JsonIgnore
    private LinksCollection linksCollection;

    @Transient
    private long collectionId;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LinksCollection getLinksCollection() {
        return linksCollection;
    }

    public void setLinksCollection(LinksCollection linksCollection) {
        this.linksCollection = linksCollection;
    }

    public long getCollectionId() {
        return linksCollection.getId();
    }
}
