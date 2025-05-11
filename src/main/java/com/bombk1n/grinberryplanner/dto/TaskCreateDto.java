package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.TaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for creating a new task")
public class TaskCreateDto {

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Title of the task", example = "Finish Homework")
    private String title;

    @Size(max = 1000)
    @Schema(description = "Detailed description of the task", example = "Complete all exercises from the Java book.")
    private String description;

    @Schema(description = "Status of the task (e.g.,IN_PROGRESS, COMPLETED)", example = "IN_PROGRESS")
    private TaskType status;

    @Schema(description = "Deadline for the task in YYYY-MM-DDTHH:MM:SS format", example = "2025-05-20T15:00:00")
    private LocalDateTime deadline;

    @Schema(description = "User ID who is assigned to the task", example = "1")
    private Long userId;
}

