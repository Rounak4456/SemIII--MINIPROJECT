import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Final implements ActionListener
{

    JTextField t4;
    JButton b4;
    JTextArea result;
    int iteration;
    float[][] probMat;
    float[][] distMat;
    double total;

    Final(float[][] probMat, float[][] distMatrix, double total)
    {
        this.probMat = probMat;
        this.distMat = distMatrix;
        this.total = total;
        JFrame finalFrame = new JFrame();
        finalFrame.setSize(420, 420);
        finalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        finalFrame.setLayout(null);

        JLabel l4 = new JLabel("Enter the number of iterations: ");
        l4.setBounds(15, 15, 300, 30);
        l4.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        finalFrame.add(l4);

        t4 = new JTextField();
        t4.setBounds(290, 18, 70, 27);
        t4.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        finalFrame.add(t4);

        b4 = new JButton("Calculate");
        b4.setBounds(150, 60, 100, 30);
        b4.setFocusable(false);
        b4.addActionListener(this);
        finalFrame.add(b4);

        JLabel l5 = new JLabel("Distribution : ");
        l5.setBounds(15, 120, 400, 30);
        l5.setFont(new Font("Segoe UI", Font.PLAIN, 25));
        finalFrame.add(l5);


        result = new JTextArea();
        result.setBounds(15, 160, 370, 180);
        result.setFont(new Font("Segoe UI", Font.BOLD, 20));
        finalFrame.add(result);
 
        finalFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MatrixOperation mat = new MatrixOperation();
        if (e.getSource() == b4)
        {
            int iteration = Integer.parseInt(t4.getText());
            if (mat.isColumnSum(probMat))
            {
                float[][] resultMatrixN = mat.powerMat(probMat, iteration);
                float[][] finalResultMatrix = mat.multiplyMat(resultMatrixN, distMat);

                for (int i = 0; i < finalResultMatrix.length; i++) 
                {
                    for (int j = 0; j < finalResultMatrix[i].length; j++) 
                    {
                        int abs = (int) Math.round(finalResultMatrix[i][j]);
                        result.append(abs+ " ");
                    }
                    result.append("\n");
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "The Probability Matrix should be stochastic, that is, the sum of probabilities in each Column should be 1", null, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
