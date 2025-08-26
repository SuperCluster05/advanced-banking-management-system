package bank.mgmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Conn implements AutoCloseable {
    public Connection c;
    public Statement s;

    public Conn() {
        try {
            String url = "jdbc:mysql://localhost:3306/bankmgmt"; // Change DB name if needed
            String username = "root"; // Your MySQL username
            String password = "123456"; // Your MySQL password

            c = DriverManager.getConnection(url, username, password);
            s = c.createStatement();
            System.out.println("✅ Connection established");
        } catch (SQLException e) {
            System.out.println("❌ SQLException: " + e.getMessage());
        }
    }

    /**
     * Method to insert a deposit transaction into the 'bank' table.
     */
    public void deposit(String pin, String amount) throws SQLException {
        String query = "INSERT INTO bank(pin, date, mode, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = c.prepareStatement(query)) {
            pstmt.setString(1, pin);
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(3, "Deposit");
            pstmt.setString(4, amount);
            int rows = pstmt.executeUpdate();
            System.out.println("💰 Deposit recorded, rows affected: " + rows);
        }
    }

    @Override
    public void close() {
        try {
            if (s != null) s.close();
            if (c != null) c.close();
            System.out.println("🔒 Connection closed");
        } catch (SQLException e) {
            System.out.println("⚠️ Error while closing: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Optional test code
        try (Conn conn = new Conn()) {
            conn.deposit("1234", "5000"); // example test
        } catch (SQLException e) {
            System.out.println("Error during test deposit: " + e.getMessage());
        }
    }
}
