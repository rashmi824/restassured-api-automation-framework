# 📌 REST-Assured API Automation Framework

## A complete end-to-end API Testing Automation Framework built using:

Java 21
REST-Assured
TestNG
Maven
Jackson Serialization / Deserialization
JSON Schema Validation
API Chaining

This project demonstrates real-world API automation covering GET, POST, PUT, PATCH, DELETE, JSONPath, POJOs, JWT authentication, logging, and chaining.

### Project Features

1. GET, POST, PUT, PATCH, DELETE requests

BDD style → given() – when() – then()
Validation includes:
✔ Status code
✔ Status line
✔ Headers
✔ Response body
✔ JSON object & nested JSON validation

2. Logging Support

Log full request
Log full response
Log only when validation fails

3. API Chaining

Use one API's response → into next API request
Example flow:
Create → Get → Update → Delete

4. JSON Path Validations

Validation using:

$ root
. child
.. recursive
[*] arrays
[0] index based

5. API Testing with External Files

Read request bodies from:
.txt
.json
using FileUtils (Apache Commons IO)

6. Serialization & Deserialization

Using Jackson Databind
Java POJO → JSON
JSON → Java POJO

7. JSON Schema Validation

Ensures correct structure + data types.

8. TestNG Integration

TestNG test classes
TestNG suite xml
Parallel execution ready

9. Maven Support

Run tests using:
mvn clean test

### Required Software
Software	        Purpose
Java 21	            Programming
IntelliJ            IDE
Maven	            Build Tool
TestNG	            Testing Framework

### How to Run Tests

1️. Clone the Repository
git clone https://github.com/your-username/restassured-api-automation-framework.git
cd restassured-api-automation-framework

2️. Run Using Maven
mvn clean test

3️. Run Specific Test Suite
mvn clean test -DsuiteXmlFile=testng.xml

### Dependencies (Pom.xml)

Includes:

✔ Rest-Assured
✔ JSON Schema Validator
✔ Jackson Databind
✔ TestNG
✔ Maven Surefire
✔ Maven Compiler (Java 21)
✔ Commons IO
