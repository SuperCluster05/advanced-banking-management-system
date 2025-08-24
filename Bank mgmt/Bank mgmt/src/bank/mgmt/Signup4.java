
package bank.mgmt;
import java.awt.*;
import javax.swing.*;


public class Signup4 extends JFrame {
    JRadioButton r1,r2,r3,r4;
    Signup4(){
        JLabel l1 = new JLabel("Page 3: Account details");
        l1.setFont(new Font("Railway",Font.BOLD,22));
        l1.setBounds(280,40,400,40);

        setSize(850,820);
        setLocation(350,0);
        setVisible(true);
        setLayout(null);
       r1 = new JRadioButton("Saving Account");
       r1.setFont(new Font("Verdana",Font.BOLD,16));
       r1.setBackground(Color.WHITE);
       r1.setBounds(100,180,250,16);
       add(r1);


       r2= new JRadioButton("Fixed Deposit Account");
       r2.setFont(new Font("Verdana",Font.BOLD,16));
       r2.setBackground(Color.WHITE);
       r2.setBounds(350,180,250,16);
       add(r2);

       r3= new JRadioButton("Current Account");
       r3.setFont(new Font("Verdana",Font.BOLD,16));
       r3.setBackground(Color.WHITE);
       r3.setBounds(100,220,250,16);
       add(r3);

       r4=  new JRadioButton("Recurring Deposit Account");
       r4.setFont(new Font("Verdana",Font.BOLD,16));
       r4.setBackground(Color.WHITE);
       r4.setBounds(350,220,300,16);
       add(r4);

       ButtonGroup groupaccount = new ButtonGroup();
       groupaccount.add(r1);
       groupaccount.add(r2);
       groupaccount.add(r3);
       groupaccount.add(r4);

       JLabel card = new JLabel("Card Number");
       card.setFont(new Font("Verdana",Font.BOLD,22));
       card.setForeground(Color.WHITE);
       card.setBounds(100,300,200,30);
       add(card);

       JLabel Pin = new JLabel("Pin");
       Pin.setFont(new Font("Verdana",Font.BOLD,22));
       Pin.setBounds(100,370,200,30);
       Pin.setForeground(Color.WHITE);
       add(Pin);

       JLabel carddetails = new JLabel("Your 16 digiit card number");
       carddetails.setFont(new Font("Verdana",Font.BOLD,22));
       carddetails.setBounds(100,330,500,30);
       
       add(carddetails);

       JLabel number = new JLabel("XXXX-XXXX-XXXX-2553");
       number.setFont(new Font("Verdana",Font.BOLD,22));
       number.setBounds(330,300,300,30);
       number.setForeground(Color.WHITE);
       add(number);
  

       

       JLabel pnumber = new JLabel("XXXX");
       pnumber.setFont(new Font("Verdana",Font.BOLD,12));
       pnumber.setBounds(300,370,300,30);
       pnumber.setForeground(Color.WHITE);
       add(pnumber);

       JLabel pindetail = new JLabel("Your 4 Digit Password");
       pindetail.setFont(new Font("Verdana",Font.BOLD,12));
       pindetail.setBounds(100,400,300,20);
       add(pindetail);
       

       //Background color
       getContentPane().setBackground(Color.BLACK);


       setSize(850,820);
       setLocation(350,0);
       setVisible(true);






    }

    public static void main(String args[]){
        new Signup4();

    }



}

