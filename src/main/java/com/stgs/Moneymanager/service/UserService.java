package com.stgs.Moneymanager.service;

import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.repos.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public User registerUser(User user) {

       return repo.save(user);
    }

    public User getUserByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
