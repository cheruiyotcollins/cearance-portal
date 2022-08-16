package com.zetech.clearance.controller;

import com.zetech.clearance.exception.ResourceNotFoundException;
import com.zetech.clearance.model.User;
import com.zetech.clearance.payload.UserIdentityAvailability;
import com.zetech.clearance.payload.UserProfile;
import com.zetech.clearance.payload.UserSummary;
import com.zetech.clearance.repository.UserRepository;
import com.zetech.clearance.security.CurrentUser;
import com.zetech.clearance.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
@CrossOrigin
public class UserController {
    @Autowired
    private UserRepository userRepository;


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
}
