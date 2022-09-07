package com.zetech.clearance.controller;

import com.zetech.clearance.exception.AppException;
import com.zetech.clearance.exception.ResourceNotFoundException;
import com.zetech.clearance.model.Role;
import com.zetech.clearance.model.User;
import com.zetech.clearance.model.student.Student;
import com.zetech.clearance.payload.*;
import com.zetech.clearance.repository.RoleRepository;
import com.zetech.clearance.repository.UserRepository;
import com.zetech.clearance.repository.student.StudentRepository;
import com.zetech.clearance.security.CurrentUser;
import com.zetech.clearance.security.JwtTokenProvider;
import com.zetech.clearance.security.UserPrincipal;
import com.zetech.clearance.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value="/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    StudentRepository studentRepository;

     @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    EmailService emailSender;


    @GetMapping("/user/me")
//    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),currentUser.getEmail(),userRepository.findById(currentUser.getId()).get().getRoleId());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/role/{id}")
    public List<User> findUsersByRoleId(@PathVariable Long roleId) {

        return  userRepository.findByRoleId(roleId);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));



        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName());

        return userProfile;
    }

    @PostMapping("/add/user")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(),1L);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findById(Long.parseLong(signUpRequest.getRoleId()))
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        user.setRoleId(Long.parseLong(signUpRequest.getRoleId()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();
        new Thread(new Runnable() {
            public void run() {
                String sendTo = result.getEmail();
                String subject = "Registration Confirmation";
                String emailBody = "Dear " + result.getName() + "," + "\n" + "\n" + "Your account on Zetech ERP has been created successfully. Your username is  " + result.getUsername() + " and your password is  " + signUpRequest.getPassword() + ".\n Please Log in to access our services."
                        + "\n\n" + "Regards" + "\n" + "Zetech University Admin";

                emailSender.sendMail(sendTo, subject, emailBody);

            }
        }).start();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }


    @GetMapping("/user/list")
    public List<UserResponse> findUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponse> userResponses=new ArrayList<>();
        users.forEach(user->{
            UserResponse response= new UserResponse();
            response.setEmail(user.getEmail());
            response.setId(user.getId());
            response.setName(user.getName());
            response.setUsername(user.getUsername());
            response.setRoleId(roleRepository.findById(user.getRoleId()).get().getName().name());
            userResponses.add(response);
        });
        return userResponses;
    }
    @GetMapping("/user/deletebyid/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        GeneralResponse generalResponse= new GeneralResponse();
        try{
            userRepository.deleteById(id);
            generalResponse.status="Success";
            generalResponse.description="Student Deleted Successfully";
        }catch(Exception e){
            generalResponse.status="Fail";
            generalResponse.description="Student Not Found";
        }
        return new ResponseEntity(generalResponse, HttpStatus.ACCEPTED);
    }
}
