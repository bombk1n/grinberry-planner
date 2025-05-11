package com.bombk1n.grinberryplanner.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Enumeration of possible task statuses")
public enum TaskType {

    @Schema(description = "Task is currently in progress")
    IN_PROGRESS,

    @Schema(description = "Task has been completed")
    COMPLETED
}