package bank.mgmt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Withdraw extends JFrame implements ActionListener {
    private JLabel text;
    private JTextField amountField;
    private JButton withdrawButton, cancelButton;
    private long currentFormNo;

    public Withdraw(long formNo) {
        this.currentFormNo = formNo;

        // Set up frame
        setLayout(null);
        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        // Label
        text = new JLabel("Enter the amount you want to withdraw");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(170, 300, 400, 20);
        image.add(text);

        // Input field
        amountField = new JTextField();
        amountField.setBounds(170, 340, 300, 30);
        image.add(amountField);

        // Buttons
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(355, 485, 150, 30);
        withdrawButton.addActionListener(this);
        image.add(withdrawButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(355, 525, 150, 30);
        cancelButton.addActionListener(this);
        image.add(cancelButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdrawButton) {
            withdrawMoney();
        } else if (ae.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }

    private void withdrawMoney() {
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero.");
                return;
            }

            if (amount > 10000) {
                JOptionPane.showMessageDialog(this, "Maximum withdrawal limit is Rs. 10,000.");
                return;
            }

            if (deductBalance(amount)) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                amountField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance or account not found.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount format.");
        }
    }

    private boolean deductBalance(double amount) {
        String url = "jdbc:mysql://localhost:3306/bankmgmt";
        String user = "root";
        String password = "123456";

        String checkBalanceQuery = "SELECT balance FROM accounts WHERE formno = ?";
        String updateBalanceQuery = "UPDATE accounts SET balance = balance - ? WHERE formno = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Step 1: Check current balance
            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalanceQuery)) {
                checkStmt.setLong(1, currentFormNo);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");

                    if (currentBalance >= amount) {
                        // Step 2: Perform withdrawal
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateBalanceQuery)) {
                            updateStmt.setDouble(1, amount);
                            updateStmt.setLong(2, currentFormNo);
                            int rows = updateStmt.executeUpdate();
                            return rows > 0;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        return false;
    }

    public static void main(String[] args) {
        new Withdraw(4444); // Replace with actual form number
    }
}
