import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;

public class App2 {
    static BufferedReader reader;
    static Connection conn = null;

    public static void main(String[] args) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        conn = establishConnection();

        if (conn == null) {
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }
        
        // Create an instance of the App2Test class and call the runAllTests method
        App2Test test = new App2Test(conn);
        test.runAllTests();

        boolean flag = true;
        while (flag) {
            System.out.println("Choose an option:");
            System.out.println("1: Insert data into table");
            System.out.println("2: Update data in table");
            System.out.println("3: Perform a complex query");
            System.out.println("0: Exit");

            int option = 9;
            try {
                option = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                option = 9;
            }

            switch (option) {
                case 1:
                    insertData();
                    break;
                case 2:
                    updateData();
                    break;
                case 3:
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

    private static Connection establishConnection() {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle2.wiu.edu:1521/orclpdb1", "ORA_SOI104",
                    "CS470_8129");
        } catch (Exception e) {
            System.out.println("Connection Failed");
        }
        return conn;
    }

    private static void listTables() {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String[] types = { "TABLE" };
            ResultSet resultSet = metaData.getTables(null, null, "%", types);

            System.out.println("List of available tables:");
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                System.out.println(tableName);
            }
        } catch (Exception e) {
            System.out.println("Error listing tables: " + e.getMessage());
        }
    }

    private static void insertData() {
        try {
            listTables();
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

            PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString());
            for (int i = 0; i < valueArray.length; i++) {
                stmt.setString(i                 + 1, valueArray[i]);
            }

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
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
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newValue);

            int rowsAffected = stmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
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

        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();
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
    } catch (Exception e) {
        System.out.println("Error executing complex query: " + e.getMessage());
        e.printStackTrace();
    }
}
}

