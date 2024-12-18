package com.stgs.Moneymanager.controllers;


import com.stgs.Moneymanager.models.Budget;
import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.service.BudgetService;
import com.stgs.Moneymanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String home(){
        return "the api is up and runninggggg";
    }
    // Add a new budget
    @PostMapping
    public ResponseEntity<?> addBudget(@RequestBody @Valid Budget budget) {
        try {
            Budget savedBudget = budgetService.addBudget(budget);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Get all budgets for a user
    @GetMapping("/{username}")
    public ResponseEntity<?> getBudgetsByUser(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            List<Budget> budgets = budgetService.getBudgetsByUser(user);
            return ResponseEntity.ok(budgets);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // Get active budgets for a user
    @GetMapping("/{username}/active")
    public ResponseEntity<?> getActiveBudgets(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            List<Budget> activeBudgets = budgetService.getActiveBudgets(user, LocalDate.now());
            return ResponseEntity.ok(activeBudgets);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // Delete a budget by ID
    @DeleteMapping("/{budgetId}")
    public ResponseEntity<?> deleteBudget(@PathVariable Long budgetId) {
        try {
            budgetService.deleteBudget(budgetId);
            return ResponseEntity.ok("Budget deleted successfully.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
