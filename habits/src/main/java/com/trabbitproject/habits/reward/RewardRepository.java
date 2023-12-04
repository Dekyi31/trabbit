package com.trabbitproject.habits.reward;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RewardRepository extends ReactiveMongoRepository<Reward, ObjectId>{
    Flux<Reward> findByUserId(ObjectId userId, Pageable pageable);
}
