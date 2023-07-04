package com.templateproject.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.templateproject.api.entity.Task;
import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.TaskRepository;
import com.templateproject.api.repository.ToDoListRepository;


import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class TaskController {

  private TaskRepository taskRepository;
  private ToDoListRepository toDoListRepository;

  public TaskController(TaskRepository taskRepository, ToDoListRepository toDoListRepository) {
    this.taskRepository = taskRepository;
    this.toDoListRepository = toDoListRepository;
  }

   @GetMapping("/api/lists/{listId}/tasks")
  public List<Task> getAllTasksByListId(@PathVariable Long listId) {
    Optional<ToDoList> optionalList = toDoListRepository.findById(listId);

    return optionalList.map(ToDoList::getTasks).orElse(null);
}


  @GetMapping("/todo-lists/tasks/{taskid}")
  public Task getTask(Long taskid) {
    Optional<Task> task = taskRepository.findById(taskid);
    return task.orElse(null);
  }

 @PostMapping("/api/lists/{listId}/tasks")
public ResponseEntity<List<Task>> addTasksToList(@PathVariable Long listId, @RequestBody List<Task> tasks) {
    ToDoList todoList = toDoListRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("List not found with id " + listId));

    for (Task task : tasks) {
        task.setToDoList(todoList);
    }

    List<Task> savedTasks = taskRepository.saveAll(tasks);

    return ResponseEntity.ok(savedTasks);
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
    return null;
  }

  @DeleteMapping("/todo-lists/{taskid}")
  public void deleteTask(@PathVariable(value = "taskid") Long id) {
    Optional<Task> task = taskRepository.findById(id);
    task.ifPresent(taskRepository::delete);
  }

  }
