package com.stgs.Moneymanager.service;

import com.stgs.Moneymanager.models.Expense;
import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.repos.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo repo;

    // Add a new expense
    public Expense addExpense(Expense expense) {
        return repo.save(expense);
    }

    // Retrieve all expenses for a user
    public List<Expense> getExpensesByUser(User user) {
        return repo.findByUser(user);
    }

    // Retrieve expenses by category
    public List<Expense> getExpensesByCategory(User user, String category) {
        return repo.findByUserAndCategory(user, category);
    }

    // Retrieve expenses within a date range
    public List<Expense> getExpensesByDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return repo.findByUserAndDateBetween(user, startDate, endDate);
    }

    // Delete an expense by ID
    public void deleteExpense(Long expenseId) {
        repo.deleteById(expenseId);
    }
}
