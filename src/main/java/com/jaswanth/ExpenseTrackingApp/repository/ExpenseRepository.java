package com.jaswanth.ExpenseTrackingApp.repository;

import com.jaswanth.ExpenseTrackingApp.model.Expense;
import com.jaswanth.ExpenseTrackingApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUser(User user);

    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Expense> findByUserAndCategory(User user, String category);


    Optional<Expense> findByIdAndUser(Long id, User user);

}
