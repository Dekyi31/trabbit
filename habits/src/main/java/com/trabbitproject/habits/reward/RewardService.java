package com.trabbitproject.habits.reward;

import java.time.LocalDate;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trabbitproject.habits.task.TaskRepository;
import reactor.core.publisher.Mono;

@Service
public class RewardService {
    private static final int TASK_THRESHOLD = 5;
    String messString = "Reward for completing 5 tasks";

    @Autowired
    private RewardRepository repository;

    @Autowired
    private TaskRepository taskRepository;


    public Mono<Reward> createRewardForCompletedTasks(ObjectId userId) {
        return taskRepository.countByUserIdAndCompletedAndDateBetween(userId, true, LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX))
            .flatMap(completedTasks -> {
                if (completedTasks >= TASK_THRESHOLD) {
                    Reward reward = new Reward();
                    reward.setDescription(messString);
                    reward.setUserId(userId);
                    return repository.save(reward);
                } else {
                    return Mono.empty();
                }
            });
            
    }
}
