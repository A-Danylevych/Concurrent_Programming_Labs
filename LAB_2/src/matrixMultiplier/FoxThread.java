package matrixMultiplier;

public class FoxThread extends Thread{
    private final Result _result;
    private final int _i;
    private final int _j;
    private int[][] _firstMatrix;
    private int[][] _secondMatrix;
    private final int _iterationCount;
    private final IBlockContainer _manager;

    public FoxThread(Result result, int i, int j, int iterationCount, IBlockContainer manager) {
        _result = result;
        _i = i;
        _j = j;
        _iterationCount = iterationCount;
        _manager = manager;
    }
    public void GetValues(int iteration){
        _firstMatrix = _manager.GetFirstMatrix(_i, iteration);
        _secondMatrix = _manager.GetSecondMatrix(_i, _j, iteration);
    }

    @Override
    public void run() {
        for (int i = 0; i<_iterationCount; i++){
            GetValues(i);
            Multiply();
        }
    }

    private void Multiply(){
        int[][] result = MatrixHelper.Multiply(_firstMatrix, _secondMatrix);
        _result.SetValue(_i, _j, result);
    }
}
