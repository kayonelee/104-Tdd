**Running Project:**

- Run DemoTddApplication.java class
- Local Host: [http://localhost:8050/](http://localhost:8050/)

**JAR File:**

- Command line run: `./mvnw clean package`
- Command line: `java -jar demoTDD-0.0.1-SNAPSHOT.jar`

**Testing Application:**

- DemoTddApplicationTest.java
- OrderTestController.java
- OrderTestRepository.java

**H2 Database:**

- In-memory database: [http://localhost:8050/h2-console](http://localhost:8050/h2-console)
- URL: jdbc:h2:mem:dcbapp
- Driver Class Name: org.h2.Driver
- Username: sa

**Project Structure:**

- DemoTddApplication.java: Entry point of the application
- OrderRepository.java: Interface that extends JpaRepository provided by Spring Data JPA (CRUD operations)
- TddOrder.java: Entity class representing an order in the system
- OrderController.java: Spring MVC controller handling HTTP requests

# 104-Tdd
