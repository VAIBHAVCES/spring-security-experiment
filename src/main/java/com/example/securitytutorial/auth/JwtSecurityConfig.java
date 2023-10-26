package com.example.securitytutorial.auth;

import com.example.securitytutorial.config.JwtAuthFilter;
import com.example.securitytutorial.models.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class JwtSecurityConfig {
    @Autowired
    JwtAuthEntryPoint jwtAuthEntryPoint;


    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf-> csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/auth/*").permitAll()
                                .requestMatchers("/admin/*").hasAnyRole(Roles.ADMIN.name())
                                .requestMatchers("/user/*").hasAnyRole(Roles.USER.name(), Roles.ADMIN.name())
                                .requestMatchers("/public/*").permitAll()
//                                .requestMatchers("/**").permitAll()
//                                .authenticated()
//                                .requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated()
//                                .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
