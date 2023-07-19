package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Task {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  private String description;
  private Integer position;

  public Integer getPosition() {
    return position;
  }

  public void setPosition(Integer position) {
    this.position = position;
  }

  @Column(columnDefinition = "boolean default false")
  private boolean isDone;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "todolist_id")
  @JsonIgnore
  private ToDoList toDoList;

  public ToDoList getToDoList() {
    return toDoList;
  }

  public void setToDoList(ToDoList toDoList) {
    this.toDoList = toDoList;
  }

  public Task() {
  }
  public Task (String description) {
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean isDone) {
    this.isDone = isDone;
  }


}
