package com.jaswanth.ExpenseTrackingApp.dto;

import java.time.LocalDate;

public class ExpenseDto {
    private Long id;
    private String description;
    private String category;
    private Double amount;
    private LocalDate date;
    private Long userId;

    public ExpenseDto(Long id, String description, String category, Double amount, LocalDate date, Long userId) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }
}
