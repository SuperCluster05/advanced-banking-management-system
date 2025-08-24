package bank.mgmt;  

import javax.swing.*;  
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  

public class Transactions extends JFrame implements ActionListener {  
    private JButton deposit, withdrawal, balanceEnquiry, fastCash, pinChange, miniStatement, exit;  
    private long currentFormNo; // Current form number for the user  

    public Transactions(long formNo) {  
        currentFormNo = formNo;  

        setLayout(null);  

        // Load and set background image  
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));  
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);  
        ImageIcon i3 = new ImageIcon(i2);  
        JLabel image = new JLabel(i3);  
        image.setBounds(0, 0, 900, 900);  
        add(image);  

        getContentPane().setBackground(Color.BLACK); // Set frame background color  

        setSize(900, 900);  
        setLocation(300, 0);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure app closes properly  
        setUndecorated(true); // Remove frame decorations  
        setVisible(true); // Make frame visible  

        JLabel text = new JLabel("Please select your Transaction");  
        text.setBounds(250, 200, 700, 35);  
        text.setForeground(Color.BLACK);  
        text.setFont(new Font("System", Font.BOLD, 16));  
        image.add(text);  

        createButtons(image);  

        setVisible(true);  
    }  

    // Method to create buttons  
    private void createButtons(JLabel image) {  
        // Create and configure buttons  
        deposit = createTransactionButton("Deposit", 170, 315, image);  
        withdrawal = createTransactionButton("Withdrawal", 355, 315, image);  
        fastCash = createTransactionButton("Fast Cash", 170, 350, image);  
        miniStatement = createTransactionButton("Mini Statement", 355, 350, image);  
        pinChange = createTransactionButton("Pin Change", 170, 385, image);  
        balanceEnquiry = createTransactionButton("Balance Enquiry", 355, 385, image);  
        exit = createTransactionButton("Exit", 355, 420, image);  
    }  

    // Helper method to create individual transaction buttons  
    private JButton createTransactionButton(String text, int x, int y, JLabel image) {  
        JButton button = new JButton(text);  
        button.setBounds(x, y, 150, 30);  
        button.addActionListener(this);  
        image.add(button);  
        return button;  
    }  

    @Override  
    public void actionPerformed(ActionEvent ae) {  
        Object source = ae.getSource();  
        if (source == exit) {  
            System.exit(0);  
        } else if (source == deposit) {  
            new Deposit(currentFormNo); // Open deposit window  
        } else if (source == withdrawal) {  
            new Withdrawal(currentFormNo); // Open withdrawal window  
        } else if (source == balanceEnquiry) {  
            new BalanceEnquiry(currentFormNo); // Open balance enquiry window  
        } else if (source == fastCash) {  
            new FastCash(currentFormNo); // Open fast cash window  
        } else if (source == pinChange) {  
            new PinChange(currentFormNo); // Open pin change window  
        } else if (source == miniStatement) {  
            new MiniStatement(currentFormNo); // Open mini statement window  
        }  
    }  

    public static void main(String[] args) {  
        // Replace with a valid form number for testing  
        new Transactions(1234567890L);  
    }  
}