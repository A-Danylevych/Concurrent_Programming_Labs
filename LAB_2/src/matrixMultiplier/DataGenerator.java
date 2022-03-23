package matrixMultiplier;

public final class DataGenerator {

    public static int[][] Generate(int iSize, int jSize){
        int[][] values = new  int[iSize][jSize];
        for(int i = 0; i< iSize; i++){
            for (int j = 0; j< jSize; j++){
                values[i][j] = ((int) (Math.random() * 10));
            }
        }
        return values;
    }
}
