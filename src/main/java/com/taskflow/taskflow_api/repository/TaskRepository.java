package com.taskflow.taskflow_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.taskflow.taskflow_api.entity.Task;
import com.taskflow.taskflow_api.entity.Task.TaskStatus;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedToId(Long userId);

    @Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
    List<Object[]> countTasksByStatus();

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignedTo ORDER BY t.createdAt DESC")
    List<Task> findAllWithUsers();
}