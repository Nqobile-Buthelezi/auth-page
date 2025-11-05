# Auth Page Project

This is an authentication microservice built with Java and Maven.

## Prerequisites

Before you begin, ensure you have the following installed:

- [Java 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

## Cloning the Repository

To get started, clone this repository:

```sh
git clone https://github.com/Nqobile-Buthelezi/auth-page.git
cd auth-page/auth-project/
```

## Build the repository

To build the repository

```sh
mvn clean compile
```

## Run the program

to execute the program run the App main class or run

```sh
mvn exec:java -Dexec.mainClass="za.co.bangoma.auth.App"
```

## Testing

The project includes a comprehensive test suite with 47 tests covering:
- Model classes (User, LoginCredentials, SignUpCredentials)
- Infrastructure components (UserValidator)
- Service layer (CredentialService, Command patterns)
- Application configuration

### Run all tests

```sh
mvn test
```

### Run specific test class

```sh
mvn test -Dtest=UserTest
mvn test -Dtest=LoginCredentialsTest
```

### Test Coverage
- **47 total tests**
- Model tests: 29 tests
- Infrastructure tests: 7 tests
- Service tests: 5 tests
- Configuration tests: 6 tests

All tests follow JUnit 5 best practices with comprehensive edge case coverage.

