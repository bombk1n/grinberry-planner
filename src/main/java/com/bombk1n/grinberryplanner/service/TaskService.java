package com.bombk1n.grinberryplanner.service;

import com.bombk1n.grinberryplanner.dto.TaskCreateDto;
import com.bombk1n.grinberryplanner.dto.TaskDto;
import com.bombk1n.grinberryplanner.entity.TaskEntity;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import com.bombk1n.grinberryplanner.enums.TaskType;
import com.bombk1n.grinberryplanner.exceptions.TaskNotFoundException;
import com.bombk1n.grinberryplanner.repository.TaskRepository;
import com.bombk1n.grinberryplanner.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        List<TaskEntity> taskEntities = taskRepository.findByUser(user);
        return taskEntities.stream()
                .map(this::convertTaskEntityToDto)
                .toList();
    }

    public TaskDto getById(Long id) {
        UserEntity user = getCurrentUser();
        return taskRepository.findByIdAndUser(id, user)
                .map(this::convertTaskEntityToDto)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public TaskDto save(TaskCreateDto taskCreateDto) {
        UserEntity user = getCurrentUser();
        TaskEntity taskEntity = convertCreateDtoToTaskEntity(taskCreateDto);
        taskEntity.setCreatedAt(LocalDateTime.now());
        taskEntity.setStatus(taskCreateDto.getStatus() != null ? taskCreateDto.getStatus() : TaskType.IN_PROGRESS);
        taskEntity.setUser(user);
        TaskEntity savedTask = taskRepository.save(taskEntity);
        return convertTaskEntityToDto(savedTask);
    }

    public TaskDto update(Long id, TaskDto taskDto) {
        UserEntity user = getCurrentUser();
        TaskEntity existingTask = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found for user " + user.getUsername()));

        existingTask.setTitle(taskDto.getTitle());
        existingTask.setDescription(taskDto.getDescription());
        existingTask.setStatus(taskDto.getStatus());
        existingTask.setDeadline(taskDto.getDeadline());

        TaskEntity updatedTask = taskRepository.save(existingTask);
        return convertTaskEntityToDto(updatedTask);
    }

    public void delete(Long id) {
        UserEntity user = getCurrentUser();
        TaskEntity existingTask = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + id + " not found for user " + user.getUsername()));
        taskRepository.delete(existingTask);
    }

    private UserEntity getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new UsernameNotFoundException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
