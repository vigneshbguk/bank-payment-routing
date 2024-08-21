**The project Structure**
Name: payment-routing
Entry Point - PaymentRoutingApplication.java
Packages: 
1. Controller (Handles the REST GET request from users)
2. Entity (Contains all the necessary entities like Branch, Connection)
3. Repository (Handles the data retrieval from the database, BranchDAO fetches data from the Branch table in DB and ConnectionDAO fetches data from the Connection table in DB)
4. Service (Handles the service by calculating the lowest cost path from source branch to destination branch, I have used Dijkstra's Algorithm to find the lowest cost path from source to destination as it is the best algorithm to find the shortest path. Ref. **Dijkstra is a Greedy approach with time complexity O(V log E)** and **Bellman-Ford is a Dynamic Programming approach with O(V*E) complexity)** )


**To run the application, follow the below procedure**
Requirements
* Java 17
* Maven 3.8.1

1. Clone the Repository
2. Create the PostgreSQL DB
**To create and instantiate the database follow the below process:**

1. Create a PostgreSQL database called Global_Banking
2. Run the below queries to create a table and insert values to the table.
   
CREATE TABLE Branch (
    id SERIAL PRIMARY KEY,
    branch_name VARCHAR(255) NOT NULL UNIQUE,
    transfer_cost INTEGER NOT NULL
);

CREATE TABLE Connection (
    connection SERIAL PRIMARY KEY,
    from_branch_id INTEGER NOT NULL,
    to_branch_id INTEGER NOT NULL,
    FOREIGN KEY (from_branch_id) REFERENCES Branches(id),
    FOREIGN KEY (to_branch_id) REFERENCES Branches(id)
);

INSERT INTO Branch (branch_name, transfer_cost) VALUES
('A', 5),
('B', 50),
('C', 10),
('D', 10),
('E', 20),
('F', 5);
('G', 10);



INSERT INTO Connection (connection_id, cost, from_branch_id, to_branch_id) VALUES
(101, 0, 1, 2),  -- A to B
(102, 0, 1, 3),  -- A to C
(103, 0, 3, 2),  -- C to B
(104, 0, 2, 4),  -- B to D
(105, 0, 3, 5),  -- C to E
(106, 0, 4, 5),  -- D to E
(107, 0, 5, 4),  -- E to D
(108, 0, 4, 6),  -- D to F
(109, 0, 5, 6);  -- E to F
(110, 0, 6, 7);  -- F to G
   
3. Build the Project
4. Run the Application
5. REST API Usage
The application exposes a GET REST API for finding the lowest cost path. The endpoint is:
http://localhost:8081/payments/route
request body sample{
  "originBranch": "BranchName",
  "destinationBranch": "BranchName"
}

7. Testing
To run the tests for the project, use the following Maven command: mvn test


Project Requirements Analysis:

 1. **Your application should make use of Java and Maven** (completed)
 
 2 **Your solution should define and implement the following interface:**
 public interface PaymentService {
    /**
     * Process a payment returning the cheapest sequence of branches as comma-separated String. 
Implementations are expected
     * to be thread-safe.
     * @param originBranch the starting branch
     * @param destinationBranch the destination branch
     * @returns the cheapest sequence for the payment as a CSV (e.g. A, D, C) or null if no sequence is 
available
    **/
    String processPayment(String originBranch, String destinationBranch);
 } 
 (Successfully Implementded)

 3 **Your solution should expose a REST API which exposes the functionality provided by the above interface. Consideration should be given to input validation.** (Completed)

4 **Your solution should be flexible to easily handle future additions of new branches and links without modification**  (Successfully tested for addition of new branches, links and also tested)
 
 5 **You should consider the performance of your implementation and demonstrate how the implementation would scale when new branches and links are added.** (I have already implemeted efficient algorithm to find the cheapest path. We can implement hikari connection pool to reduce creating connection to DB eveytime the request has been made. Also, we can implement cache to store the branches of the bank to reduce the number of connection requests saving more time)

 **6 Your solution should have sufficient and appropriate functional test coverage** (Test cases have been written for all the logics in the project which is mostly for service package classes).
 
 **7 Your solution should be thread safe such that the above processPayment method can be called by multiple threads concurrently** (I have used ReentrantLock, it allows a thread to be interrupted while waiting for a lock, which can be useful for handling interruptions more gracefully. With ReentrantLock, we can use tryLock to attempt to acquire the lock without blocking.)

 
 8 **Your implementation is free to use libraries however an assessment should be made on the credibility of the library.**

**Spring Boot:** Spring Boot is a widely adopted framework in the Java ecosystem for building production-ready applications. It simplifies the process of setting up and configuring Spring applications, which is why it’s extensively used in enterprise applications. Spring Boot is regularly updated with new features, improvements, and security patches. It has a predictable release cycle and long-term support (LTS) versions. Spring Boot benefits from the security practices of the broader Spring ecosystem. Regular updates and security patches are provided, and the framework adheres to industry standards for security.

**JUnit:** JUnit is the de facto standard testing framework for Java applications. It is widely used for unit testing and has been integral to Java development for many years. It is actively maintained with frequent updates. The latest major versions follow semantic versioning, and updates include new features, bug fixes, and performance improvements. As a testing framework, JUnit itself is not a security risk, but it's essential to keep it updated to avoid compatibility issues with other libraries and frameworks.

**Spring Data JPA:** Spring Data JPA is a popular choice for data access in Spring applications. It builds on top of JPA and simplifies the implementation of data access layers. Actively maintained as part of the Spring Data project. It receives regular updates and improvements and is aligned with new Spring and JPA standards. Spring Data JPA follows the security practices of the Spring ecosystem. Regular updates ensure that any vulnerabilities are addressed promptly.

**Hibernate:** Hibernate is one of the most widely used Object-Relational Mapping (ORM) frameworks in Java. It is often used as the JPA provider in Spring applications. Hibernate is actively maintained with regular updates. The project follows a well-defined release cycle and addresses issues related to performance, features, and security. Hibernate's security largely depends on its integration with the rest of the application. It is actively updated to address known vulnerabilities, and users should ensure they are using the latest stable versions.

**Mockito:** Mockito is a widely used framework for creating mock objects in unit tests. It simplifies the process of mocking dependencies and verifying interactions. Mockito is actively maintained with frequent updates. It follows semantic versioning, and the maintainers address issues and introduce new features regularly. Mockito is a testing tool, so it does not directly introduce security risks. However, keeping it updated ensures compatibility and stability with other libraries and frameworks.
 
 9 **Your implementation and any supporting documentation (if required) should not refer to any real company names or individuals - everything should be fictitious** (followed)
 
 10 **Your completed solution should be uploaded to a publically available repository on GitHub. Once the interview process is complete, the repository should be deleted.** (followed)


**Future Improvement:**
* The use of Git commits and branch/version management could be more frequent and standardized.
* Documentation for code and design could be more detailed (Couldn't do it due to time constraint) 
