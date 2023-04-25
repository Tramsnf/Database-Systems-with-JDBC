import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class App {
    private static Connection establishConnection(String dbType) {
        Connection conn = null;
        String url = "";
        String username = "";
        String password = "";

        switch (dbType.toLowerCase()) {
            case "oracle":
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    url = "jdbc:oracle:thin:@oracle2.wiu.edu:1521/orclpdb1";
                    username = "";
                    password = "C0_9";
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Oracle driver not found");
                }
                break;
            case "mysql":
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    url = "jdbc:mysql://localhost:3306/your_database";
                    username = "your_username";
                    password = "your_password";
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "MySQL driver not found");
                }
                break;
            case "postgresql":
                try {
                    Class.forName("org.postgresql.Driver");
                    url = "jdbc:postgresql://localhost:5432/your_database";
                    username = "your_username";
                    password = "your_password";
                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "PostgreSQL driver not found");
                }
                break;
            default:
                logger.log(Level.SEVERE, "Unsupported database type");
                return null;
        }

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection Failed");
        }

        return conn;
    }

    static BufferedReader reader;
    static Connection conn = null;

    private static final Logger logger = Logger.getLogger(App.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("App.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (Exception e) {
            System.err.println("Error setting up logger: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        reader = new BufferedReader(new InputStreamReader(System.in));

        // Prompt the user to enter the database type they want to connect to
        System.out.println("Enter the database type (oracle, mysql, postgresql):");
        String dbType = "";
        try {
            dbType = reader.readLine();
        } catch (IOException e) {
            System.err.println("Error reading database type: " + e.getMessage());
        }

        conn = establishConnection(dbType);

        if (conn == null) {
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }

        boolean runTest = false;
        int option = 0;

        while (option != 1 && option != 2) {
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1: Run the program");
            System.out.println("2: Run the test case");
            System.out.println();

            try {
                System.out.println("Enter your option:");
                option = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                option = 0;
            }

            if (option == 1) {
                runProgram();
            } else if (option == 2) {
                runTest = true;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        if (runTest) {
            // Create an instance of the App2Test class and call the runAllTests method
            AppTestCase test = new AppTestCase(conn);
            test.runAllTests();
        }

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing the connection: " + e.getMessage());
        }
    }

    private static void runProgram() {
        boolean flag = true;
        while (flag) {
            System.out.println();
            System.out.println();
            System.out.println("Choose an option:");
            System.out.println("1: List tables in Database");
            System.out.println("2: Insert data into table");
            System.out.println("3: Update data in table");
            System.out.println("4: Perform a complex queries");
            System.out.println("0: Exit");
            System.out.println();
            System.out.println();

            int option = 9;
            try {
                System.out.println("Enter your option:");
                option = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                option = 9;
            }

            switch (option) {
                case 1:
                    listTables();
                    break;
                case 2:
                    insertData();
                    break;
                case 3:
                    updateData();
                    break;
                case 4:
                    complexQuery();
                    break;
                case 0:
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void listTables() {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] types = { "TABLE" };
            try (ResultSet resultSet = metaData.getTables(null, null, "%", types)) {
                System.out.println("List of available tables:");
                int columnCount = 5;
                int currentColumn = 0;
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    System.out.printf("%-20s", tableName); //allocate 20 characters for each table name
                    currentColumn++;

                    if (currentColumn % columnCount == 0) {
                        System.out.println(); // Start a new row when the current row is full
                    }
                }

                // Add a newline at the end if the last row was not complete
                if (currentColumn % columnCount != 0) {
                    System.out.println();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error listing tables: " + e.getMessage());
        }
    }

    private static void insertData() {
        try {
            System.out.println("Enter table name:");
            String tableName = reader.readLine();
            System.out.println("Enter column names separated by commas (e.g. column1,column2):");
            String columns = reader.readLine();
            System.out.println("Enter the corresponding values separated by commas (e.g. value1,value2):");
            String values = reader.readLine();

            String[] columnArray = columns.split(",");
            String[] valueArray = values.split(",");

            if (columnArray.length != valueArray.length) {
                System.out.println("The number of columns and values do not match. Please try again.");
                return;
            }

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO ").append(tableName).append(" (");
            queryBuilder.append(columns);
            queryBuilder.append(") VALUES (");

            for (int i = 0; i < valueArray.length; i++) {
                queryBuilder.append("?");
                if (i < valueArray.length - 1) {
                    queryBuilder.append(", ");
                }
            }
            queryBuilder.append(")");

            try (PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {
                for (int i = 0; i < valueArray.length; i++) {
                    stmt.setString(i + 1, valueArray[i]);
                }

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) inserted.");
            }
        } catch (Exception e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    private static void updateData() {
        try {
            System.out.println("Enter table name:");
            String tableName = reader.readLine();
            System.out.println("Enter the column name to update:");
            String columnName = reader.readLine();
            System.out.println("Enter the new value for the column:");
            String newValue = reader.readLine();
            System.out.println("Enter the WHERE condition (e.g. column1 = value1 AND column2 = value2):");
            String condition = reader.readLine();

            String query = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE " + condition;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, newValue);

                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) updated.");
            }
        } catch (Exception e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
    }

    private static void complexQuery() {
        try {
            System.out.println("Enter your complex query:");
            String query = reader.readLine();
            query = query.trim();
            if (query.endsWith(";")) {
                query = query.substring(0, query.length() - 1);
            }

            // Get the first word of the query (the SQL command)
            String command = query.split("\\s+")[0].toLowerCase();

            // Check if the command is a SELECT or INSERT statement
            boolean isSelect = command.equals("select");
            boolean isInsert = command.equals("insert");
            boolean isAlter = command.equals("alter");

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                if (isSelect) {
                    // Handle SELECT queries
                    try (ResultSet resultSet = stmt.executeQuery()) {
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // Print column names
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(metaData.getColumnName(i));
                            if (i < columnCount) {
                                System.out.print("\t");
                            }
                        }
                        System.out.println();

                        // Print column values
                        while (resultSet.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                System.out.print(resultSet.getString(i));
                                if (i < columnCount) {
                                    System.out.print("\t");
                                }
                            }
                            System.out.println();
                        }
                    }
                } else if (isInsert) {
                    // Handle INSERT queries
                    int rowsAffected = stmt.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted.");
                } else if (isAlter) {
                    // Handle ALTER queries
                    stmt.executeUpdate();
                    System.out.println("Table altered.");
                } else {
                    // Handle other SQL queries (UPDATE, DELETE, etc.)
                    int rowsAffected = stmt.executeUpdate();
                    System.out.println(rowsAffected + " row(s) affected.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error executing complex query: " + e.getMessage());
            e.printStackTrace();
        }
    }
}