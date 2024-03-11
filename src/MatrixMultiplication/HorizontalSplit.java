package MatrixMultiplication;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class HorizontalSplit implements Runnable{
        int[][] matrixA, matrixB, resultant_matrix;
        int start, end, colstart, colend;
    public static Logger LOG = LogManager.getLogger(HorizontalSplit.class);

    public HorizontalSplit(int[][] a, int[][] b, int[][] c ){
            this.matrixA = a;
            this.matrixB = b;
            this.resultant_matrix = c;
        }
        public void run(){
            switch(Thread.currentThread().getName()){
                case "Thread-0": this.start=0;
                    this.end = matrixA.length/2;
                    this.colstart = 0;
                    this.colend = matrixB[0].length/2;
                    break;
                case "Thread-1": this.start=matrixA.length/2;
                    this.end = matrixA.length;
                    this.colstart=0;
                    this.colend= matrixA[0].length/2;
                    break;
                case "Thread-2": this.start=0;
                    this.end= matrixB.length/2;
                    this.colstart= matrixB[0].length/2;
                    this.colend= matrixB.length;
                    break;
                case "Thread-3": this.start= matrixB.length/2;
                    this.end= matrixB.length;
                    this.colstart=matrixB[0].length/2;
                    this.colend = matrixA.length;
                    break;
                default: LOG.info("Thread Not Found");
                    break;
            }

            LOG.info(String.format("Executing Thread : %s Range[ %d - %d]x[%d - %d]", Thread.currentThread().getName(), start, end, colstart, colend));
//        System.out.println("Thread Found");
            for(int i = start; i < end ; i++) {
                for(int j = colstart; j < colend ; j++) {
                    for(int k = 0; k < matrixB.length ; k++) {
                        resultant_matrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }

        }
}
