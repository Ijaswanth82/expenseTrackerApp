package com.jaswanth.ExpenseTrackingApp.controller;


import com.jaswanth.ExpenseTrackingApp.dto.ExpenseDto;
import com.jaswanth.ExpenseTrackingApp.model.Expense;
import com.jaswanth.ExpenseTrackingApp.model.User;
import com.jaswanth.ExpenseTrackingApp.repository.UserRepository;
import com.jaswanth.ExpenseTrackingApp.service.ExpenseService;
import com.jaswanth.ExpenseTrackingApp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;



    @GetMapping
    public List<ExpenseDto> getAllExpenses(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        List<Expense> userExpenses = expenseService.getAllExpenses(userOpt.get());
        List<ExpenseDto> expenseDtos = userExpenses.stream()
                .map(expense -> new ExpenseDto(
                        expense.getId(),
                        expense.getDescription(),
                        expense.getCategory(),
                        expense.getAmount(),
                        expense.getDate(),
                        expense.getUser().getId()
                ))
                .collect(Collectors.toList());
        return expenseDtos;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ExpenseDto addExpense(@RequestBody Expense expense, HttpSession session, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("username: " + username);
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        expense.setUser(userOpt.get());
        Expense addedExpense = expenseService.addExpense(expense);
        return new ExpenseDto(
                addedExpense.getId(),
                addedExpense.getDescription(),
                addedExpense.getCategory(),
                addedExpense.getAmount(),
                addedExpense.getDate(),
                addedExpense.getUser().getId()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody Expense expenseDetails,HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Expense> optionalExpense = expenseService.getExpenseByIdAndUser(id, userOpt.get());
        if (!optionalExpense.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Expense expense = optionalExpense.get();

        // Update fields
        expense.setDescription(expenseDetails.getDescription());
        expense.setCategory(expenseDetails.getCategory());
        expense.setAmount(expenseDetails.getAmount());
        expense.setDate(expenseDetails.getDate());
        Expense updatedExpense = expenseService.addExpense(expense);
        ExpenseDto updatedExpenseDto=new ExpenseDto(updatedExpense.getId(),updatedExpense.getDescription(),updatedExpense.getCategory(),updatedExpense.getAmount(),updatedExpense.getDate(),updatedExpense.getUser().getId());
        return ResponseEntity.ok(updatedExpenseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id,HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        Optional<Expense> optionalExpense = expenseService.getExpenseByIdAndUser(id, userOpt.get());
        if (!optionalExpense.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}

