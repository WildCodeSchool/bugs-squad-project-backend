package com.templateproject.api.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class ToDoList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

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

}
