package com.bombk1n.grinberryplanner.repository;

import com.bombk1n.grinberryplanner.entity.TaskEntity;
import com.bombk1n.grinberryplanner.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUser(UserEntity user);
    Optional<TaskEntity> findByIdAndUser(Long id, UserEntity user);
}
