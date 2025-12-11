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

db-down:
	@echo "Stopping database..."
	docker-compose down
	@echo "Database stopped."

test:
	@echo "Test..."
	mvn test
	@echo "Tests completed."

