import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class App2Test {
    private Connection conn;

    public App2Test(Connection conn) {
        this.conn = conn;
    }

    // Test Case 17
    public void testCase17() {
        System.out.println("Test Case 17: Your test description");

        try {
            // Your test implementation
            String query = "Your SQL query for Test Case 17";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test Case 18
    public void testCase18() {
        System.out.println("Test Case 18: Your test description");

        try {
            // Your test implementation
            String query = "Your SQL query for Test Case 18";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test Case 19
    public void testCase19() {
        System.out.println("Test Case 19: Your test description");

        try {
            // Your test implementation
            String query = "Your SQL query for Test Case 19";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Test Case 20
    public void testCase20() {
        System.out.println("Test Case 20: Your test description");

        try {
            // Your test implementation
            String query = "Your SQL query for Test Case 20";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            e.printStackTrace();
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
        testCase17();
        testCase18();
        testCase19();
        testCase20();
    }
}
