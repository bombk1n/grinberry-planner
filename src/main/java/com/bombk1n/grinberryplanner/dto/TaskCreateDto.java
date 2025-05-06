package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.entity.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDto {
    @NotBlank
    @Size(max = 100)
    private String title;

    @Size(max = 1000)
    private String description;

    @NotNull
    private TaskType status;

    private LocalDateTime deadline;

    @NotNull
    private Long userId;
}
