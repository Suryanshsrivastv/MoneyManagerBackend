package com.stgs.Moneymanager.repos;

import com.stgs.Moneymanager.models.Expense;
import com.stgs.Moneymanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense,Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, String category);

    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}
