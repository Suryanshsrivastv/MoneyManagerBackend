package com.stgs.Moneymanager.service;

import com.stgs.Moneymanager.models.Budget;
import com.stgs.Moneymanager.models.User;
import com.stgs.Moneymanager.repos.BudgetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepo repo;

    @Autowired
    private UserService userService;
    // Add a new budget
    public Budget addBudget(Budget budget) {
        if (budget.getUser() == null || budget.getUser().getUsername() == null) {
            throw new IllegalArgumentException("User must be provided with a valid username.");
        }

        // Fetch persistent user entity
        User persistentUser = userService.getUserByUsername(budget.getUser().getUsername());
        budget.setUser(persistentUser);

        // Ensure no overlapping budgets for the same user and category
        List<Budget> existingBudgets = repo.findByUserAndCategory(persistentUser, budget.getCategory());
        boolean hasOverlap = existingBudgets.stream().anyMatch(existingBudget ->
                !(budget.getEndDate().isBefore(existingBudget.getStartDate())
                        || budget.getStartDate().isAfter(existingBudget.getEndDate()))
        );

        if (hasOverlap) {
            throw new IllegalArgumentException("Budget dates overlap with an existing budget!");
        }

        return repo.save(budget);
    }


    // Retrieve all budgets for a user
    public List<Budget> getBudgetsByUser(User user) {
        return repo.findByUser(user);
    }

    // Retrieve budgets active within a specific date range
    public List<Budget> getActiveBudgets(User user, LocalDate currentDate) {
        return repo.findByUserAndStartDateBeforeAndEndDateAfter(user, currentDate, currentDate);
    }

    // Delete a budget by ID
    public void deleteBudget(Long budgetId) {
        repo.deleteById(budgetId);
    }
}
