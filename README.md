# Database Systems with JDBC

This Java program demonstrates how to interact with a database using JDBC. It allows you to list tables in the database, insert data into tables, update data in tables, and perform complex SQL queries.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Oracle JDBC driver (ojdbc11.jar) (included in the `lib` directory)

## How to run the program

1. Compile the Java files:
   ```
   javac -cp lib/ojdbc11.jar src/App.java src/AppTestCase.java
   ```
2. Run the `App` class:
   ```
   java -cp src:lib/ojdbc11.jar App
   ```

## Program features

When running the program, you will be presented with the following options:

1. Run the program
2. Run the test case

### Running the program

If you choose to run the program, you will have the following options:

1. List tables in Database
2. Insert data into table
3. Update data in table
4. Perform a complex queries
0. Exit

### Running the test case

If you choose to run the test case, the program will create an instance of the `AppTestCase` class and call the `runAllTests()` method.

## AppTestCase

The `AppTestCase` class contains two test cases: `testCase1()` and `testCase2()`. You can modify these methods or add additional test cases as needed. The `executeQueryAndPrintResults()` method handles query execution and result printing for the test cases.

# Usage

Once you've compiled and run the program, follow the on-screen instructions to perform various database operations. Here's a detailed explanation of the available options:

# List tables in Database
This option lists all the tables in the connected database. Selecting this option will display the names of the tables in the console.

# Insert data into table
This option allows you to insert data into a table. First, you'll be prompted to enter the table name, followed by the column names (separated by commas), and finally the corresponding values (also separated by commas). The program will then insert the data into the specified table.

# Update data in table
This option lets you update data in a table. You'll be asked to enter the table name, the column name to update, the new value for the column, and the WHERE condition. The program will then update the specified data in the table.

# Perform complex queries
This option allows you to execute any SQL query on the connected database. You'll be prompted to enter the query, and the program will then execute the query and display the results (if any) in the console.

# Exit
Selecting this option will exit the program.

# Extending the program

You can extend the program to add more functionality or customize it to suit your requirements. Here are a few ideas:

1. Implement additional database operations, such as deleting data from tables.
2. Add more test cases to the AppTestCase class to cover a broader range of scenarios.
3. Improve error handling and user input validation.
4. Add support for connecting to different types of databases, such as MySQL or PostgreSQL.
5. Implement a graphical user interface (GUI) to make it more user-friendly.


Feel free to modify the source code and experiment with new features to enhance the program's capabilities.
