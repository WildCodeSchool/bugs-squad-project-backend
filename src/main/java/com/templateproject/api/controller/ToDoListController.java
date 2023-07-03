package com.templateproject.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.ToDoListRepository;

@RestController
public class ToDoListController {

  private ToDoListRepository toDoListRepository;

  public ToDoListController(ToDoListRepository toDoListRepository) {
    this.toDoListRepository = toDoListRepository;
  }


  @GetMapping("/api/todo-lists")
  public List<ToDoList> getToDoLists() {
    return toDoListRepository.findAll();
  }

  @GetMapping("/api/todo-lists/{id}")
  public ToDoList getToDoList(Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    if (toDoList.isPresent()) {
      return toDoList.get();
    }
    return null;
  }

  @PostMapping("/api/todo-lists")
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }



}
