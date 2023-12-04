package com.trabbitproject.habits.task;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users/tasks/")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/alltasks/")
    public Flux<Task> getUserTasks(@PathVariable ObjectId userId) {
        return taskService.getAllUserTasks(userId);
    }
}
