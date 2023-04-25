# Database-Systems-with-JDBC

README for the Java program

This program is a simple command-line application that connects to an Oracle database and allows the user to perform basic CRUD (Create, Read, Update, Delete) operations.

Requirements

To run this program, you need to have the following installed on your machine:

Java Development Kit (JDK) version 8 or higher
An Oracle database server
Installation

To install the program, follow these steps:

Clone or download the source code from the repository.
Open the project in an IDE such as Eclipse or IntelliJ.
Configure the classpath to include the ojdbc11.jar file located in the lib folder.
Build the project to generate the executable Java bytecode.
Run the program by executing the main class (App.java).
Usage

When the program is run, the user will be presented with a menu of options to choose from. Here are the available options:

List tables: displays a list of all tables in the database.
Insert data into table: prompts the user to enter the name of a table, the columns to insert data into, and the corresponding values.
Update data in table: prompts the user to enter the name of a table, the column to update, the new value for the column, and a WHERE condition to specify which rows to update.
Perform a complex query: prompts the user to enter a SQL query to execute.
Run the test case: runs a set of predefined test cases.
Testing

The program includes a test case class (AppTestCase.java) that can be used to verify the correctness of the program's functionality. The test cases are defined as methods in the class, and can be run by creating an instance of the class and calling the runAllTests method.

Database Connection

The program connects to an Oracle database using the Oracle JDBC driver. The database connection details (host, port, database name, username, password) are hard-coded into the establishConnection method in the App class. You will need to modify these details to match your own database configuration.

Dependencies

This program depends on the Oracle JDBC driver (ojdbc11.jar), which is included in the lib folder. Make sure to include this file in the classpath when building and running the program.

License

This program is licensed under the MIT License. See the LICENSE file for details.

Contributing

Contributions are welcome! If you find a bug or want to suggest an improvement, please create a pull request or open an issue on the repository.

Credits

This program was created by [Your Name] and is based on the Oracle JDBC driver and the Java programming language.

This program is provided as-is and without warranty. The author is not responsible for any damage or loss caused by the use of this program. Use at your own risk.
