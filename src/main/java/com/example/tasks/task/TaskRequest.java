package com.example.tasks.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Long categoryId;
    private Long userId;
}

