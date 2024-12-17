package com.stgs.Moneymanager.repos;

import com.stgs.Moneymanager.models.Budget;
import com.stgs.Moneymanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BudgetRepo extends JpaRepository<Budget, Long> {

    List<Budget> findByUser(User user);

    // Find budgets by user and category
    List<Budget> findByUserAndCategory(User user, String category);

    // Find budgets that are active within a specific date range
    List<Budget> findByUserAndStartDateBeforeAndEndDateAfter(User user, LocalDate currentDate1, LocalDate currentDate2);
}
