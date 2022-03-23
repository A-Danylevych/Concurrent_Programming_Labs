package matrixMultiplier;

public final class MatrixHelper {

    static void Display(int[][] matrix){
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    static int[][] Multiply(int[][] firstArray, int[][] secondArray){
        int[][] result = new int[firstArray.length][secondArray[0].length];
        for(int i = 0; i < firstArray.length; i++) {
            for (int j = 0; j < secondArray[0].length; j++) {
                for (int k = 0; k < firstArray[0].length; k++) {
                    result[i][j] += firstArray[i][k] * secondArray[k][j];
                }
            }
        }
        return result;
    }

    static int[][] Copy(int[][] source, int[][] destination){
        for (int i = 0; i < source.length; i++){
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
        return destination;
    }

    static int[] GetRow(int[][] array, int rowIndex){
        return array[rowIndex];
    }

    static int[][] GetRows(int[][] array, int beg, int end) {
        int[][] rows = new int[end-beg + 1][];
        for (int i = 0; i <= end-beg; i++){
            rows[i] = GetRow(array, beg + i);
        }
        return rows;
    }

    static int[] GetColumn(int[][] array, int columnIndex){
        int[] column = new int[array.length];
        for (int i = 0; i<array.length; i++){
            column[i] = array[i][columnIndex];
        }
        return column;
    }

    static int[][] GetColumns(int[][] array, int beg, int end) {
        int[][] columns = new int[end - beg + 1][];
        for (int i = 0; i <= end - beg; i++) {
            columns[i] = GetColumn(array, beg + i);
        }
        return columns;
    }

    static int[] GetIndexes(int beg, int end){
        int[] indexes = new int[end-beg+1];
        int current = beg;
        for (int i = 0; i<=end-beg; i++){
            indexes[i] = current;
            current++;
        }
        return indexes;
    }
}
