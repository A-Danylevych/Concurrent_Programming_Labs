package matrixMultiplier;

public final class Multiplier {

    public static Result FoxMultiply(int[][] firstMatrix, int[][] secondMatrix, int threadCount){
        CheckMatrixSize(firstMatrix, secondMatrix);
        CheckThreadAllocating(firstMatrix, secondMatrix, threadCount);

        int blockCount = GetBlockCount(threadCount);

        return foxCalculating(firstMatrix, secondMatrix, threadCount, blockCount);
    }


    private static Result foxCalculating(int[][] firstMatrix, int[][] secondMatrix, int threadCount,
                                         int blockCount){

        long start = System.currentTimeMillis();

        Block[] firstBlocks = Block.GetBlocks(firstMatrix, blockCount);
        Block[] secondBlocks = Block.GetBlocks(secondMatrix, blockCount);

        CheckBlockDivision(firstBlocks, secondBlocks, threadCount);

        IMatrixCalculator foxManager = new FoxManager(threadCount, blockCount, firstBlocks, secondBlocks,
                firstMatrix.length, secondMatrix[0].length);

        Result result = foxManager.Calculate();

        long finish = System.currentTimeMillis();
        result.Elapsed = finish - start;

        return result;
    }

    public static Result StripeMultiply(int[][] firstMatrix, int[][] secondMatrix, int threadCount){

        CheckThreadAllocating(firstMatrix, secondMatrix, threadCount);

        return stripeCalculating(firstMatrix, secondMatrix, threadCount);
    }

    private static Result stripeCalculating(int[][] firstMatrix, int[][] secondMatrix, int threadCount){
        long start = System.currentTimeMillis();

        IMatrixCalculator calculator = new StripeManager(firstMatrix, secondMatrix, threadCount);

        Result result  = calculator.Calculate();

        long finish = System.currentTimeMillis();
        result.Elapsed = finish - start;
        return result;
    }

    private static void CheckThreadAllocating(int[][] firstMatrix, int[][] secondMatrix, int threadCount) {
        if(threadCount > firstMatrix.length || threadCount > firstMatrix[0].length)
        {
            throw new IllegalArgumentException("Invalid threads count. Threads count must be greater than matrix size");
        }
        if (threadCount > secondMatrix.length || threadCount > secondMatrix[0].length){
            throw new IllegalArgumentException("Invalid threads count. Threads count must be greater than matrix size");
        }
        if (threadCount <= 0){
            throw new IllegalArgumentException("Invalid threads count");
        }
    }

    private static void CheckMatrixSize(int[][] firstMatrix, int[][] secondMatrix){
        if (firstMatrix[0].length != secondMatrix.length)
        {
            throw new IllegalArgumentException("Matrix has to be same size");
        }
    }

    private static int GetBlockCount(int threadCount){
        double blockCountDouble = Math.sqrt(threadCount);
        int blockCount;
        if (blockCountDouble % 1 == 0){
            blockCount = (int) blockCountDouble;
        }
        else {
            throw new IllegalArgumentException("Invalid threads count");
        }
        return blockCount;
    }

    private static void CheckBlockDivision(Block[] firstBlocks, Block[] secondBlocks, int threadCount){
        if(firstBlocks[threadCount-1].matrix[0].length != secondBlocks[threadCount-1].matrix.length){
            throw new IllegalArgumentException("Divided blocks cannot be multiplied");
        }
        if (firstBlocks[0].matrix[0].length != secondBlocks[0].matrix.length){
            throw new IllegalArgumentException("Divided blocks cannot be multiplied");
        }
    }
}
