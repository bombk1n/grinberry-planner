package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.TaskCreateDto;
import com.bombk1n.grinberryplanner.dto.TaskDto;
import com.bombk1n.grinberryplanner.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("api/todo")
@SecurityRequirement(name = "bearer-jwt")
@Tag(name = "Tasks", description = "Endpoints for managing tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @Operation(summary = "Get all tasks", description = "Returns a list of all tasks created by the authenticated user.")
    @ApiResponse(responseCode = "200", description = "List of tasks returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class)))
    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        log.info("Fetching all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @Operation(summary = "Get task by ID", description = "Returns a task by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        log.info("Fetching task with id: {}", id);
        return ResponseEntity.ok(taskService.getById(id));
    }

    @Operation(summary = "Create a new task", description = "Creates a task with provided title and details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(schema = @Schema(implementation = MethodArgumentNotValidException.class)))
    })
    @PostMapping
    public ResponseEntity<TaskDto> saveTask(@Valid @RequestBody TaskCreateDto taskDto) {
        log.info("Creating new task with title: {}", taskDto.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskDto));
    }

    @Operation(summary = "Update a task", description = "Updates the task by ID with new details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        log.info("Updating task with id: {}", id);
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Deleting task with id: {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}