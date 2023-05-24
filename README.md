# Cocus Challenge

The Cocus Challenge is a sample project developed with Spring Webflux. It is a REST API that provides GitHub users and theirs repositories.

## Prerequisites

Before running the application, make sure you have the following prerequisites installed on your machine:

- Java Development Kit (JDK) version 17 or higher
- Gradle version 7.x

## Project Setup

Follow the steps below to set up the project on your local machine:

1. Clone this repository to your development environment:

```shell
git clone https://github.com/lucasstoller/cocus-challenge.git
```

2. Navigate to the project root directory:

```shell
cd CocusChallenge
```
3. Generate a GitHub personal access token:
- Go to your GitHub account settings
- Navigate to "Developer settings" > "Personal access tokens"
- Click on "Generate new token"
- Give the token a meaningful name and select the necessary scopes or permissions
- Click on "Generate token"
- Copy the generated token

4. Open the application.properties file located in src/main/resources:
- Replace the value of `github.token` with your generated GitHub token
- Save the file

## Running the Application

To run the application, follow these steps:

1. In the project's root directory, execute the following command to build the project:

```shell
./gradlew build
```

2. After the build command completes, you can run the application with the following command:

```shell
./gradlew bootRun
```

This will start the application, and it will be accessible locally at `http://localhost:8080`.

## Testing

The project includes automated tests to verify the proper functioning of components. To run the tests, use the following command:

```shell
./gradlew test
```

## Project Structure

The project directory structure follows the Spring Boot convention:

- `src/main/kotlin`: Contains the main application source code.
- `src/test/kotlin`: Contains the automated tests.
- `src/main/resources`: Contains static resources and configuration files, such as property files and static assets.

## Documentation

The API documentation can be accessed at `http://localhost:8080/swagger-ui.html` when the application is running. From there, you can view and test the available endpoints.

