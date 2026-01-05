# Blog Case Study: HTTP and MCP Adapters

This repository contains HTTP and MCP adapters built as part of a blog-oriented case study. The project demonstrates how to expose blog-related data (authors, articles, and comments) through a Spring Boot–based HTTP API, backed by a PostgreSQL database.

## Overview

The project showcases:

- A Spring Boot–based HTTP adapter exposing REST endpoints
- An MCP server that enables natural-language querying of blog data
- A PostgreSQL database initialized with schema and seed data
- Integration with Apache Calcite for SQL parsing and query planning

## Prerequisites

Ensure the following tools are installed before getting started:

- Maven 3
- Java 25
- Docker and Docker Compose (for running the PostgreSQL database)
- jq (for formatting and inspecting JSON output in the terminal)
- HTTPie (for testing HTTP APIs)
- Claude Desktop (to interact with MCP server)
- MCP Inspector (for inspecting MCP interactions) 

## Technology Stack

The project is built using the following frameworks and libraries:

- Spring Boot 4 – application framework for building and running the service
- Apache Calcite – SQL parsing and query planning
- Testcontainers – managing containerized dependencies during tests
- JUnit 5 – unit and integration testing
- Mockito – mocking and behavior verification in tests

## Getting Started

Follow the steps below to build and run the project locally.

### Build the project

- Build the project using Maven and run all tests

  ```bash
  mvn install
  ```

### Start the database

- Launch a PostgreSQL instance with the initial schema and seed data:

  ```bash
  docker-compose up
  ```

### Run the HTTP Adapter

- Start the Spring Boot application (HTTP adapter) using Jetty:

  ```bash
  mvn spring-boot:run -pl http
  ```

The application will be available on port 8080.

### Test the HTTP API

You can use HTTPie together with jq to query and inspect the API responses.

#### Retrieve authors

  ```bash
    http :8080/authors | jq
  ```

#### Retrieve articles

  ```bash
    http :8080/articles | jq
  ```

#### Retrieve comments

  ```bash
    http :8080/comments | jq
  ```

### Notes

- Ensure Docker is running before starting the database.
- The HTTP adapter must be running for the API endpoints to be accessible.
- MCP-related tools (Claude Desktop and MCP Inspector) are required only when interacting with the MCP server 
  components.

## MCP Server

This project includes an MCP server built using the Model Context Protocol Java SDK 
- https://github.com/modelcontextprotocol/java-sdk

The MCP server allows LLMs (such as Claude Desktop) to query blog data using natural language.

### Claude Desktop Configuration

To register the MCP server with Claude Desktop, update your `claude_desktop_config.json` file as shown below:

  ```json
{
  "mcpServers": {
    "blog-mcp-server": {
      "command": "/Users/olivier/.sdkman/candidates/java/current/bin/java",
      "env": {
        "MODELS_CONFIG_DIR": "/Users/olivier/IdeaProjects/casestudy/http/src/main/config/"
      },
      "args": [
        "-jar",
        "/Users/olivier/IdeaProjects/casestudy/mcp/target/mcp-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
  ```

#### Configuration Notes

- `MODELS_CONFIG_DIR` specifies the directory containing the Apache Calcite JDBC adapter configuration.
- Paths should be adjusted to match your local environment.

### Example Queries with Claude

Once the MCP server is running and connected to Claude Desktop, you can ask questions such as:

#### Retrieve a complete list of all authors who contribute to this blog

Here's the complete list of all authors who contribute to this blog:

- Alice - alice@example.com (ID: 40e6215d-b5c6-4896-987c-f30f3678f608)
- Bob - bob@example.com (ID: 6ecd8c99-4036-403d-bf84-cf8400f67836)
- Carla - carla@example.com (ID: d0216c95-a141-404a-9e56-b80f05936812)
- Dan - dan@example.com (ID: a60b3bfe-d596-40c9-b296-2bd1987bc076)

#### Retrieve a complete list of all published articles on this blog

I'll retrieve the complete list of all published articles on this blog.Here's the complete list of all published articles on this blog:

