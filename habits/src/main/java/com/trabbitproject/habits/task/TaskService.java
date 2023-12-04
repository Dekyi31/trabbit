package com.trabbitproject.habits.task;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TaskService {
    @Autowired
    private TaskRepository repository;

    public Flux<Task> getAllUserTasks(ObjectId userId) {
        return repository.findByUserId(userId);
    }
    
    public Mono<Task> createUserTask(String description, LocalDateTime date, LocalDateTime dueDate, ObjectId userId) {
        Task task = new Task();
        task.setDescription(description);
        task.setDate(date);
        task.setDueDate(dueDate);
        task.setUserId(userId);
        return repository.save(task);
    }

    public Mono<Task> updateTask(ObjectId id, Task task) {
        return repository.findById(id)
            .flatMap(existingTask -> {
                existingTask.setDescription(task.getDescription());
                existingTask.setDate(task.getDate());
                existingTask.setCompletedDate(task.getCompletedDate());
                existingTask.setCompleted(task.isCompleted());
                existingTask.setUserId(task.getUserId());
                return repository.save(existingTask);
            });
    }

    public Mono<Void> deleteTask(ObjectId id) {
        return repository.deleteById(id);
    }

    public Flux<Task> createTask(String description, LocalDateTime date, LocalDateTime dueDate, ObjectId userId) {
        return null;
    }
}
