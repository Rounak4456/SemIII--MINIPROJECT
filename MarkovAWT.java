import java.awt.*;
import java.awt.event.*;

class MatrixOperations 
{

    public float[][] multiplyMat(float[][] MatA, float[][] MatB) 
    {
        int rowsA = MatA.length;
        int colsA = MatA[0].length;
        int rowsB = MatB.length;
        int colsB = MatB[0].length;

        if (colsA != rowsB) 
        {
            System.out.println("Matrix Multiplication is not possible. Please make sure that cols of the first matrix are equal to rows of the second matrix");
            return null;
        }

        float[][] ResultMat = new float[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) 
        {
            for (int j = 0; j < colsB; j++) 
            {
                ResultMat[i][j] = 0.0f;
                for (int k = 0; k < colsA; k++) 
                {
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
        while (i < n) 
        {
            ResultN = multiplyMat(ResultN, Matrix);
            i++;
        }
        return ResultN;
    }

}

public class MarkovAWT extends Frame implements ActionListener 
{
    TextField[][] probMat;
    TextField[][] distMat;
    TextField steps;
    Button submit;
    TextArea Res;

    public MarkovAWT() 
    {
        setTitle("Matrix Probability Calculator");
        setSize(850, 600);
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent we) 
            {
                System.exit(0);
            }
        });
        setLayout(null);
        setResizable(false);

        Label label1 = new Label("Enter the probability matrix:");
        label1.setBounds(20, 30, 350, 30);
        label1.setFont(new Font("Segou UI", Font.PLAIN, 25));
        add(label1);

        probMat = new TextField[3][3];
        int X = 30;
        int Y = 60;
        for (int i = 0; i < probMat.length; i++) {
            for (int j = 0; j < probMat[i].length; j++) {
                probMat[i][j] = new TextField();
                probMat[i][j].setBounds(X, Y, 80, 35);
                probMat[i][j].setFont(new Font("Segou UI", Font.PLAIN, 20));
                add(probMat[i][j]);
                X += 100;
            }
            X = 30;
            Y += 40;
        }

        Label label2 = new Label("Enter the distribution matrix:");
        label2.setBounds(20, Y, 350, 30);
        label2.setFont(new Font("Segou UI", Font.PLAIN, 25));
        add(label2);

        distMat = new TextField[3][1];
        Y += 30;
        for (int i = 0; i < distMat.length; i++) {
            distMat[i][0] = new TextField();
            distMat[i][0].setBounds(30, Y, 80, 30);
            distMat[i][0].setFont(new Font("Segou UI", Font.PLAIN, 25));
            add(distMat[i][0]);
            Y += 40;
        }

        Label label3 = new Label("Enter the number of iterations:");
        label3.setBounds(20, Y, 400, 20);
        label3.setFont(new Font("Segou UI", Font.PLAIN, 25));
        add(label3);

        steps = new TextField();
        steps.setBounds(30, Y + 35, 60, 30);
        steps.setFont(new Font("Segou UI", Font.PLAIN, 25));
        add(steps);

        submit = new Button("Submit");
        submit.setBounds(30, Y + 80, 80, 30);
        submit.addActionListener(this);
        add(submit);

        Label label4 = new Label("RESULT: ");
        label4.setBounds(370, 30, 200, 30);
        label4.setFont(new Font("Segou UI", Font.PLAIN, 25));
        add(label4);

        Res = new TextArea();
        Res.setBounds(370, 65, 400, 300);
        Res.setFont(new Font("Segou UI", Font.PLAIN, 20));
        add(Res);
    }


    public boolean isColumnSum(float[][] matrix) 
    {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int j = 0; j < numCols; j++) 
        {
            float columnSum = 0.0f;
            for (int i = 0; i < numRows; i++) 
            {
                columnSum += matrix[i][j];
            }
            if (Math.abs(columnSum - 1.0f) > 1e-6) 
            {
                return false;
            }
        }
        return true;
    }




    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource() == submit) 
        {

            float[][] probMatrix = new float[3][3];
            float[][] distMatrix = new float[3][1];
            int iteration = Integer.parseInt(steps.getText());
            double total = 0;

            for (int i = 0; i < probMat.length; i++) 
            {
                for (int j = 0; j < probMat[i].length; j++) 
                {
                    probMatrix[i][j] = Float.parseFloat(probMat[i][j].getText());
                }
            }

            for (int i = 0; i < distMat.length; i++) 
            {
                distMatrix[i][0] = Float.parseFloat(distMat[i][0].getText());
            }
            for (int i = 0; i < distMatrix.length; i++) 
            {
                total += distMatrix[i][0];
            }
            if (total > 0) 
            {
                for (int i = 0; i < distMatrix.length; i++) 
                {
                    distMatrix[i][0] /= total;
                }
            }

            MatrixOperations matrixOps = new MatrixOperations();
            if (isColumnSum(probMatrix))
            {
                float[][] resultMatrixN = matrixOps.powerMat(probMatrix, iteration);
                float[][] finalResultMatrix = matrixOps.multiplyMat(resultMatrixN, distMatrix);

                Res.setText("Distribution after " + iteration + " iterations:\n");
                for (int i = 0; i < finalResultMatrix.length; i++) 
                {
                    for (int j = 0; j < finalResultMatrix[i].length; j++) 
                    {
                        Res.append((finalResultMatrix[i][j] * total) + " ");
                    }
                    Res.append("\n");
                }
            } 
            else 
            {
                Res.setText("The Probability Matrix should be stochastic, \nthat is, The sum of column in Probability\nmatrix should be 1.");
            }
        }
    }

    public static void main(String[] args) 
    {
        MarkovAWT calculator = new MarkovAWT();
        calculator.setVisible(true);
    }
}
