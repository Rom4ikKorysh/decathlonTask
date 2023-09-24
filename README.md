# Notepad Application

This is a simple notepad application built using Spring Boot in Java 17. The application allows you to manage notes, including adding new notes, deleting existing notes, editing notes, searching for notes by title, and displaying the list of notes.

## Table of Contents

- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
    - [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Contributing](#contributing)

## Technologies Used

- Java 17
- Spring Boot
- PostgreSQL (as the database)
- Log4j (for logging)
- JUnit and Mockito (for testing)
- Postman (for testing)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17 installed on your system.
- [Spring Boot](https://spring.io/projects/spring-boot) installed.
- [PostgreSQL](https://www.postgresql.org/) installed and running.
- [Postman](https://www.postman.com/) installed (for testing).

### Installation

1. Clone the repository from GitHub: git clone <https://github.com/Rom4ikKorysh/notepad-application.git>
2. Navigate to the project directory: cd notepad-application
3. Build the application:./mvnw clean package
4. Run the application: ./mvnw spring-boot:run

The application should now be running locally.

### Usage

## API Endpoints

Get All Notes
Endpoint: /notes
Method: GET
Description: Retrieves a list of all notes.
Response: List of notes.

Add New Note
Endpoint: /notes
Method: POST
Description: Creates a new note.
Request Body: JSON with title and content fields.
Response: HTTP 201 Created.

Delete Note by ID
Endpoint: /notes/{id}
Method: DELETE
Description: Deletes a note by its ID.
Response: HTTP 204 No Content.

Edit Note Content
Endpoint: /notes/{id}
Method: PUT
Description: Updates the content of a note by its ID.
Request Body: JSON with title and content fields.
Response: Updated note.

Search Notes by Title
Endpoint: /notes/findByTitle
Method: GET
Description: Retrieves notes containing the specified title.
Query Parameter: title (string)
Response: List of matching notes.

## Testing

You can use Postman or any other API testing tool to test the endpoints of this application. Import the provided Postman collection to get started with testing.
Additionally, the application includes a comprehensive set of JUnit tests with Mockito for the `NoteService` class to ensure the functionality of your notepad application.
These tests cover various scenarios, including finding notes by ID, creating new notes, deleting notes, updating notes, searching by title, and more.
You can find the test class in the following location: `src/test/java/com/example/decathlonTask/service/NoteServiceTest.java`.

### Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please create a GitHub issue or submit a pull request.
