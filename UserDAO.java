import java.sql.*;
public class UserDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/romeobank";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Bhargav@2208";

    // Establish a database connection
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Add a new user to the database
    public void addUser(String username, String password, int age, String email) {
        String sql = "INSERT INTO users (username, password, age, email, balance) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, age);
            stmt.setString(4, email);
            stmt.setDouble(5, 0.0); // Initial balance is 0.0
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Authenticate a user by username and password
    public int authenticateUser(String username, String password) {
        String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Authentication failed
    }
    
    // Get the balance of a user account
    public double getBalance(int accountId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Account not found
    }

    // Deposit an amount to a user's account
    public void deposit(int accountId, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Withdraw an amount from a user's account
    public boolean withdraw(int accountId, double amount) {
        String sql = "UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, amount);
            stmt.setInt(2, accountId);
            stmt.setDouble(3, amount);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // True if withdrawal was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Withdrawal failed
    }

    // Get user by username
    public int getUserByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // User not found
    }

    // Transfer funds from one user to another
    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        String withdrawSql = "UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?";
        String depositSql = "UPDATE users SET balance = balance + ? WHERE id = ?";
        try (Connection conn = connect()) {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSql);
                 PreparedStatement depositStmt = conn.prepareStatement(depositSql)) {

                // Withdraw funds from sender
                withdrawStmt.setDouble(1, amount);
                withdrawStmt.setInt(2, fromAccountId);
                withdrawStmt.setDouble(3, amount);
                int rowsUpdated = withdrawStmt.executeUpdate();

                if (rowsUpdated == 0) {
                    conn.rollback(); // Rollback if insufficient balance
                    return false;
                }

                // Deposit funds to receiver
                depositStmt.setDouble(1, amount);
                depositStmt.setInt(2, toAccountId);
                depositStmt.executeUpdate();

                conn.commit(); // Commit transaction
                return true;
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Transfer failed
    }
}
