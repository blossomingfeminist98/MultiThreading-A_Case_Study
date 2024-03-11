package MatrixMultiplication;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SplitThread implements  Runnable{
        int[][] matrixA, matrixB, resultant_matrix;
        int start, end, matrix_size, thread_size, thread_name;
    public static Logger LOG = LogManager.getLogger(SplitThread.class);

    public SplitThread(int[][] a, int[][] b, int[][] c , int n, int t, int i){
            this.matrixA = a;
            this.matrixB = b;
            this.resultant_matrix = c;
            this.matrix_size = n;
            this.thread_size = t;
            this.thread_name = i;
        }
        public void run(){
            if(thread_name == thread_size -1){
                this.start = thread_name * ( matrix_size / thread_size );
                this.end = matrix_size;
            }
            else {
                this.start = thread_name * ( matrix_size / thread_size );
                this.end = (thread_name+1) * ( matrix_size / thread_size );
            }
            LOG.info(String.format("Executing Thread : %s Range[ %d - %d]x[0 - %d]", Thread.currentThread().getName(), start, end, matrix_size-1));


            for(int i = start; i < end ; i++) {
                for(int j = 0; j < matrixB[0].length ; j++) {
                    for(int k = 0; k < matrixB.length ; k++) {
                        resultant_matrix[i][j] += matrixA[i][k] * matrixB[k][j];
                    }
                }
            }
        }

    }
