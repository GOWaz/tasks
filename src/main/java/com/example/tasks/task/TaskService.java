package com.example.tasks.task;

import com.example.tasks.category.Category;
import com.example.tasks.category.CategoryRepository;
import com.example.tasks.user.User;
import com.example.tasks.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        if (taskRepository.existsById(id)) {
            return taskRepository.findById(id);
        } else {
            throw new RuntimeException("Task not found");
        }

    }

    public Task createTask(Task task, Long CategoryId, Long userId) {
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            task.setUser(user);
        }
        if (CategoryId != null) {
            Category category = categoryRepository.findById(CategoryId).orElseThrow(() -> new RuntimeException("Category not found"));
            task.setCategory(category);
        }
        return taskRepository.save(task);
    }

    public Task assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);

    }

    public Task assignTaskCategory(Long taskId, Long CategoryId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        Category category = categoryRepository.findById(CategoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        task.setCategory(category);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            /*task.setCategory(updatedTask.getCategory());
            task.setUser(updatedTask.getUser());*/
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task with ID " + id + " not found"));
    }


    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found");
        }
    }
}
