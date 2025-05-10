package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskType status;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private Long userId;
}
