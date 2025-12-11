# Prerequisites

- Maven 3
- Java 25
- Docker & Docker Compose
- JQ
               
# Frameworks and Libraries used in this project
-
- TestContainer
- Spring Boot 4
- Apache Calcite
- JUnit 5
- Mockito

# Getting Started

- Build the project

  ```bash
	mvn install
  ```

- Start the database - PostgreSQL with initial schema and data

  ```bash
	docker-compose up
  ```

- Start the Spring boot application (HTTP adapter server) - Jetty

  ```bash
    mvn spring-boot:run -pl http
  ```
                                                                 
- Test the API using HTTPie to get authors

  ```bash
    http :8080/authors | jq
  ```