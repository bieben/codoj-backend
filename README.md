# CODOJ

## Overview

CODOJ is a coding platform that allows users to submit and judge coding problems. It is built using Java, Spring Boot, and Maven.

## Features

- User authentication and authorization
- Problem submission and management
- Code execution and judging
- Pagination for problem submissions

## Technologies

- Java
- Spring Boot
- Maven
- SQL

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- A SQL database (e.g., MySQL, PostgreSQL)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/codoj.git
    cd codoj
    ```

2. Configure the database connection in `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/codoj
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    ```

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

1. Access the application at `http://localhost:8080`.
2. Register a new user or log in with existing credentials.
3. Submit coding problems and view the results.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.
