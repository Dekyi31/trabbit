package com.trabbitproject.habits.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import com.trabbitproject.habits.user.UserService;
import reactor.core.publisher.Mono;

@Component
// JwtAuthenticationFilter.java
public class JWTAuthenticationFilter implements WebFilter {
    
    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JWTService JWTService;

    public JWTAuthenticationFilter(UserService userService) {
        this.userDetailsService = userService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
      final String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
  
      String username = null;
      String jwt = null;
  
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
          jwt = authorizationHeader.substring(7);
          username = JWTService.extractUsername(jwt);
      }
  
      if (username != null) {
          UserDetails userDetails = userDetailsService.findByUsername(username).block();
  
          if (JWTService.isTokenValid(jwt, userDetails)) {
              UsernamePasswordAuthenticationToken authenticationToken =
                      new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  
              return ReactiveSecurityContextHolder.getContext()
                      .doOnSuccess(context -> context.setAuthentication(authenticationToken))
                      .then(chain.filter(exchange));
          }
      }
  

    return chain.filter(exchange);
}
}
