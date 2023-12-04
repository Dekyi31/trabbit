package com.trabbitproject.habits.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.trabbitproject.habits.user.UserService;
import reactor.core.publisher.Mono;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private ServerAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserService userDetailsService;
@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
public JWTAuthenticationFilter jwtAuthenticationFilter(UserService userDetailsService) {
        return new JWTAuthenticationFilter(userDetailsService);
}


@Bean
public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*"); // Allow all origins
        corsConfig.addAllowedMethod("*"); // Allow all methods
        corsConfig.addAllowedHeader("*"); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        http.cors(cors -> cors.configurationSource(source));
        JWTAuthenticationFilter jwtAuthenticationFilter = jwtAuthenticationFilter(userDetailsService);
        http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/auth/**").permitAll()
                        .anyExchange().authenticated())
                .csrf(csrf -> csrf.disable()
                        .exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                        .addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                        .logout(logout ->
                                logout.logoutUrl("/logout")
                                        .logoutSuccessHandler((exchange, authentication) -> {
                                            SecurityContextHolder.clearContext();
                                            return Mono.empty();
                                        })
                ));
        return http.build();
}

@Bean
public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
}

}

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
// import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.server.SecurityWebFilterChain;
// import reactor.core.publisher.Mono;

// import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// @EnableWebFluxSecurity
// public class SecurityConfig {

//     private final UserService userDetailsService;

//     public SecurityConfig(UserService userDetailsService) {
//         this.userDetailsService = userDetailsService;
//     }

//     @Bean
//     public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//         return http
//                 .authorizeExchange(exchange -> exchange
//                         .pathMatchers("/register").permitAll()
//                         .anyExchange().authenticated())
//                 .formLogin(withDefaults())
//                 .build();
//     }

//     @Primary
//     public ReactiveUserDetailsService userDetailsService() {
//         return userDetailsService;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }
