package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;
  private String description;
  @Column(columnDefinition = "boolean default false")
  private boolean isDone;

  @ManyToOne(cascade = CascadeType.REFRESH)
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
