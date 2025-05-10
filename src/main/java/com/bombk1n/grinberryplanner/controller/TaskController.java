package com.bombk1n.grinberryplanner.controller;

import com.bombk1n.grinberryplanner.dto.TaskCreateDto;
import com.bombk1n.grinberryplanner.dto.TaskDto;
import com.bombk1n.grinberryplanner.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("api/todo")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        log.info("Fetching all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        log.info("Fetching task with id: {}", id);
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> saveTask(@Valid @RequestBody TaskCreateDto taskDto) {
        log.info("Creating new task with title: {}", taskDto.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
        log.info("Updating task with id: {}", id);
        return ResponseEntity.ok(taskService.update(id, taskDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.info("Deleting task with id: {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}