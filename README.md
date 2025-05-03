# Task API Documentation

This document describes the endpoints available in the Task and User APIs.

## Base URLs

- `/api/task`
- `/api/user`

## Task API Endpoints

### 1. GET /api/task/

- **Description:** Retrieves a list of all tasks.
- **HTTP Method:** `GET`
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON array of `Task` objects.

### 2. GET /api/task/admin/search/id/{id}

- **Description:** Retrieves a specific task by its ID (Admin access).
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{id}`: The ID of the task to retrieve (Long).
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON object representing the `Task`, or a 404 Not Found if the task does not exist.

### 3. POST /api/task/admin/search/title

- **Description:** Retrieves a task by its title (Admin access).
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:**
    ```json
    {
      "title": "Task Title" (String, required)
    }
    ```
- **Response Body:** A JSON object representing the `Task`, or a 404 Not Found if the task does not exist.

### 4. GET /api/task/search/user

- **Description:** Retrieves all tasks belonging to the authenticated user.
- **HTTP Method:** `GET`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:** None
- **Response Body:** A JSON array of `Task` objects associated with the user identified by the Bearer Token.

### 5. GET /api/task/admin/search/status/{done}

- **Description:** Retrieves tasks based on their completion status (Admin access).
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{done}`: The completion status to filter by (Boolean, `true` for completed, `false` for incomplete).
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON array of `Task` objects matching the specified status.

### 6. GET /api/task/search/status/{done}

- **Description:** Retrieves tasks belonging to the authenticated user based on their completion status.
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{done}`: The completion status to filter by (Boolean, `true` for completed, `false` for incomplete).
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:** None
- **Response Body:** A JSON array of `Task` objects belonging to the authenticated user that match the specified status.

### 7. POST /api/task/add

- **Description:** Adds a new task for the authenticated user.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:**
    ```json
    {
      "title": "New Task Title" (String, required),
      "description": "Task Description" (String, optional)
    }
    ```
- **Response Body:** None (successful operation typically returns a 2xx status code).

### 8. POST /api/task/modify/{taskId}

- **Description:** Modifies an existing task. Only the owner of the task can modify it.
- **HTTP Method:** `POST`
- **Path Parameter:**
    - `{taskId}`: The ID of the task to modify (Long).
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:**
    ```json
    {
      "title": "Updated Task Title" (String, optional),
      "description": "Updated Task Description" (String, optional),
      "done": true/false (Boolean, optional)
    }
    ```
- **Response Body:** None (successful operation typically returns a 2xx status code). Returns a 404 Not Found if the task does not exist, or a 401 Unauthorized if the authenticated user is not the owner of the task.

### 9. GET /api/task/delete/{id}

- **Description:** Deletes a specific task. Only the owner of the task can delete it.
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{id}`: The ID of the task to delete (Long).
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:** None
- **Response Body:** None (successful operation typically returns a 2xx status code, e.g., 202 Accepted). Returns a 404 Not Found if the task does not exist, or a 403 Forbidden if the authenticated user is not the owner of the task.

### 10. POST /api/task/search/title

- **Description:** Retrieves tasks belonging to the authenticated user by their title.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:**
    ```json
    {
      "title": "Task Title" (String, required)
    }
    ```
- **Response Body:** A JSON array of `Task` objects matching the specified title and belonging to the authenticated user. Returns an empty array if no matching tasks are found.

## User API Endpoints

### 1. POST /api/user/modify/login

- **Description:** Modifies the login of the authenticated user.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:**
    ```json
    {
      "login": "newLogin" (String, required)
    }
    ```
- **Response Body:** None (successful operation typically returns a 2xx status code, e.g., 202 Accepted or 204 No Content).

### 2. POST /api/user/modify/password

- **Description:** Modifies the password of the authenticated user.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** Yes
- **Request Body:**
    ```json
    {
      "password": "newPassword" (String, required)
    }
    ```
- **Response Body:** None (successful operation typically returns a 2xx status code, e.g., 202 Accepted or 204 No Content).

### 3. POST /api/user/

- **Description:** Creates a new user.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:**
    ```json
    {
      "login": "newLogin" (String, required),
      "password": "newPassword" (String, required)
    }
    ```
- **Response Body:** None (successful operation typically returns a 2xx status code, e.g., 201 Created).

### 4. GET /api/user/

- **Description:** Retrieves a list of all users (Admin access).
- **HTTP Method:** `GET`
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON array of `User` objects.

### 5. GET /api/user/admin/search/login/{login}

- **Description:** Retrieves a specific user by their login (Admin access).
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{login}`: The login of the user to retrieve (String).
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON object representing the `User`, or a 404 Not Found if the user does not exist.

### 6. GET /api/user/admin/search/id/{id}

- **Description:** Retrieves a specific user by their ID (Admin access).
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{id}`: The ID of the user to retrieve (Long).
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** A JSON object representing the `User`, or a 404 Not Found if the user does not exist.

### 7. POST /api/user/auth

- **Description:** Authenticates a user and returns a JWT token.
- **HTTP Method:** `POST`
- **Requires X-API-KEY Header:** No
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:**
    ```json
    {
      "login": "userLogin" (String, required),
      "password": "userPassword" (String, required)
    }
    ```
- **Response Body:** A JSON string containing the JWT token. Returns a 404 Not Found if the login is not found, or a 400 Bad Request for an invalid password.

### 8. GET /api/user/delete/{id}

- **Description:** Deletes a specific user (Admin access).
- **HTTP Method:** `GET`
- **Path Parameter:**
    - `{id}`: The ID of the user to delete (Long).
- **Requires X-API-KEY Header:** Yes
- **Requires Authorization Header (Bearer Token):** No
- **Request Body:** None
- **Response Body:** None (successful operation typically returns a 2xx status code, e.g., 202 Accepted). Returns a 404 Not Found if the user does not exist, or a 403 Forbidden if the API key is invalid.

