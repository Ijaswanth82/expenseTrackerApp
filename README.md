# Expense Tracker App

A simple backend-only RESTful service for managing expenses.

## Features

- User Registration and Login with JWT Authentication  
- Password hashing with BCrypt for secure password storage  
- CRUD operations for Expenses: Add, Get, Update, Delete  
- MySQL used as the database  
- Six REST endpoints covering user and expense management  

## Endpoints

| Method | Endpoint           | Description                   |
|--------|--------------------|-------------------------------|
| POST   | /api/auth/register | Register a new user            |
| POST   | /api/auth/login    | User login, returns JWT token |
| POST   | /api/expenses      | Add a new expense             |
| GET    | /api/expenses      | Get all expenses for user     |
| PUT    | /api/expenses/{id} | Update an existing expense    |
| DELETE | /api/expenses/{id} | Delete an expense             |

## Technologies Used

- Java 21 / Spring Boot  
- Spring Security with JWT  
- MySQL Database  
- Hibernate / JPA  
- BCrypt password encoder  

## Setup Instructions

### Prerequisites

- Java 17 or above installed  
- Maven installed  
- MySQL installed and running  
- Postman or any REST client for testing APIs

### Database Setup  

- Create a new database in MySQL:  
  &nbsp;CREATE DATABASE expense_tracker_db;  
- Create a MySQL user with privileges or use your existing user.  
- Update src/main/resources/application.properties (or .yml) with your MySQL credentials:  
  &nbsp;spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker_db  
  &nbsp;spring.datasource.username=your_mysql_username  
  &nbsp;spring.datasource.password=your_mysql_password  
  &nbsp;spring.jpa.hibernate.ddl-auto=update  
  &nbsp;spring.jpa.show-sql=true  
  &nbsp;spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect  


### Build and Run  
1.Clone the repo   
   git clone https://github.com/Ijaswanth82/expenseTrackerApp.git  
   cd expenseTrackerApp  
2.Build the project using Maven:  
   mvn clean install  
3.Run the application  
   mvn spring-boot:run  


### Usage / API Examples  
1.Register User  
POST /api/auth/register  
Request body:  
{  
  "username": "user@example.com",  
  "password": "your_password"  
}  


2.Login User  
POST /api/auth/login  
Request body:  
{  
  "username": "user@example.com",  
  "password": "your_password"  
}  
Response:  
{  
  "token": "your_jwt_token_here"  
}  


3.Add Expense  
POST /api/expenses  
Headers:  
Authorization: Bearer your_jwt_token_here  
Request body:  
{  
  "description": "Grocery shopping",  
  "category": "Food",  
  "amount": 1500,  
  "date": "2025-05-31"  
}  


4.Get Expenses  
GET /api/expenses  
Headers:  
Authorization: Bearer your_jwt_token_here  


5.Update Expense  
PUT /api/expenses/{expenseId}  
Headers:  
Authorization: Bearer your_jwt_token_here  
Request body:  
{  
  "description": "Updated description",  
  "category": "Updated category",  
  "amount": 2000,  
  "date": "2025-06-01"  
}  


6.Delete Expense  
DELETE /api/expenses/{expenseId}  
Headers:  
Authorization: Bearer your_jwt_token_here  


