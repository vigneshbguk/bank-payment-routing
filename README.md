**The project Structure**
Name: payment-routing
Entry Point - PaymentRoutingApplication
Packages: 
1. Controller (Handles the REST GET request from users)
2. Entity (Contains all the necessary entities like Branch, Connection)
3. Repository (Handles the data retrieval from the database, BranchDAO fetches data from the Branch table in DB and ConnectionDAO fetches data from the Connection table in DB)
4. Service (Handles the service by calculating the lowest cost path from source branch to destination branch, I have used Dijkstra's Algorithm to find the lowest cost path from source to destination as it is the best algorithm to find the shortest path. Ref. Dijkstra is a Greedy approach with time complexity O(V log E) and Bellman-Ford is a Dynamic Programming approach with O(V*E) complexity) 


**To run the application, follow the below procedure**

* Requirements
* Java 17 or later
* Maven 3.8.1 or later

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

INSERT INTO Connection (connection_id, cost, from_branch_id, to_branch_id) VALUES
(101, 0, 1, 2), 
(102, 0, 1, 3), 
(103, 0, 3, 2), 
(104, 0, 2, 4), 
(105, 0, 3, 5), 
(106, 0, 4, 5), 
(107, 0, 5, 4),
(108, 0, 4, 6),
(109, 0, 5, 6); 
   
3. Build the Project
4. Run the Application
5. REST API Usage
The application exposes a GET REST API for finding the lowest cost path. The endpoint is:
http://localhost:8081/payments/route

request body sample{
  "originBranch": "BranchName",
  "destinationBranch": "BranchName"
}

6. Testing
To run the tests for the project, use the following Maven command:
mvn test


**Future Enhancements**



