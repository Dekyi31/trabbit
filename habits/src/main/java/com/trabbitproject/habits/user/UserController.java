package com.trabbitproject.habits.user;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.trabbitproject.habits.task.Task;
import com.trabbitproject.habits.task.TaskService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public Mono<UserDetails> findByUsername(@PathVariable String id) {
        return userService.findByUsername(id);
    }

    @GetMapping("/{username}/tasks")
    public Flux<Task> getUserTasks(@PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (!username.equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.getUserByUsername(username)
            .flatMapMany(user -> taskService.getAllUserTasks(user.getUserId()));
    }
    
    @PostMapping("/{username}/create")
    public Mono<Task> createTask(@RequestParam String description, @RequestParam LocalDateTime date, @RequestParam LocalDateTime dueDate, @RequestParam boolean completed, @PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (!username.equals(userDetails.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
        }
        return userService.getUserByUsername(username)
            .flatMap(user -> taskService.createTask(description, date, dueDate, user.getUserId())
                .collectList()
                .flatMap(tasks -> Mono.justOrEmpty(tasks.stream().findFirst())));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("#username == authentication.principal.username")
    public Mono<User> updateUser(@PathVariable ObjectId id, @RequestParam String attribute, @RequestParam String value) {
        return userService.updateUserAttribute(id, attribute, value);
    }
}
