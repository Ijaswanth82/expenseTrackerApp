package com.jaswanth.ExpenseTrackingApp.service;

import com.jaswanth.ExpenseTrackingApp.dto.ExpenseDto;
import com.jaswanth.ExpenseTrackingApp.model.Expense;
import com.jaswanth.ExpenseTrackingApp.model.User;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getAllExpenses(User user);
    Expense addExpense(Expense expense);
    Expense updateExpense(Long id, Expense updatedExpense, User user);
    void deleteExpense(Long id, User user);
    Map<String, Double> getTotalSpentByCategory(User user);
    Map<Month, Double> getTotalSpentByMonth(User user);
    Optional<Expense> getExpenseByIdAndUser(Long id, User user);
    void deleteExpenseById(Long id) ;

}
