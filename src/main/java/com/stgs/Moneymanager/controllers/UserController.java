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
        return service.registerUser(user);
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return service.getUserByUsername(username);
    }

}
