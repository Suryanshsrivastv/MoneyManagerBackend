package com.stgs.Moneymanager.controllers;

import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){
         try {
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    } catch (DataIntegrityViolationException e) {
        // Handle duplicate entry error from the database (for username or email)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or email already exists!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
    }
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return service.getUserByUsername(username);
    }

}
