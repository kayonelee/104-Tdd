**Running Project:**  
Run DemoTddApplication.java class
Local Host: http://localhost:8050/

**JAR File:**  
Command line run:  ./mvnw clean package  
Command line: java -jar demoTDD-0.0.1-SNAPSHOT.jar

**Testing Application:**  
DemoTddApplicationTest.java  
OrderTestController.java  
OrderTestRepository.java  

**H2 Database-in-memory database:**  
http://localhost:8050/h2-console  
url=jdbc:h2:mem:dcbapp  
driverClassName=org.h2.Driver  
username=sa  

**DemoTddApplication.java:**  
Entry point of application

**OrderRepository.java:**  
Interface that extends JpaRepository provided by Spring Data Jpa. (CRUD operations)  

**TddOrder.java:**  
Entity class representing an order in the system.  

**OrderController.java:**  
Spring MVC controller handling Http requests  

#   1 0 4 - T d d  
 