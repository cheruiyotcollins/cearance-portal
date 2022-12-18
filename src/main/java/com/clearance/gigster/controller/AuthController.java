package com.clearance.gigster.controller;


import com.clearance.gigster.model.Role;
import com.clearance.gigster.model.User;
import com.clearance.gigster.model.student.Student;
import com.clearance.gigster.payload.ApiResponse;
import com.clearance.gigster.payload.JwtAuthenticationResponse;
import com.clearance.gigster.repository.RoleRepository;
import com.clearance.gigster.repository.UserRepository;
import com.clearance.gigster.repository.student.StudentRepository;
import com.clearance.gigster.service.EmailService;
import com.clearance.gigster.exception.AppException;
import com.clearance.gigster.payload.LoginRequest;
import com.clearance.gigster.payload.SignUpRequest;
import com.clearance.gigster.security.JwtTokenProvider;
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
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
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
                String emailBody = "Dear " + result.getName() + "," + "\n" + "\n" + "Your student account on Gigster ERP has been created successfully. Your username is  " + result.getUsername() + " and your password is  " + signUpRequest.getPassword() + ".\n Please Log in to access our services."
                        + "\n\n" + "Regards" + "\n" + "Gigster University Admin";

                emailSender.sendMail(sendTo, subject, emailBody);

            }
        }).start();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
