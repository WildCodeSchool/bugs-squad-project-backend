package com.templateproject.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


  @GetMapping("/todo-lists")
  public List<ToDoList> getToDoLists(@RequestParam(value="isfavorite", required = false) boolean isFavorite) {
    if (isFavorite) {
      return toDoListRepository.findByIsFavorite(isFavorite);
    }
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
  public ToDoList updateToDoList(@PathVariable(value = "id") Long id, @RequestBody ToDoList toDoList) {
    Optional<ToDoList> optionaltoDoList = toDoListRepository.findById(id);
    if (optionaltoDoList.isPresent()) {
      ToDoList updatedToDoList = optionaltoDoList.get();
      updatedToDoList.setTitle(toDoList.getTitle());
      updatedToDoList.setDescription(toDoList.getDescription());
      updatedToDoList.setTasks(toDoList.getTasks());
      updatedToDoList.setFavorite(toDoList.isFavorite());
      return toDoListRepository.save(updatedToDoList);
    }
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
  }

  @PatchMapping("/todo-lists/{id}")
  public ToDoList updateIsFavorite(@PathVariable(value="id") Long id, @RequestBody boolean isFavorite) {
    Optional<ToDoList> optionalToDoList = toDoListRepository.findById(id);
    if (optionalToDoList.isPresent()) {
        ToDoList toDoList = optionalToDoList.get();
        toDoList.setFavorite(isFavorite);
        return toDoListRepository.save(toDoList);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
    }
  }

  @PatchMapping("/todo-lists/{id}/tasks")
  public ToDoList updateTasksPosition(@PathVariable(value="id") Long id, @RequestBody List<Task> tasks) {
    Optional<ToDoList> optionalToDoList = toDoListRepository.findById(id);
    if (optionalToDoList.isPresent()) {
        ToDoList toDoList = optionalToDoList.get();
                for (Task task : tasks) {
          task.setToDoList(toDoList);
        }
        toDoList.setTasks(tasks);
        return toDoListRepository.save(toDoList);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found");
    }
  }


  @DeleteMapping("/todo-lists/{id}")
  public void deleteToDoList(@PathVariable(value = "id") Long id) {
    toDoListRepository.deleteById(id);
  }

}
