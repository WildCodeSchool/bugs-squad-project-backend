package com.templateproject.api.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class ToDoList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "user_id")
  @JsonIgnore
    private User user;

    @Column(columnDefinition = "boolean default false")
    private boolean isFavorite;

    @OneToMany(mappedBy = "toDoList", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public ToDoList() {
    }

    public ToDoList(String title) {
      this.title = title;
    }

    public Long getId() {
      return id;
    }

    public List<Task> getTasks() {
      return tasks;
    }

    public void setTasks(List<Task> tasks) {
      this.tasks = tasks;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public boolean isFavorite() {
      return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
      this.isFavorite = isFavorite;
    }

    public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}
