
package bank.mgmt;
import javax.swing.*;
import java.awt.*;
import java.util.*;


public class Signup2 extends JFrame {
    Signup2(){
        setLayout(null);
        
        JLabel formno = new JLabel("Page2");
        formno.setFont(new Font("Verdana",Font.BOLD,38));
        formno.setBackground(Color.BLACK);
        formno.setBounds(140,20,600,40);
        add(formno);
        getContentPane().setBackground(Color.BLACK);
        setSize(850,900);
        setLocation(350,10);
        setVisible(true);
        
        JLabel Mpin = new JLabel("Mpin Confirmation");
        add(Mpin);
        Mpin.setFont(new Font("Verdana",Font.BOLD,38));
        Mpin.setForeground(Color.WHITE);
        Mpin.setBounds(250,150,400, 40);
        
        JPasswordField MpinTextField = new JPasswordField();
        MpinTextField.setBounds(350,200,100,40);
        MpinTextField.setBackground(Color.WHITE);
        MpinTextField.setForeground(Color.BLACK);
        MpinTextField.setFont(new Font("Verdana",Font.BOLD,15));
        add(MpinTextField);
        
        JButton submit = new JButton("Submit");
        submit.setBackground(Color.WHITE);
        submit.setForeground(Color.WHITE);
        submit.setBounds(620,660,80,30);
        add(submit);
        
    
     
}
    public static void main(String args[]){
        new Signup2();
    }
 


}
