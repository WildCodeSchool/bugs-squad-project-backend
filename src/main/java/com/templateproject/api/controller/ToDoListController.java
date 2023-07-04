package com.templateproject.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.templateproject.api.entity.Task;
import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.TaskRepository;
import com.templateproject.api.repository.ToDoListRepository;

@RestController
public class ToDoListController {

  final ToDoListRepository toDoListRepository;
  final TaskRepository taskRepository;

  public ToDoListController(ToDoListRepository toDoListRepository, TaskRepository taskRepository) {
    this.toDoListRepository = toDoListRepository;
    this.taskRepository = taskRepository;
  }

  @GetMapping("/api/todo-lists")
  public List<ToDoList> getToDoLists() {
    return toDoListRepository.findAll();
  }

  @GetMapping("/api/todo-lists/{id}")
  public ToDoList getToDoList(Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    return toDoList.orElse(null);
  }

  @PostMapping("/api/todo-lists")
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }

  @PutMapping("/api/todo-lists/{id}")
  public ToDoList update(@PathVariable(value = "id") Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    if (toDoList.isPresent()) {
      ToDoList updatedToDoList = toDoList.get();
      updatedToDoList.setTitle(updatedToDoList.getTitle());
      updatedToDoList.setDescription(updatedToDoList.getDescription());
      updatedToDoList.setTasks(updatedToDoList.getTasks());
      updatedToDoList.setFavorite(!updatedToDoList.isFavorite());
      return toDoListRepository.save(toDoList.get());
    }
    return null;
  }

  @DeleteMapping("/api/todo-lists/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    toDoList.ifPresent(toDoListRepository::delete);
  }

  //create a new task
  @PostMapping("/api/todo-lists/{id}")
  public Task createTask(@PathVariable("id") Long listId, @RequestBody Task task) {
        Optional<ToDoList> optionalToDoList = toDoListRepository.findById(listId);
        if (optionalToDoList.isPresent()) {
            ToDoList toDoList = optionalToDoList.get();
            task.setToDoList(toDoList);
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

}
