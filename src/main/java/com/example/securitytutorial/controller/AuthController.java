package com.example.securitytutorial.controller;

import com.example.securitytutorial.auth.JwtHelper;
import com.example.securitytutorial.models.JwtRequest;
import com.example.securitytutorial.models.JwtResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtHelper jwtHelper;


    @GetMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder().username(request.getEmail())
                .token(token)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public void doAuthenticate(String email, String pwd) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);
        } catch (BadCredentialsException e) {
            log.info("Bad Credentials !! {}", e.getMessage());
            throw e;
        } catch (Exception e){
            log.info("SOme failure detected : {}",e.getMessage());
            throw  e;
        }

    }

}
