package com.templateproject.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.ToDoListRepository;

@RestController
public class ToDoListController {

  final ToDoListRepository toDoListRepository;

  public ToDoListController(ToDoListRepository toDoListRepository) {
    this.toDoListRepository = toDoListRepository;
  }


  @GetMapping("/todo-lists")
  public List<ToDoList> getToDoLists() {
    return toDoListRepository.findAll();
  }

  @GetMapping("/todo-lists/{id}")
  public ToDoList getToDoList(Long id) {
    return toDoListRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found"));
  }

  @PostMapping("/todo-lists")
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }

  @PutMapping("/todo-lists/{id}")
  public ToDoList updateToDoList(@PathVariable(value = "id") Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    if (toDoList.isPresent()) {
      ToDoList updatedToDoList = toDoList.get();
      updatedToDoList.setTitle(updatedToDoList.getTitle());
      updatedToDoList.setDescription(updatedToDoList.getDescription());
      updatedToDoList.setTasks(updatedToDoList.getTasks());
      updatedToDoList.setFavorite(!updatedToDoList.isFavorite());
      return toDoListRepository.save(toDoList.get());
    }
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
  }

  @DeleteMapping("/todo-lists/{id}")
  public void deleteToDoList(@PathVariable(value = "id") Long id) {
    toDoListRepository.deleteById(id);
  }

}