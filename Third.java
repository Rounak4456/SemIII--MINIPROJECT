import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Third implements ActionListener
{

    int choice;
    JButton b3;
    JTextField[][] t3;
    float[][] probMat;

    public Third(int choice, float[][] probMat)
    {
        this.choice = choice;
        this.probMat = probMat;
        JFrame third = new JFrame("Distribution");
        third.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        third.setLayout(new GridLayout(choice+1, 1));
        
        t3 = new JTextField[choice][1];
        for (int i = 0; i < choice; i++)
        {
            t3[i][0] = new JTextField();
            t3[i][0].setFont(new Font("Segou UI", Font.PLAIN, 20));
            t3[i][0].setHorizontalAlignment(JTextField.CENTER);
            third.add(t3[i][0]);
        }

        b3 = new JButton("Next");
        b3.setFocusable(false);
        b3.addActionListener(this);
        third.add(b3);

        third.pack();
        third.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        float[][] distMat = new float[choice][1];
        double total = 0;
        for (int i = 0; i < choice; i++)
        {
            distMat[i][0] = Float.parseFloat(t3[i][0].getText());
            total += distMat[i][0];
        }
        
        Final finalframe = new Final(probMat, distMat, total);

    }
}
