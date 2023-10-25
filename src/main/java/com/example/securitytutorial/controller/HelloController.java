package com.example.securitytutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        log.info("Hello from controller layer with auth in security context as : {}", SecurityContextHolder.getContext().getAuthentication());

        return "hello from vaibhav";
    }


    @GetMapping("/current-user")
    public String currentUser(Principal principal){
        log.info("Also logging the current principal obj: {}", principal);
        return principal.getName();

    }
}
