package com.trabbitproject.habits.task;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, ObjectId>{
    Flux<Task> findByUserId(ObjectId userId);
    Mono<Task> findById(ObjectId id);
    Mono<Void> deleteById(ObjectId id);
    Mono<Integer> countByUserIdAndCompletedAndDateBetween(ObjectId userId, boolean completed, LocalDateTime atStartOfDay,
            LocalDateTime atTime);
}
