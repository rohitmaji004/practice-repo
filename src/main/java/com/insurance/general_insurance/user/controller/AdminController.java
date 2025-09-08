package com.insurance.general_insurance.user.controller;


import com.insurance.general_insurance.user.entity.User;
import com.insurance.general_insurance.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User with ID " + userId + " deleted successfully.");
        } catch (Exception e) {
            // It's better to return a 404 Not Found if the user doesn't exist
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public  ResponseEntity<?> getAllUsers(){
        List<User> users =  userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
