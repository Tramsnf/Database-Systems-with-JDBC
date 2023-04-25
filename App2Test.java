import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class App2Test {
    private Connection conn;

    public App2Test(Connection conn) {
        this.conn = conn;
    }

    // Test Case 1
    public void testCase1() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Test Case 1: Your test description");

        try {
            // Your test implementation
            String query = "SELECT doc_id, doc_name FROM doctors where doc_id = 2";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            System.out.println("Error executing complex query: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    // Test Case 2
    public void testCase2() {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Test Case 2: Your test description");

        try {
            // Your test implementation
            String query = "Your SQL query for Test Case 2";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            System.out.println("Error executing complex query: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    private void executeQueryAndPrintResults(String query) throws Exception {
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
    }

    public void runAllTests() {
        testCase1();
        testCase2();
    }
}
