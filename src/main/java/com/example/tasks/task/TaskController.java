package com.example.tasks.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

/*
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
*/

    @PostMapping("/create")
    public ResponseEntity<Task> createTaskWithUser(@RequestBody TaskRequest request) {
        Task task = taskService.createTask(new Task(null, request.getTitle(), request.getDescription(), request.getStatus(), LocalDate.now(), null, null), request.getCategoryId(), request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{taskId}/assign-user/{userId}")
    public ResponseEntity<Task> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        Task updatedTask = taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{taskId}/assign-category/{categoryId}")
    public ResponseEntity<Task> categoriesTask(@PathVariable Long taskId, @PathVariable Long categoryId) {
        Task updatedTask = taskService.assignTaskCategory(taskId, categoryId);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskRequest request) {
        try {
            Task updatedTask = taskService.updateTask(id, new Task(null, request.getTitle(), request.getDescription(), request.getStatus(), LocalDate.now(), null, null));
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

