package com.bombk1n.grinberryplanner.dto;

import com.bombk1n.grinberryplanner.enums.TaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO representing a task entity")
public class TaskDto {

    @Schema(description = "Unique identifier of the task", example = "101")
    private Long id;

    @Schema(description = "Title of the task", example = "Write project report")
    private String title;

    @Schema(description = "Detailed description of the task", example = "Summarize all findings and write the final version of the report.")
    private String description;

    @Schema(description = "Current status of the task", example = "IN_PROGRESS")
    private TaskType status;

    @Schema(description = "Date and time when the task was created", example = "2025-05-10T08:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Deadline for the task", example = "2025-05-15T17:00:00")
    private LocalDateTime deadline;

    @Schema(description = "ID of the user who owns the task", example = "42")
    private Long userId;
}
