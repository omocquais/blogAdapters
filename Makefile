clean:
	@echo "Cleaning Maven modules..."
	mvn clean
	@echo "Done."

build:
	@echo "Building Maven modules..."
	mvn install
	@echo "Maven modules built."

db-up:
	@echo "Starting database..."
	docker-compose up
	@echo "Database started."

api-up:
	@echo "Starting http-adapter..."
	mvn spring-boot:run -pl http
	@echo "http-adapter started."

http-get-authors:
	@echo "Making HTTP requests authors..."
	http :8080/authors | jq
	@echo "HTTP requests authors completed."

http-get-articles:
	@echo "Making HTTP requests articles..."
	http :8080/articles | jq
	@echo "HTTP requests articles completed."

http-get-comments:
	@echo "Making HTTP requests comments..."
	http :8080/comments | jq
	@echo "HTTP requests comments completed."

mcp-up:
	@echo "Starting mcp-adapter..."
	mvn spring-boot:run -pl mcp
	@echo "mcp-adapter started."

db-down:
	@echo "Stopping database..."
	docker-compose down
	@echo "Database stopped."

test:
	@echo "Test..."
	mvn test
	@echo "Tests completed."
