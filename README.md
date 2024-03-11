# Implementing Matrix Multiplication and Array Summation using Multithreading in Java
Argument Format:

-- ["Type"  "Strategy" "Size" "Thread_size"]

-- e.g -> [ArraySum ThreadPool 1000000 4]

-- e.g -> [MatrixMultiplication HorSplit 6500 8]

-- Type -> MatrixMultiplication | ArraySum

-- Strategies ->

-- For Matrix Multiplication = HorVerSplit, VerHorSplit, HorSplit

-- For Array sum = ForkJoinPool, ThreadPool, ManualThread

-- Preferred values for thread size - [1, 4, 8 32]


# To run the jar file ->
    java -cp ./Assignment_Threads.jar Main ArraySum ThreadPool 1000000 4

!-----------------------------------------------------------------------------!
