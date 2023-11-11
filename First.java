import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class First extends JFrame implements ActionListener 
{
    public int choice;
    JButton b1;
    JLabel l1;
    JTextField t1;
    First()
    {
        JFrame first = new JFrame();
        first.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        first.setSize(300, 150);
        first.setLayout(null);
        

        l1 = new JLabel("Enter the number of states: ");
        l1.setBounds(10,20,200, 20);
        l1.setFont(new Font("Segou UI", Font.PLAIN, 15));
        first.add(l1);


        t1 = new JTextField();
        t1.setBounds(200, 20, 70, 20);
        first.add(t1);

        b1 = new JButton("Generate Matrix");
        b1.setBounds(75, 60, 130, 30);
        b1.setFocusable(false);
        b1.addActionListener(this);
        first.add(b1);

        first.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
        if(e.getSource()== b1)
        {
            try 
            {
                choice = Integer.parseInt(t1.getText());
                Second second = new Second(choice);
                
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(l1, "Please enter a valid integer 'n'.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
public static void main(String[] args) 
    {
        First first = new First();
    }
}

