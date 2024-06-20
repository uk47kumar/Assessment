# Mykarsol-Technologies Spring Boot Application

## Overview

This project is a Spring Boot application that demonstrates the implementation of various functionalities including CRUD operations, validation, logging, error handling, email notifications, and a user interface for managing articles and authors.

## Features

- **CRUD Operations**: Create, Read, Update, and Delete operations for `Article` and `Author` entities.
- **Logging**: Application events and errors are logged using Log4j2.
- **Error Tracking and Management**: Global exception handling to manage different types of exceptions.
- **Email Service Integration**: Email notifications for critical events like errors and successful operations.
- **Database Design**: Relational database schema with entities `Article` and `Author`.
- **User Interface**: Web interface for adding, updating, viewing, and listing articles and authors.

## Database Table Design

### Author Table
| Column | Type          | Constraints               |
|--------|---------------|---------------------------|
| id     | Long          | Primary Key, Auto-increment|
| name   | String        | Not null                  |

### Article Table
| Column      | Type          | Constraints                    |
|-------------|---------------|--------------------------------|
| id          | Long          | Primary Key, Auto-increment    |
| title       | String        | Not null                       |
| description | String        | Not null                       |
| publishDate | Date          |                                |
| status      | String        |                                |
| banner      | String        |                                |
| author_id   | Long          | Foreign Key, references Author |


# Database Configuration
The application uses an in-memory H2 database, which is configured in the application.properties file as follows:

# H2 Database configuration
spring.datasource.url=jdbc:h2:mem:test

spring.h2.console.enabled=true

# JPA configuration
spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true

# You can access the H2 database console at: 
http://localhost:8080/h2-console


# Exception Handling
The application includes comprehensive exception handling to manage various types of exceptions gracefully.

Global Exception Handling
Global exceptions are handled by the ApiException class, which manages all types of exceptions that can occur within the application. This ensures that any unexpected errors are caught and a meaningful response is provided to the client.

Resource Not Found Exception
The ResourceNotFoundException is used to handle cases where a requested resource is not found. This specific exception is caught and handled to return a 404 Not Found status along with a descriptive message.


## User Interface Pages

### Add Page
Form to add a new article with fields:
- Title
- Description
- Publish Date
- Status (Dropdown)
- Banner
- Author (Dropdown)

### Update Page
Form to update an existing article with pre-filled fields.

### View Page
Page to view the details of a specific article.

### List Page
Page to list all articles with options to view, edit, or delete each article.

## Setup and Configuration

### Prerequisites
- Java 17
- Maven
- Email SMTP server configuration

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/uk47kumar/Assessment.git
   cd Assessment
