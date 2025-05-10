package com.bombk1n.grinberryplanner.service;

import com.bombk1n.grinberryplanner.dto.TaskCreateDto;
import com.bombk1n.grinberryplanner.dto.TaskDto;
import com.bombk1n.grinberryplanner.entity.TaskEntity;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import com.bombk1n.grinberryplanner.enums.TaskType;
import com.bombk1n.grinberryplanner.exceptions.TaskNotFoundException;
import com.bombk1n.grinberryplanner.repository.TaskRepository;
import com.bombk1n.grinberryplanner.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TaskService {
    private final ModelMapper modelMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(ModelMapper modelMapper, TaskRepository taskRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDto> getAllTasks() {
        UserEntity user = getCurrentUser();
        log.info("Fetching all tasks for user: {}", user.getUsername());
        List<TaskEntity> taskEntities = taskRepository.findByUser(user);
        return taskEntities.stream()
                .map(this::convertTaskEntityToDto)
                .toList();
    }

    public TaskDto getById(Long id) {
        UserEntity user = getCurrentUser();
        log.info("Fetching task with id: {} for user: {}", id, user.getUsername());
        return taskRepository.findByIdAndUser(id, user)
                .map(this::convertTaskEntityToDto)
                .orElseThrow(() -> {
                    log.error("Task with id: {} not found for user: {}", id, user.getUsername());
                    return new TaskNotFoundException("Task with id: " + id + " not found");
                });
    }

    public TaskDto save(TaskCreateDto taskCreateDto) {
        UserEntity user = getCurrentUser();
        log.info("Saving new task for user: {}", user.getUsername());
        TaskEntity taskEntity = convertCreateDtoToTaskEntity(taskCreateDto);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setStatus(taskCreateDto.getStatus() != null ? taskCreateDto.getStatus() : TaskType.IN_PROGRESS);
        taskEntity.setUser(user);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        log.info("Task saved successfully for user: {}", user.getUsername());
        return convertTaskEntityToDto(savedTask);
    }

    public TaskDto update(Long id, TaskDto taskDto) {
        UserEntity user = getCurrentUser();
        log.info("Updating task with id: {} for user: {}", id, user.getUsername());
        TaskEntity existingTask = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found for user: {}", id, user.getUsername());
                    return new TaskNotFoundException("Task with ID " + id + " not found for user " + user.getUsername());
                });

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setDeadline(taskDto.getDeadline());

        TaskEntity updatedTask = taskRepository.save(existingTask);
        log.info("Task with id: {} updated successfully for user: {}", id, user.getUsername());
        return convertTaskEntityToDto(updatedTask);
    }

    public void delete(Long id) {
        UserEntity user = getCurrentUser();
        log.info("Deleting task with id: {} for user: {}", id, user.getUsername());
        TaskEntity existingTask = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> {
                    log.error("Task with ID: {} not found for user: {}", id, user.getUsername());
                    return new TaskNotFoundException("Task with ID " + id + " not found for user " + user.getUsername());
                });
        taskRepository.delete(existingTask);
        log.info("Task with id: {} deleted successfully for user: {}", id, user.getUsername());
    }

    private UserEntity getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            log.error("User is not authenticated");
            throw new UsernameNotFoundException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User not found: {}", userDetails.getUsername());
                    return new UsernameNotFoundException("User not found");
                });
    }

    private TaskDto convertTaskEntityToDto(TaskEntity taskEntity) {
        TaskDto taskDto = modelMapper.map(taskEntity, TaskDto.class);
        if (taskEntity.getUser() != null) {
            taskDto.setUserId(taskEntity.getUser().getId());
        }
        return taskDto;
    }

    private TaskEntity convertCreateDtoToTaskEntity(TaskCreateDto taskCreateDto) {
        return modelMapper.map(taskCreateDto, TaskEntity.class);
    }
}