package com.clearance.gigster.controller;

import com.clearance.gigster.exception.AppException;
import com.clearance.gigster.model.Role;
import com.clearance.gigster.model.User;
import com.clearance.gigster.model.student.Student;
import com.clearance.gigster.payload.ApiResponse;
import com.clearance.gigster.payload.GeneralResponse;
import com.clearance.gigster.payload.SignUpRequest;
import com.clearance.gigster.repository.RoleRepository;
import com.clearance.gigster.repository.UserRepository;
import com.clearance.gigster.repository.student.StudentRepository;
import com.clearance.gigster.security.JwtTokenProvider;
import com.clearance.gigster.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping(value="/api/admin")
public class AdminController {

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


    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRoleId());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findById(signUpRequest.getRoleId())
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setRoleId(signUpRequest.getRoleId());

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        new Thread(new Runnable() {
            public void run() {
                String sendTo = result.getEmail();
                String subject = "Registration Confirmation";
                String emailBody = "Dear " + result.getName() + "," + "\n" + "\n" + "Your User account on Gigster ERP has been created successfully. Your username is  " + result.getUsername() + " and your password is  " + signUpRequest.getPassword() + ".\n Please Log in to access our services."
                        + "\n\n" + "Regards" + "\n" + "Gigster University Admin";

                emailSender.sendMail(sendTo, subject, emailBody);

            }
        }).start();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){

        GeneralResponse generalResponse= new GeneralResponse();
        try{
            userRepository.deleteById(id);
            generalResponse.setStatus("Success");
            generalResponse.setDescription("User Deleted Successfully");
        }catch (Exception e){
            generalResponse.setStatus("Fail");
            generalResponse.setDescription("User not found");
        }
           return new ResponseEntity(generalResponse,HttpStatus.ACCEPTED);
    }
}
