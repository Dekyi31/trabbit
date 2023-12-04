package com.trabbitproject.habits.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        // Get the HTTP response
        ServerHttpResponse response = exchange.getResponse();

        // Set the response status code to 401 Unauthorized
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        // Create a JSON error message
        Map<String, Object> error = new HashMap<>();
        error.put("message", "Unauthorized");
        error.put("timestamp", LocalDateTime.now());

        // Convert the error message to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] errorBytes;
        try {
            errorBytes = objectMapper.writeValueAsBytes(error);
        } catch (IOException ex) {
            errorBytes = new byte[0];
        }

        // Create a DataBuffer from the error bytes
        DataBuffer buffer = response.bufferFactory().wrap(errorBytes);

        // Write the error message to the response
        return response.writeWith(Mono.just(buffer));
    }
}