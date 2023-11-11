import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MatrixOperations{

    public float[][] multiplyMat(float[][] MatA, float[][] MatB) {
        int rowsA = MatA.length;
        int colsA = MatA[0].length;
        int rowsB = MatB.length;
        int colsB = MatB[0].length;

        if (colsA != rowsB) {
            System.out.println("Matrix Multiplication is not possible. Please make sure that cols of the first matrix are equal to rows of the second matrix");
            return null;
        }

        float[][] ResultMat = new float[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                ResultMat[i][j] = 0.0f;
                for (int k = 0; k < colsA; k++) {
                    ResultMat[i][j] += MatA[i][k] * MatB[k][j];
                }
            }
        }
        return ResultMat;
    }

    public float[][] powerMat(float[][] Matrix, int n) 
    {
        int i = 1;
        float[][] ResultN = Matrix;
        while (i < n) {   
            ResultN = multiplyMat(ResultN, Matrix);
            i++;
        }
        return ResultN;
    }

    public boolean isColumnSum(float[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int j = 0; j < numCols; j++) {
            float columnSum = 0.0f;
            for (int i = 0; i < numRows; i++) {
                columnSum += matrix[i][j];
            }
            if (Math.abs(columnSum - 1.0f) > 1e-6) {
                return false;
            }
        }
        return true;
    }
}

public class Markov extends JFrame implements ActionListener {
    JTextField[][] probMat;
    JTextField[][] distMat;
    JTextField steps;
    JButton submit;
    JTextArea Res;

    public Markov() 
    {
        setTitle("Matrix Probability Calculator");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        
        JLabel label1 = new JLabel("Enter the probability matrix:");
        label1.setBounds(10, 10, 400, 30);
        label1.setFont(new Font("Segou UI", NORMAL, 25));
        add(label1);

        probMat = new JTextField[3][3];
        int X = 10;
        int Y = 45;
        for (int i = 0; i < probMat.length; i++) {
            for (int j = 0; j < probMat[i].length; j++) {
                probMat[i][j] = new JTextField();
                probMat[i][j].setBounds(X, Y, 80, 35);
                probMat[i][j].setFont(new Font("Segou UI", NORMAL, 20));
                add(probMat[i][j]);
                X += 100;
            }
            X = 10;
            Y += 40;
        }

        JLabel label2 = new JLabel("Enter the distribution matrix:");
        label2.setBounds(10, Y, 400, 20);
        label2.setFont(new Font("Segou UI", NORMAL, 25));
        add(label2);

        distMat = new JTextField[3][1]; 
        Y += 30;
        for (int i = 0; i < distMat.length; i++)  {
            distMat[i][0] = new JTextField();
            distMat[i][0].setBounds(10, Y, 80, 30);
            distMat[i][0].setFont(new Font("Segou UI", NORMAL, 25));
            add(distMat[i][0]);
            Y += 40;
        }

        JLabel label3 = new JLabel("Enter the number of iterations:");
        label3.setBounds(10, Y, 400, 20);
        label3.setFont(new Font("Segou UI", NORMAL, 25));
        add(label3);

        steps = new JTextField();
        steps.setBounds(10, Y + 35, 60, 30);
        steps.setFont(new Font("Segou UI", NORMAL, 25));

        add(steps);

        submit = new JButton("Submit");
        submit.setBounds(10, Y + 80, 80, 30);
        submit.setFocusable(false);
        submit.addActionListener(this);
        add(submit);

        JLabel label4 = new JLabel("RESULT: ");
        label4.setBounds(370, 15, 200, 30);
        label4.setFont(new Font("Segou UI", NORMAL, 25));
        add(label4);

        Res = new JTextArea();
        Res.setBounds(370, 45, 400, 300);
        Res.setFont(new Font("Segou UI", NORMAL, 20));

        add(Res);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {

            float[][] probMatrix = new float[3][3]; 
            float[][] distMatrix = new float[3][1]; 
            int iteration = Integer.parseInt(steps.getText());
            double total = 0;

            for (int i = 0; i < probMat.length; i++) {
                for (int j = 0; j < probMat[i].length; j++) {
                    probMatrix[i][j] = Float.parseFloat(probMat[i][j].getText());
                }
            }

            for (int i = 0; i < distMat.length; i++) {
                distMatrix[i][0] = Float.parseFloat(distMat[i][0].getText());
            }
            for (int i = 0; i < distMatrix.length; i++)
            {
                total += distMatrix[i][0];
            }
            if (total > 0)
            {
                for (int i = 0; i < distMatrix.length; i++) {
                        distMatrix[i][0] /= total;
                    }
            }

            MatrixOperations matrixOps = new MatrixOperations();
            if (matrixOps.isColumnSum(probMatrix))
            {
                float[][] resultMatrixN = matrixOps.powerMat(probMatrix, iteration);
                float[][] finalResultMatrix = matrixOps.multiplyMat(resultMatrixN, distMatrix);

                Res.setText("Distribution after " + iteration + " iterations:\n");
                for (int i = 0; i < finalResultMatrix.length; i++) {
                    for (int j = 0; j < finalResultMatrix[i].length; j++) {
                        Res.append((finalResultMatrix[i][j]*total)+" ");
                    }
                    Res.append("\n");
                }
            }
            else{
                Res.setText("The Probability Matrix should be stochastic, \nthat is, The sum of column in Probability\nmatrix should be 1.");
            }
        }
    }

    public static void main(String[] args) 
    {
            Markov calculator = new Markov();
            calculator.setVisible(true);
    }
}
