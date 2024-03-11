package MatrixMultiplication;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixMultiplication {

    public static Logger LOG = LogManager.getLogger(MatrixMultiplication.class);
    public static Random random = new Random();

    static {
        random.setSeed(20L);
    }


    public void compute(String strategy, int matrix_size, int thread_size) {
        int[][] matrixA, matrixB, resultant_matrix;
        try {
            matrixA = new int[matrix_size][matrix_size];
            for (int i = 0; i < matrixA.length; i++)
                for (int j = 0; j < matrixA.length; j++)
                    matrixA[i][j] = random.nextInt(1000) + 1;
            matrixB = new int[matrix_size][matrix_size];
            for (int i = 0; i < matrixB.length; i++)
                for (int j = 0; j < matrixB.length; j++)
                    matrixB[i][j] = random.nextInt(1000) + 1;
            resultant_matrix = new int[matrixA.length][matrixB.length];
            Thread[] thread_arr = new Thread[thread_size];
            LOG.info("Matrix Multiplication Initiated....");
            long t1=System.currentTimeMillis();

            MDC.put("userName", UUID.randomUUID());
            if(strategy.equalsIgnoreCase("HorVerSplit")) {
                for(int i=0;i<thread_arr.length;i++){
                    thread_arr[i] = new Thread(new HorizontalSplit(matrixA, matrixB, resultant_matrix), "Thread-" + i);
                    thread_arr[i].start();
                }
            } else if(strategy.equalsIgnoreCase("VerHorSplit")) {
                ReentrantLock[] lockList = new ReentrantLock[4];
                for(int i = 0 ; i < lockList.length ; i++) {
                    lockList[i] = new ReentrantLock();
                }
                for (int i = 0; i < thread_arr.length; i++) {
                    thread_arr[i] = new Thread(new VerticalSplit(matrixA, matrixB, resultant_matrix, lockList), "Thread-"+i);
                    thread_arr[i].start();
                }
            } else if(strategy.equalsIgnoreCase("HorSplit")) {
                for(int i=0;i<thread_arr.length;i++){
                    thread_arr[i] = new Thread(new SplitThread(matrixA, matrixB, resultant_matrix, matrix_size, thread_size, i), "Thread-" + i);
                    thread_arr[i].start();
                }
            }
            for(int i=0;i<thread_arr.length;i++)
                thread_arr[i].join();
            long t2=System.currentTimeMillis();
            LOG.info(String.format("Multiplication finished in "+ (t2-t1)/1000f) + " sec successfully");

//            display(resultant_matrix);
        } catch (Exception e) {
            LOG.error("Error in MatrixMultiplication.compute (Invalid strategy) : ", e);
        }
    }

    public static void display(int[][] result){
        for(int i=0;i<result.length;i++) {
            for(int j=0;j<result.length;j++)
                System.out.print(result[i][j] + " ");
            System.out.println();
        }
    }
}
