package bank.mgmt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Deposit extends JFrame implements ActionListener {

    JTextField t1;
    JButton b1, b2;
    JLabel l1;
    String pin;

    Deposit(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel background = new JLabel(i3);
        background.setBounds(0, 0, 960, 1080);
        this.add(background);

        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));

        b1 = new JButton("DEPOSIT");
        b2 = new JButton("BACK");

        setLayout(null);
        l1.setBounds(190, 350, 400, 35);
        background.add(l1);

        t1.setBounds(190, 420, 320, 25);
        background.add(t1);

        b1.setBounds(390, 588, 150, 35);
        background.add(b1);

        b2.setBounds(390, 633, 150, 35);
        background.add(b2);

        b1.addActionListener(this);
        b2.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String amountStr = t1.getText().trim();

        if (ae.getSource() == b1) {
            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit");
                return;
            }
            try {
                long amount = Long.parseLong(amountStr);

                try (Conn c1 = new Conn()) {
                    c1.deposit(pin, amount);
                }

                JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully");
                this.setVisible(false);
                new Transactions(pin).setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == b2) {
            this.setVisible(false);
            new Transactions(pin).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}
