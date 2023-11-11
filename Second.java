import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Second implements ActionListener
{
    int choice;
    JTextField[][] t2;
    JButton b2;

    public Second(int choice)
    {
        this.choice = choice;
        JFrame second = new JFrame("Probability Matrix");
        second.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        second.setLayout(new GridLayout(choice + 1, choice));

        t2 = new JTextField[choice][choice];

        for (int i = 0; i < choice; i++)
        {
            for (int j = 0; j < choice; j++)
            {
                t2[i][j] = new JTextField();
                t2[i][j].setFont(new Font("Segou UI", Font.PLAIN, 20));
                t2[i][j].setHorizontalAlignment(JTextField.CENTER);
                second.add(t2[i][j]);
            }
        }
        
        b2 = new JButton("Next");
        b2.setFocusable(false);
        b2.addActionListener(this);
        second.add(b2);


        second.pack();
        second.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)  
    { 
        float[][] probMat = new float[choice][choice];
        for(int i = 0; i < probMat.length; i++) 
        {
            for (int j = 0; j < probMat[i].length; j++) 
            {
                probMat[i][j] = Float.parseFloat(t2[i][j].getText());
            }
        }

        boolean decimal = false;
        for(int i = 0; i < probMat.length; i++) 
        {
            for (int j = 0; j < probMat[i].length; j++) 
            {
                if (probMat[i][j] >= 1)
                {
                    decimal = true;
                }
            }
        }
        
        if (decimal)
        {
            JOptionPane.showMessageDialog(null, "The values must be less than 1", null, JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Third third = new Third(choice, probMat);
        }
    }
}
