package matrixMultiplier;

public class FoxManager implements IMatrixCalculator, IBlockContainer {
    private final int _threadCount;
    private final int _blockCount;
    private final Block[] _firstBlocks;
    private final Block[] _secondBlocks;
    private final int _firstSize;
    private final int _secondSize;
    private FoxThread[] _foxThreads;


    public FoxManager(int threadCount, int blockCount, Block[] firstMatrixBlocks, Block[] secondMatrixBlocks,
                      int firstMatrixRowsCount, int secondMatrixColumnsCount) {
        _threadCount = threadCount;
        _blockCount = blockCount;
        _firstBlocks = firstMatrixBlocks;
        _secondBlocks = secondMatrixBlocks;
        _firstSize = firstMatrixRowsCount;
        _secondSize = secondMatrixColumnsCount;
    }

    private Result CreateResult(int firstSize, int secondSize){
        Result result = new Result(firstSize, secondSize);
        result.SetOffsets(firstSize/_blockCount-1, secondSize/_blockCount-1);
        return result;
    }
    private void WaitCalculation(){
        for (FoxThread thread :
                _foxThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void CreateThreads(Result result){
        _foxThreads = new FoxThread[_threadCount];

        int index = 0;
        for (int i =0; i<_blockCount; i++){
            for (int j = 0; j < _blockCount; j++){
                _foxThreads[index] = new FoxThread(result, i, j, _blockCount, this);
                index++;
            }
        }
    }
    private void StartThreads(){
        for (FoxThread thread :
                _foxThreads) {
            thread.start();
        }
    }
    @Override
    public int[][] GetFirstMatrix(int i, int iteration){
        return _firstBlocks[(i + iteration)%_blockCount + i * _blockCount].matrix;
    }
    @Override
    public int[][] GetSecondMatrix(int i, int j, int iteration){
        return _secondBlocks[j + (i +iteration)  % _blockCount * _blockCount ].matrix;
    }

    @Override
    public Result Calculate() {
        Result result = CreateResult(_firstSize, _secondSize);
        CreateThreads(result);
        StartThreads();
        WaitCalculation();
        return result;
    }
}
