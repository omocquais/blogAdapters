# HTTP and MCP adapters for a Blog Case Study

## Prerequisites

- Maven 3
- Java 25 
- Docker & Docker Compose (to run PostgreSQL database)
- JQ (testing JSON output in terminal)
- HTTPie (testing HTTP APIs)

## Frameworks and Libraries used in this project

- TestContainers
- Spring Boot 4
- Apache Calcite
- JUnit 5
- Mockito

## Getting Started

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

- Test the API using HTTPie to get articles

  ```bash
    http :8080/articles | jq
  ```

- Test the API using HTTPie to get comments

  ```bash
    http :8080/comments | jq
  ```

## MCP Server - Model Context Protocol Java SDK
- https://github.com/modelcontextprotocol/java-sdk

### Claude Desktop MCP Server configuration ( claude_desktop_config.json )

  ```json
{
  "mcpServers": {
    "blog-mcp-server": {
      "command": "/Users/olivier/.sdkman/candidates/java/current/bin/java",
      "env": {
        "MODELS_CONFIG_DIR": "/Users/olivier/IdeaProjects/casestudy/http/src/main/config/",
        "LOGGING_PATTERN_CONSOLE_FILE_NAME": "/Users/olivier/IdeaProjects/casestudy/logs/mcp-blog-stdio-server.log"
      },
      "args": [
        "-jar",
        "/Users/olivier/IdeaProjects/casestudy/mcp/target/mcp-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
  ```

### Ask Questions to Claude about the Blog Data

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