1. **The Rise of AI Technologies**
  - Author: Alice (ID: 40e6215d-b5c6-4896-987c-f30f3678f608)
  - Published: January 10, 2024 at 9:00 AM
  - Content: "Artificial intelligence is transforming industries across the world..."
  - Article ID: fa730898-3587-4809-b22c-f84b34b26cfe

2. **Understanding Cloud Computing**
  - Author: Alice (ID: 40e6215d-b5c6-4896-987c-f30f3678f608)
  - Published: March 15, 2024 at 2:30 PM
  - Content: "Cloud computing allows businesses to scale infrastructure efficiently..."
  - Article ID: 096249c1-d1d1-4d92-8632-bda1e627d111

3. **How Blockchain Works**
  - Author: Carla (ID: d0216c95-a141-404a-9e56-b80f05936812)
  - Published: May 1, 2024 at 8:15 AM
  - Content: "Blockchain is a decentralized ledger technology..."
  - Article ID: a200870b-6f83-4d3e-a274-a1591f2f4fcf

There are currently 3 published articles on this blog. Alice has authored 2 articles, and Carla has authored 1 article.

#### Retrieve a complete list of all unpublished articles on this blog

I'll retrieve the complete list of all unpublished articles on this blog.Here's the complete list of all unpublished articles on this blog:

1. **Top 10 Programming Languages in 2025**
  - Author: Bob (ID: 6ecd8c99-4036-403d-bf84-cf8400f67836)
  - Status: Unpublished (Draft)
  - Publish Date: Not set
  - Content: "The tech landscape evolves quickly. Here are the most popular languages..."
  - Article ID: 42af0ee7-9a81-4399-b775-72cb2a749da8

There is currently 1 unpublished article on this blog, authored by Bob.

#### Retrieve a complete list of comments on published blog articles

I'll retrieve the complete list of comments on published blog articles.Here's the complete list of comments on blog articles:

**Comments on "The Rise of AI Technologies" (fa730898-3587-4809-b22c-f84b34b26cfe):**
1. **Jean** (jean@mail.com) - January 11, 2024 at 10:05 AM
  - "Great insights on AI!"
  - Comment ID: 78884f5c-5af0-4921-9dd4-cb7687693dd9

2. **Marie** (marie@mail.com) - March 16, 2024 at 9:00 AM
  - "Cloud computing still confuses me, but this helped."
  - Comment ID: 807139db-d2e5-4dd2-acdf-31fe2474f3c1

**Comments on "Understanding Cloud Computing" (096249c1-d1d1-4d92-8632-bda1e627d111):**
3. **Luc** (luc@mail.com) - April 1, 2024 at 12:00 PM
  - "This article provides a clear and accessible overview of cloud computing, making a complex topic much easier to understand."
  - Comment ID: 73204bf2-e65f-4f72-8e2b-541881873d3e

4. **Sophie** (sophie@mail.com) - May 2, 2024 at 7:30 AM
  - "Great overview—Understanding Cloud Computing really clarifies how virtualized resources make modern computing more flexible and scalable."
  - Comment ID: 6ef3aa9e-69b4-4572-9744-840ed7a45c38

**Comments on "Top 10 Programming Languages in 2025" (42af0ee7-9a81-4399-b775-72cb2a749da8) [Unpublished Article]:**
5. **Paul** (paul@mail.com) - January 12, 2024 at 3:20 PM
  - "Rust should definitely be higher on the list!"
  - Comment ID: 10ae62ad-37fd-4dee-b4c7-4c236b5f3e17

6. **Nora** (nora@mail.com) - February 21, 2024 at 1:40 PM
  - "Interesting to see how emerging languages are climbing the ranks in 2025's top 10 list."
  - Comment ID: 4a26bf44-5fae-4960-ac49-eb23d149e76f

**Summary:**
- Total comments: 6
- Comments on published articles: 4
- Comments on unpublished articles: 2 (on Bob's draft article)
- Note: There are no comments yet on the "How Blockchain Works" article by Carla.

         
# MCP Inspector

To launch the MCP Inspector locally:

  ```bash
    npx @modelcontextprotocol/inspector
  ```

This tool helps inspect MCP traffic and validate server behavior during development.