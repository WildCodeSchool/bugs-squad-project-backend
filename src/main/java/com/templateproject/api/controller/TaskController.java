package com.templateproject.api.controller;

import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;



@RestController
public class TaskController {

  private TaskRepository taskRepository;
  private ToDoListRepository toDoListRepository;

  public TaskController(TaskRepository taskRepository, ToDoListRepository toDoListRepository) {
    this.taskRepository = taskRepository;
    this.toDoListRepository = toDoListRepository;
  }

   @GetMapping("/lists/{listId}/tasks")
  public List<Task> getAllTasksByListId(@PathVariable Long listId) {
    Optional<ToDoList> optionalList = toDoListRepository.findById(listId);

    return optionalList.map(ToDoList::getTasks).orElse(null);
}


  @GetMapping("/todo-lists/tasks/{taskid}")
  public Task getTask(Long taskid) {
    Optional<Task> task = taskRepository.findById(taskid);
    return task.orElse(null);
  }

 @PostMapping("/lists/{listId}/tasks")
public ResponseEntity<List<Task>> addTasksToList(@PathVariable Long listId, @RequestBody List<Task> tasks) {
    Optional<ToDoList> optionalList = toDoListRepository.findById(listId);
    if (optionalList.isPresent()) {
        ToDoList list = optionalList.get();
        tasks.forEach(task -> task.setToDoList(list));
        taskRepository.saveAll(tasks);
        return ResponseEntity.ok(tasks);
    } else {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }
  }


  @PutMapping("/todo-lists/{id}/tasks/{taskid}")
  public Task updateTask(@PathVariable(value = "id") Long id, @RequestBody Task task) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (optionalTask.isPresent()) {
      Task updatedTask = optionalTask.get();
      updatedTask.setDescription(updatedTask.getDescription());
      updatedTask.setDone(updatedTask.isDone());
      return taskRepository.save(updatedTask);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
  }

  @DeleteMapping("/todo-lists/{taskid}")
  public @ResponseBody void deleteTask(@PathVariable(value = "taskid") Long id) {
    taskRepository.deleteById(id);
  }

  }
