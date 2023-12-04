package com.trabbitproject.habits.user;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

// UserDetailsServiceImpl.java
@Service
public class UserService implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build()
                );
    }

    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<User> updateUser(ObjectId id, String name, String password, String username) {
        return userRepository.findById(id)
            .flatMap(user -> {
                user.setName(name);
                user.setPassword(passwordEncoder.encode(password)); // Note: you should hash the password before saving it
                user.setUsername(username);
                return userRepository.save(user);
            });
    }

    public Mono<User> updateUserAttribute(ObjectId id, String attribute, String value) {
        return userRepository.findById(id)
            .flatMap(user -> {
                switch (attribute) {
                    case "name":
                        user.setName(value);
                        break;
                    case "password":
                        user.setPassword(passwordEncoder.encode(value));
                        break;
                    case "username":
                        user.setUsername(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid attribute: " + attribute);
                }
                return userRepository.save(user);
            });
    }
}

