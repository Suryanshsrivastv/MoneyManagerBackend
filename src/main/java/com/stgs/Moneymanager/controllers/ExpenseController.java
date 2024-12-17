package com.stgs.Moneymanager.controllers;

import com.stgs.Moneymanager.models.Expense;
import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.service.ExpenseService;
import com.stgs.Moneymanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    private UserService userService;

    @PostMapping
    public Expense addExpense(@RequestBody Map<String, Object> request) {
        // Extract user information and validate
        Map<String, Object> userMap = (Map<String, Object>) request.get("user");
        String username = (String) userMap.get("username");

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username is required");
        }

        // Fetch the user by username
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        // Extract other fields
        Double amount = Double.valueOf(request.get("amount").toString());
        String category = request.get("category").toString();
        String description = request.getOrDefault("description", "").toString();
        LocalDate date = LocalDate.parse(request.get("date").toString());

        // Create the Expense entity
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDescription(description);
        expense.setDate(date);

        // Save the expense
        return service.addExpense(expense);
    }

    // Get all expenses for a user
    @GetMapping("/{username}")
    public List<Expense> getExpensesByUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return service.getExpensesByUser(user);
    }

    // Get expenses by category
    @GetMapping("/{username}/category/{category}")
    public List<Expense> getExpensesByCategory(@PathVariable String username, @PathVariable String category) {
        User user = userService.getUserByUsername(username);
        return service.getExpensesByCategory(user, category);
    }

    // Get expenses by date range
    @GetMapping("/{username}/date")
    public List<Expense> getExpensesByDateRange(
            @PathVariable String username,
            @RequestParam String startDate,
            @RequestParam String endDate
    ) {
        User user = userService.getUserByUsername(username);
        return service.getExpensesByDateRange(user, LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    // Delete an expense by ID
    @DeleteMapping("/{expenseId}")
    public void deleteExpense(@PathVariable Long expenseId) {
        service.deleteExpense(expenseId);
    }
}
