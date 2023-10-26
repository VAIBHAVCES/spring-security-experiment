package com.example.securitytutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/hello")
    public String hello(){
        return """
                <h1>PUBLIC</h1>
                <h2> Hello from a public url</h2>
                """;


    }
}
