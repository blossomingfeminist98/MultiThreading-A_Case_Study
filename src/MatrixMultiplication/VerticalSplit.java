package MatrixMultiplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class VerticalSplit implements Runnable{
    public static Logger LOG = LogManager.getLogger(MatrixMultiplication.class);

    int matrixA[][], matrixB[][], resultant_matrix[][];
        int row_start, row_end, col_start, col_end, region;
        ReentrantLock[] lock;

        public VerticalSplit(int[][] a, int[][] b, int[][] c, ReentrantLock[] lock){
            this.matrixA = a;
            this.matrixB = b;
            this.resultant_matrix =c;
            this.lock = lock;

        }

        public void run(){

            switch (Thread.currentThread().getName()) {
                case "Thread-0":
                    this.row_start = 0;
                    this.row_end = matrixA.length;
                    this.col_start = 0;
                    this.col_end = matrixA.length / 2;
                    break;
//              case "Thread-1": break;
//              case "Thread-2": break;
                case "Thread-1":
                    this.row_start = 0;
                    this.row_end = matrixA.length;
                    this.col_start = matrixA[0].length / 2;
                    this.col_end = matrixA.length;
                    break;
                default:
                    LOG.info("Thread not found!");
                    break;
            }
            LOG.info(String.format("Executing Thread : %s Range[ %d - %d]x[%d - %d]", Thread.currentThread().getName(), row_start, row_end, col_start, col_end));

            for (int i = 0; i < matrixA.length; i++) {
                for (int j = col_start; j < col_end; j++) {
                    region = getRegion(i, j);
                    if (region == -1)
                        LOG.info("Region Not Found!");
                    for (int k = row_start; k < row_end; k++) {
                        lock[region].tryLock();
                        resultant_matrix[i][j] += matrixA[i][k] * matrixB[k][j];
                        lock[region].unlock();
                    }
                }
            }


        }
        public int getRegion(int i, int j) {
            if ((i == 0 || i < (matrixA.length / 2)) && (j == 0 || j < (matrixB[0].length / 2))) {
                return 0;
            } else if ((i == 0 || i < (matrixA.length / 2)) && (j == (matrixB[0].length / 2) || j < matrixB.length)) {
                return 1;
            } else if ((i == (matrixA.length / 2) || i < matrixA.length) && (j == 0 || j < (matrixB[0].length / 2))) {
                return 2;
            } else {
                return 3;
            }

        }
}
