package bank.mgmt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 400;
    private static final Font LABEL_FONT = new Font("Verdana", Font.BOLD, 24);

    JButton loginBtn, clearBtn;
    JTextField upiTextField;
    JPasswordField passTextField;

    public Login() {
        setTitle("AUTOMATED TELLER MACHINE");
        setLayout(null);

        // Bank Logo
        ImageIcon atmImage = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));
        Image scaledImage = atmImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setBounds(50, 20, 100, 100);
        add(imageLabel);

        // Bank Name
        JLabel bankName = new JLabel("Canara Bank");
        bankName.setFont(new Font("Verdana", Font.BOLD, 30));
        bankName.setForeground(Color.WHITE);
        bankName.setBounds(180, 40, 400, 40);
        add(bankName);

        // UPI ID Label
        JLabel upiLabel = new JLabel("UPI ID:");
        upiLabel.setFont(LABEL_FONT);
        upiLabel.setForeground(Color.WHITE);
        upiLabel.setBounds(100, 140, 200, 40);
        add(upiLabel);

        upiTextField = new JTextField();
        upiTextField.setBounds(250, 140, 200, 35);
        upiTextField.setFont(new Font("Verdana", Font.PLAIN, 16));
        upiTextField.setBackground(Color.BLACK);
        upiTextField.setForeground(Color.WHITE);
        add(upiTextField);

        // Passcode Label
        JLabel passLabel = new JLabel("Passcode:");
        passLabel.setFont(LABEL_FONT);
        
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(100, 190, 200, 40);
        add(passLabel);

        passTextField = new JPasswordField();
        passTextField.setBounds(250, 190, 200, 35);
        passTextField.setBackground(Color.BLACK);
        passTextField.setForeground(Color.WHITE);
        passTextField.setFont(new Font("Verdana", Font.PLAIN, 16));
        add(passTextField);

        // Biometric Button
        loginBtn = new JButton("Biometric");
        loginBtn.setBounds(250, 250, 100, 35);
        loginBtn.setBackground(Color.CYAN);
        loginBtn.setForeground(Color.BLACK);
        loginBtn.addActionListener(this);
        add(loginBtn);

        // Clear Button
        clearBtn = new JButton("Clear");
        clearBtn.setBounds(360, 250, 90, 35);
        clearBtn.setBackground(Color.CYAN);
        clearBtn.setForeground(Color.BLACK);
        clearBtn.addActionListener(this);
        add(clearBtn);

        // Background Color
        getContentPane().setBackground(Color.BLACK);

        // Frame setup
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == clearBtn) {
            upiTextField.setText("");
            passTextField.setText("");
        } else if (ae.getSource() == loginBtn) {
            String upi = upiTextField.getText().trim();
            String pass = new String(passTextField.getPassword()).trim();

            if (upi.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both UPI ID and Passcode.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Conn conn = new Conn();
                 PreparedStatement pstmt = conn.c.prepareStatement("SELECT * FROM login WHERE upi = ? AND pin = ?")) {

                pstmt.setString(1, upi);
                pstmt.setString(2, pass);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "Login successful");
                        // Navigate to next screen
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect UPI ID or Passcode", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database Error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
