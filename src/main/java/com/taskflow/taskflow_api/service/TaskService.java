package com.taskflow.taskflow_api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.taskflow.taskflow_api.entity.Task;
import com.taskflow.taskflow_api.entity.Task.TaskStatus;
import com.taskflow.taskflow_api.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllWithUsers();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updated) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found: " + id));
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setStatus(updated.getStatus());
        task.setPriority(updated.getPriority());
        task.setDueDate(updated.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Map<String, Long> getTaskStats() {
        Map<String, Long> stats = new HashMap<>();
        for (TaskStatus status : TaskStatus.values()) {
            stats.put(status.name(), 0L);
        }
        taskRepository.countTasksByStatus().forEach(row -> {
            stats.put(row[0].toString(), (Long) row[1]);
        });
        return stats;
    }
}