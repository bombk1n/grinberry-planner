# Grinberry Planner

Grinberry Planner is a Java-based Spring Boot REST API designed to help users efficiently plan and manage events, tasks, or schedules with secure access and modern best practices. The project includes a `docker-compose.yml` file for streamlined deployment using Docker.

## Features

- User registration and authentication
- JWT-based authentication and authorization
- Role-based access control (e.g., Admin, User)
- RESTful API endpoints for planning and scheduling resources
- Integration with a relational database via Spring Data JPA
- Docker support for easy containerized deployment

## Technologies Used

- Java (Spring Boot)
- Spring Security
- Spring Data JPA
- JWT (JSON Web Tokens)
- Docker & Docker Compose
- Relational Database (e.g., MySQL)
- Flyway

## Prerequisites

Make sure you have the following installed before you begin:

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

## Running the Application with Docker Compose

To run Grinberry Planner and its dependencies using Docker Compose:

### 1. Clone the Repository

```bash
git clone https://github.com/bombk1n/grinberry-planner.git
cd grinberry-planner
```

### 2. Build the Docker Images

```bash
docker-compose build
```

### 3. Start the Application

```bash
docker-compose up
```

This will start the following services:
- **Grinberry Planner API**: Accessible at `http://localhost:8080`
- **Database**  (PostgreSQL or other, as configured): Available within the Docker network

## API Endpoints

### Authentication (`/api/auth`)

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| POST   | /api/auth/register | Register new user | [JSON](#register) |
| POST   | /api/auth/login | Login user | [JSON](#login) |

### Users (`/api/user`)

| Method | Url | Decription | Sample Valid Request Body | 
| ------ | --- | ---------- | --------------------------- |
| PUT   | /api/user | Update current user profile | [JSON](#userupdate) |
| GET   | /api/auth/profile | Get current user profile | - |

### Tasks (`/api/todo`)

| Method | Url | Description | Sample Valid Request Body |
| ------ | --- | ----------- | -------------------------- |
| GET    | /api/todo/{id} | Get task by ID | - |
| PUT    | /api/todo/{id} | Update a task | [JSON](#taskupdate) |
| DELETE | /api/todo/{id} | Delete a task - |
| GET    | /api/todo | Get all tasks | - |
| POST   | /api/todo | Create a new task | [JSON](#taskcreate) |

##### <a id="register">Register -> /api/auth/register</a>
```json
{
    "username": "johnDoe123",
    "password": "securePassword123",
}
```

##### <a id="login">Login -> /api/auth/login</a>
```json
{
    "username": "johnDoe123",
    "password": "securePassword123"
}
```

##### <a id="userupdate">Update Current User Profile -> /api/user</a>
```json
{
    "username": "newUsername",
    "oldPassword": "oldPassword123",
    "newPassword": "newPassword321"
}
```

##### <a id="ctaskcreate">Create Task -> /api/todo</a>
```json
{
    "title": "Finish Homework",
    "description": "Complete all exercises from the Java book.",
    "status": "IN_PROGRESS",
    "deadline": "2025-05-20T15:00:00"
    "userId": 3
}
```

##### <a id="taskupdate">Update Task -> /api/todo/{id}</a>
```json
{
    "title": "Updated Task Title",
    "description": "Updated task description.",
    "status": "COMPLETED",
    "deadline": "2025-05-25T12:00:00"
}
```

## Project Structure

![Project Structure](https://github.com/user-attachments/assets/be9de6ee-b769-48b4-8194-f3d403360a4d)

<sub>Project structure diagram</sub>


