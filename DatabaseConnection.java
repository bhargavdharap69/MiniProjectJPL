import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/romeobank"; // Replace "romeobank" with your DB name
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = "Bhargav@2208"; // Your MySQL password

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
        // Utility class - no instances allowed
    }

    /**
     * Get a connection to the database.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found. Ensure the MySQL JDBC driver is added to the project.");
        }

        // Return the connection to the database
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
