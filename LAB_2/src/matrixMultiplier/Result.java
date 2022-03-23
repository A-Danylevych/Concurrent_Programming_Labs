package matrixMultiplier;

import static matrixMultiplier.MatrixHelper.Multiply;

public class Result {
    private int[][] _matrix;
    public long Elapsed;
    private int _iOffset;
    private int _jOffset;
    public int ProcessCount;

    public Result(int firstSize, int secondSize){
        _matrix = new int[firstSize][secondSize];
    }
    public void SetOffsets(int iOffset, int jOffset){
        _iOffset = iOffset;
        _jOffset = jOffset;
    }

    public void SetValue(int i, int j, int value){
        _matrix[i][j] = value;
    }
    public void SetValue(int iIndex, int jIndex, int[][] value){
        int startI = iIndex + iIndex*_iOffset;
        int startJ = jIndex + jIndex*_jOffset;
        for(int i = startI; i < value.length + startI; i++){
            for (int j = startJ; j <value[i-startI].length + startJ; j++){
                _matrix[i][j] += value[i-startI][j-startJ];
            }
        }
    }

    public int[][] GetMatrix(){
        return _matrix;
    }
    public void Display(){
        MatrixHelper.Display(_matrix);
    }
    public void GetResult(int[][] firstArray, int[][] secondArray){
        _matrix = Multiply(firstArray, secondArray);
    }
}
