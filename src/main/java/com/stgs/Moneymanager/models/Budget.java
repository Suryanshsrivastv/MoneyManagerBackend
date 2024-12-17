package com.stgs.Moneymanager.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Budget {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many budgets can belong to one user
    @JoinColumn(name = "user_id", nullable = false) // Foreign key column
    private User user;

    @Column(nullable = false) // Budget category (e.g., food, entertainment)
    private String category;

    @Column(nullable = false) // Allocated budget amount
    @JsonProperty("budgetAmount")
    @NotNull(message = "budget amount is required")
    private Double budgetAmount;

    @Column(nullable = false) // Start date of the budget
    private LocalDate startDate;

    @Column(nullable = false) // End date of the budget
    private LocalDate endDate;
}
