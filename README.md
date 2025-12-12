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

# MCP Server - Model Context Protocol Java SDK
- https://github.com/modelcontextprotocol/java-sdk

## Claude Desktop MCP Server configuration

  ```json
    {
  "mcpServers": {
    "blog-mcp-server": {
      "command": "java",
      "args": [
        "-jar",
        "/Users/olivier/IdeaProjects/casestudy/mcp/target/mcp-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
  ```

## Ask Questions to Claude about the Blog Data

- Who are the authors of the blog?

The blog has four authors:

- Alice (alice@example.com)
- Bob (bob@example.com)
- Carla (carla@example.com)
- Dan (dan@example.com)