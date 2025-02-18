#!/bin/bash

echo "=== Starting All Tests ==="

# Check if the application is running
if ! curl -s http://localhost:8080/actuator/health > /dev/null; then
    echo "Error: Application is not running!"
    echo "Please start the application first."
    exit 1
fi

# Clean the database
echo -e "\nCleaning database..."
./scripts/cleanup-db.sh

# Run main API tests
echo -e "\nRunning API tests..."
./scripts/test-api.sh

# Run validation tests
echo -e "\nRunning validation tests..."
./scripts/test-validation.sh

echo -e "\n=== All Tests Complete ==="
