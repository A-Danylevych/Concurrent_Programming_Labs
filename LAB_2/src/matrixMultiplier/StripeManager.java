package matrixMultiplier;


import static matrixMultiplier.MatrixHelper.*;

public class StripeManager implements IMatrixCalculator{

    private final int[][] firstMatrix;
    private final int[][] secondMatrix;
    private final int threadCount;
    private final StripeThread[] threads;
    private final Result result;

    public StripeManager(int[][] firstMatrix, int[][] secondMatrix, int threadCount){
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.threadCount = threadCount;
        this.threads = new StripeThread[threadCount];
        this.result = new Result(firstMatrix.length, secondMatrix[0].length);
    }

    @Override
    public Result Calculate() {
        DistributeData();
        ConnectingWithNext();
        StartAndWait();
        return result;
    }
    private void DistributeData(){
        int begCol = 0;
        int begRow = 0;
        final int rowsPerThread = firstMatrix.length / threadCount;
        final int colsPerThread = secondMatrix[0].length / threadCount;
        int endRow = begRow + rowsPerThread-1;
        int endCol = begCol + colsPerThread-1;
        final int finalRow = firstMatrix.length-1;
        final int finalCol = secondMatrix[0].length-1;

        for (int i = 0; i < threadCount; i++){
            int[][] firstRows = GetRows(firstMatrix, begRow, endRow);
            int[][] secondColumns = GetColumns(secondMatrix, begCol, endCol);
            int[] rowIndexes = GetIndexes(begRow, endRow);
            int[] columnsIndexes = GetIndexes(begCol, endCol);
            threads[i] = new StripeThread(firstRows, secondColumns, rowIndexes, columnsIndexes,
                    result, threadCount);
            begCol += colsPerThread;
            begRow += rowsPerThread;
            endRow += rowsPerThread;
            endCol += colsPerThread;
        }

        for (int i = 0; i <= finalRow - begRow; i++){
            threads[i%threadCount].AddRow(firstMatrix[begRow + i], begRow + i);
        }
        for (int i = 0; i <= finalCol - begCol; i++){
            threads[(threadCount-1-i)%threadCount].AddColumn(GetColumn(secondMatrix,begCol + i),
                    begCol +i);
        }
    }
    private void StartAndWait(){
        for (StripeThread thread : threads){
            thread.start();
        }
        for (StripeThread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void ConnectingWithNext(){
        for(int i = 0; i < threadCount; i++){
            threads[i].SetNext(threads[(i+1)%threadCount]);
        }
    }


}
