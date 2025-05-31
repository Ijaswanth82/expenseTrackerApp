package com.jaswanth.ExpenseTrackingApp.service.impl;

import com.jaswanth.ExpenseTrackingApp.dto.ExpenseDto;
import com.jaswanth.ExpenseTrackingApp.exception.UserNotFoundException;
import com.jaswanth.ExpenseTrackingApp.model.Expense;
import com.jaswanth.ExpenseTrackingApp.model.User;
import com.jaswanth.ExpenseTrackingApp.repository.ExpenseRepository;
import com.jaswanth.ExpenseTrackingApp.repository.UserRepository;
import com.jaswanth.ExpenseTrackingApp.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Expense> getAllExpenses(User user) {
        System.out.println("inside expense service impl");
        return expenseRepository.findByUser(user);
    }

    @Override
    public Expense addExpense(Expense expense) {
        Optional<User> user = userRepository.findByUsername(expense.getUser().getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found with username: " + expense.getUser().getUsername());
        }
        expense.setUser(user.get());
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense updatedExpense, User user) {
        Expense existing = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found or unauthorized"));

        existing.setDescription(updatedExpense.getDescription());
        existing.setCategory(updatedExpense.getCategory());
        existing.setAmount(updatedExpense.getAmount());
        existing.setDate(updatedExpense.getDate());

        return expenseRepository.save(existing);
    }



    @Override
    public void deleteExpense(Long id, User user) {
        Expense expense = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found or unauthorized"));
        expenseRepository.delete(expense);
    }

    @Override
    public Map<String, Double> getTotalSpentByCategory(User user) {
        return expenseRepository.findByUser(user).stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    @Override
    public Map<Month, Double> getTotalSpentByMonth(User user) {
        return expenseRepository.findByUser(user).stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getDate().getMonth(),
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    @Override
    public Optional<Expense> getExpenseByIdAndUser(Long id, User user) {
        return expenseRepository.findByIdAndUser(id, user);
    }


    @Override
    public void deleteExpenseById(Long id) {
        expenseRepository.deleteById(id);
    }
}
