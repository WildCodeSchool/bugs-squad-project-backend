package com.templateproject.api.controller;

import com.templateproject.api.entity.Task;
import com.templateproject.api.entity.ToDoList;
import com.templateproject.api.repository.TaskRepository;
import com.templateproject.api.repository.ToDoListRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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
    return task.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToDoList not found"));
  }

 @PostMapping("/lists/{listId}/tasks")
  public @ResponseBody Task createTask(@PathVariable Long listId, @RequestBody Task task) {
    Optional<ToDoList> optionalList = toDoListRepository.findById(listId);
    if (optionalList.isPresent()) {
      ToDoList list = optionalList.get();
      task.setToDoList(list);
      return taskRepository.save(task);
    } else {
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }
  }


  @PutMapping("/todo-lists/tasks/{taskid}")
  public Task updateTask(@PathVariable(value = "taskid") Long id, @RequestBody Task task) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (optionalTask.isPresent()) {
      Task updatedTask = optionalTask.get();
      updatedTask.setDescription(task.getDescription());
      updatedTask.setDone(task.getisDone());
      return taskRepository.save(updatedTask);
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
  }

  @DeleteMapping("/todo-lists/tasks/{taskid}")
  public @ResponseBody void deleteTask(@PathVariable(value = "taskid") Long id) {
    taskRepository.deleteById(id);
  }

  @PatchMapping("/todo-lists/tasks/{taskid}")
  public Task updateIsDone(@PathVariable(value="taskid") Long id, @RequestBody boolean isDone) {
    Optional<Task> optionalTask = taskRepository.findById(id);
    if (optionalTask.isPresent()) {
        Task task = optionalTask.get();
        task.setDone(isDone);
        return taskRepository.save(task);
    } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }
  }

  }
