import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class AppTestCase {
    private Connection conn;

    public AppTestCase(Connection conn) {
        this.conn = conn;
    }

    // Test Case 1
    public void testCase1() {
        System.out.println();
        System.out.println("Test Case 1: Your test description");
        System.out.println();

        try {
            // Your test implementation
            String query = "SELECT j.name, COUNT(j.name) AS name_count, j.time, j.price, COUNT(DISTINCT j.ip) AS ip_count, SUM(j.price) AS price_sum FROM joy j JOIN friends f ON j.name = f.name WHERE j.time >= TO_TIMESTAMP(:yesterday, 'YYYY-MM-DD HH24:MI:SS') AND j.time < TO_TIMESTAMP(:today, 'YYYY-MM-DD HH24:MI:SS') GROUP BY j.name, j.time, j.price";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            System.out.println("Error executing complex query: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Test Case 2
    public void testCase2() {
        System.out.println();
        System.out.println("Test Case 2: Your test description");
        System.out.println();

        try {
            // Your test implementation
            String query = "...";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error executing complex query: " + e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }

    // Test Case 3
    public void testCase3() {
        System.out.println();
        System.out.println("Test Case 3: Your test description");
        System.out.println();

        try {
            // Your test implementation
            String query = "...";
            executeQueryAndPrintResults(query);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Error executing complex query: " + e.getMessage());
            e.printStackTrace();
            System.out.println();
        }
    }

    private void executeQueryAndPrintResults(String query) throws Exception {
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            if (isSelectQuery(query)) {
                try (ResultSet resultSet = stmt.executeQuery()) {
                    printSelectResults(resultSet);
                }
            } else {
                int rowsAffected = stmt.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected.");
            }
        }
    }

    private boolean isSelectQuery(String query) {
        String command = query.trim().split("\\s+")[0].toLowerCase();
        return command.equals("select");
    }

    private void printSelectResults(ResultSet resultSet) throws Exception {
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
        testCase3();
    }
}
