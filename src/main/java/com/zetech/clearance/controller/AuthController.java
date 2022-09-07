package com.zetech.clearance.controller;


import com.zetech.clearance.exception.AppException;
import com.zetech.clearance.model.Role;
import com.zetech.clearance.model.User;
import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.ApiResponse;
import com.zetech.clearance.payload.LoginRequest;
import com.zetech.clearance.payload.SignUpRequest;
import com.zetech.clearance.repository.RoleRepository;
import com.zetech.clearance.repository.UserRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import com.zetech.clearance.security.JwtTokenProvider;
import com.zetech.clearance.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    EmailService emailSender;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new com.example.polls.payload.JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), 1L);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findById(2L)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setRoleId(2L);

        User result = userRepository.save(user);
        Student student = studentRepository.findByRegNo(signUpRequest.getRegNo());
        student.setUserId(result.getId());
        studentRepository.save(student);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        new Thread(new Runnable() {
            public void run() {
                String sendTo = result.getEmail();
                String subject = "Registration Confirmation";
                String emailBody = "Dear " + result.getName() + "," + "\n" + "\n" + "Your student account on Zetech ERP has been created successfully. Your username is  " + result.getUsername() + " and your password is  " + signUpRequest.getPassword() + ".\n Please Log in to access our services."
                        + "\n\n" + "Regards" + "\n" + "Zetech University Admin";

                emailSender.sendMail(sendTo, subject, emailBody);

            }
        }).start();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
