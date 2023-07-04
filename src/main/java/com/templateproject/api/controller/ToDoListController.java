package com.templateproject.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.templateproject.api.entity.Task;
import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.TaskRepository;
import com.templateproject.api.repository.ToDoListRepository;

@RestController
public class ToDoListController {

  private ToDoListRepository toDoListRepository;
  private TaskRepository taskRepository;

  public ToDoListController(ToDoListRepository toDoListRepository) {
    this.toDoListRepository = toDoListRepository;
  }
  public ToDoListController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }


  @GetMapping("/todo-lists")
  public List<ToDoList> getToDoLists() {
    return toDoListRepository.findAll();
  }

  @GetMapping("/todo-lists/{id}")
  public ToDoList getToDoList(Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    if (toDoList.isPresent()) {
      return toDoList.get();
    }
    return null;
  }

  @PostMapping("/todo-lists")
  public ToDoList createToDoList(@RequestBody ToDoList toDoList) {
    return toDoListRepository.save(toDoList);
  }

  @PutMapping("/todo-lists/{id}")
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

  @DeleteMapping("/todo-lists/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    Optional<ToDoList> toDoList = toDoListRepository.findById(id);
    if (toDoList.isPresent()) {
      toDoListRepository.delete(toDoList.get());
    }
  }

  //create a new task
  @PostMapping("/todo-lists/{id}")
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
