package com.trabbitproject.habits.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trabbitproject.habits.user.User;
import com.trabbitproject.habits.user.UserRepository;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> createAuthenticationToken(@RequestBody Map<String, String> authenticationRequest) {
        return authenticate(authenticationRequest.get("username"), authenticationRequest.get("password"))
                .flatMap(authentication -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String token = jwtUtil.generateToken(userDetails);
                    return Mono.just(ResponseEntity.ok().body(token));
                })
                .onErrorResume(UsernameNotFoundException.class, error -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<Object>> registerUser(@RequestBody Map<String, String> registrationRequest) {
        return userRepository.findByUsername(registrationRequest.get("username"))
                .flatMap(existingUser -> Mono.just(ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build()))
                .switchIfEmpty(createUserAndGenerateToken(registrationRequest));
    }

    private Mono<Authentication> authenticate(String username, String password) {
        return Mono.defer(() -> Mono.just(new UsernamePasswordAuthenticationToken(username, password)))
                .flatMap(authenticationManager::authenticate)
                .cast(Authentication.class)
                .onErrorResume(e -> Mono.error(new UsernameNotFoundException("Invalid credentials")));
    }

    private Mono<ResponseEntity<Object>> createUserAndGenerateToken(Map<String, String> registrationRequest) {
        User newUser = new User();
        newUser.setName(registrationRequest.get("name"));
        newUser.setEmail(registrationRequest.get("email"));
        newUser.setUsername(registrationRequest.get("username"));
        newUser.setPassword(passwordEncoder.encode(registrationRequest.get("password")));

        return userRepository.save(newUser)
            .flatMap(savedUser -> {
                String token = jwtUtil.generateToken(savedUser.getUsername());
                return Mono.just(ResponseEntity.ok(token));
            });
    }
}


// @RestController
// @RequestMapping("/")
// public class AuthController {
    
//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;

//     public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//     }

//     @PostMapping("/register")
//     public Mono<ResponseEntity<String>> registerUser(@RequestBody Map<String, String> registrationRequest) {
//         String username = registrationRequest.get("username");
//         String password = registrationRequest.get("password");

//         if (username == null || password == null) {
//             return Mono.just(ResponseEntity.badRequest().body("Username and password are required"));
//         }

//         return userRepository.findByUsername(username)
//                 .flatMap(existingUser -> Mono.just(ResponseEntity.badRequest().body("Username already exists")))
//                 .switchIfEmpty(userRepository.save(createUser(username, password))
//                         .map(savedUser -> ResponseEntity.ok("Registration successful"))
//                 );
// }

// private User createUser(String username, String password) {
//     User newUser = new User();
//     newUser.setUsername(username);
//     newUser.setPassword(passwordEncoder.encode(password));
//     // Set other user details as needed
//     return newUser;
// }

// }
