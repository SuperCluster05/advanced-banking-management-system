package bank.mgmt;  

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.sql.*;  
import java.util.Random;  

public class Signup1 extends JFrame implements ActionListener {  
    private long random; // Registration form number  
    private JPasswordField MpinTextField; // MPIN input field  
    private JButton next; // Next button  

    // Constructor  
    Signup1() {  
        setLayout(null);  
        generateRandomFormNo();  

        JLabel formnoLabel = createLabel("Registration form no. " + random, 140, 20, 600, 40, 38);  
        add(formnoLabel);  

        JLabel MpinLabel = createLabel("Create MPIN", 250, 150, 400, 40, 38);  
        add(MpinLabel);  

        MpinTextField = new JPasswordField();  
        MpinTextField.setBounds(350, 200, 100, 40);  
        MpinTextField.setBackground(Color.WHITE);  
        MpinTextField.setForeground(Color.BLACK);  
        MpinTextField.setFont(new Font("Verdana", Font.BOLD, 15));  
        add(MpinTextField);  

        next = new JButton("Next");  
        next.setBounds(620, 660, 80, 30);  
        next.setBackground(Color.CYAN);  
        next.setForeground(Color.BLACK);  
        next.addActionListener(this);  
        add(next);  

        // Frame settings  
        getContentPane().setBackground(Color.BLACK);  
        setSize(850, 900);  
        setLocation(350, 10);  
        setVisible(true);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }  

    // Method to generate a random form number  
    private void generateRandomFormNo() {  
        Random ran = new Random();  
        random = Math.abs((ran.nextLong() % 9000L) + 1000L);  
    }  

    // Helper method to create labels  
    private JLabel createLabel(String text, int x, int y, int width, int height, int fontSize) {  
        JLabel label = new JLabel(text);  
        label.setFont(new Font("Verdana", Font.BOLD, fontSize));  
        label.setForeground(Color.WHITE);  
        label.setBounds(x, y, width, height);  
        return label;  
    }  

    // Action handling for the button  
    @Override  
    public void actionPerformed(ActionEvent ae) {  
        String Mpin = new String(MpinTextField.getPassword());  
        String formno = Long.toString(random);  

        if (Mpin.isEmpty()) {  
            JOptionPane.showMessageDialog(this, "MPIN is required", "Input Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        // Ensure MPIN meets basic length requirement  
        if (Mpin.length() < 4) {  
            JOptionPane.showMessageDialog(this, "MPIN must be at least 4 digits long", "Input Error", JOptionPane.ERROR_MESSAGE);  
            return;  
        }  

        // Attempt to connect to the database and insert data  
        try (Conn c = new Conn();  
             PreparedStatement myStmt = c.c.prepareStatement("INSERT INTO signup (formno, Mpin) VALUES (?, ?)")) {  
            
            myStmt.setString(1, formno);  
            myStmt.setString(2, Mpin);  

            myStmt.executeUpdate();  
            JOptionPane.showMessageDialog(this, "Signup successful! Proceed to the next step.","Success", JOptionPane.INFORMATION_MESSAGE);  
            // Optionally, you might want to navigate to the next step or clear fields  
        } catch (SQLException e) {  
            JOptionPane.showMessageDialog(this, "Error processing your request. Please try again later.", "Database Error", JOptionPane.ERROR_MESSAGE);  
            e.printStackTrace();  
        }  
    }  

    // Main Method  
    public static void main(String[] args) {  
        new Signup1();  
    }  
}