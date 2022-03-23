package matrixMultiplier;

import static matrixMultiplier.MatrixHelper.Copy;

public class Block {
    public int i;
    public int j;
    public int[][] matrix;

    public Block(int i, int j, int[][] matrix){
        this.matrix = matrix;
        this.i = i;
        this.j = j;
    }

    public static Block[] GetBlocks(int[][] array,int blockCount){
        final int iStep = array.length/blockCount;
        final int jStep = array[0].length/blockCount;
        Block[] blocks = new Block[blockCount*blockCount];

        int i = 0;
        int j = 0;
        final int max = blockCount-1;
        int index = 0;
        boolean rowOverflow = false;
        for (int iIndex = 0; iIndex< array.length; iIndex++){

            boolean columnOverflow =false;

            for (int jIndex = 0; jIndex <array[iIndex].length; jIndex++){
                if (index >= blocks.length){
                    index -= blockCount;
                    rowOverflow = true;
                }
                if (blocks[index] == null){
                    blocks[index] = new Block(i, j, new int[iStep][jStep]);
                }

                if (rowOverflow && columnOverflow){
                    int jLength = array[iIndex].length - jStep *(blockCount -1);
                    int iLength = array.length - iStep *(blockCount -1);
                    int differenceI = iLength - iStep;
                    int remainI = array.length - iIndex;
                    int differenceJ = jLength - jStep;
                    int remainJ = array[iIndex].length - jIndex;
                    if (blocks[index].matrix.length != iLength || blocks[index].matrix[0].length != jLength){
                        blocks[index].matrix = Copy(blocks[index].matrix, new int[iLength][jLength]);
                    }

                    blocks[index].matrix[iStep + (differenceI - remainI)][(jStep + (differenceJ - remainJ) )] =
                            array[iIndex][jIndex];
                }
                else if (columnOverflow){
                    int jLength = array[iIndex].length - jStep *(blockCount -1);
                    int difference = jLength - jStep;
                    int remain = array[iIndex].length - jIndex;
                    if (blocks[index].matrix[0].length != jLength){
                        blocks[index].matrix = Copy(blocks[index].matrix, new int[iStep][jLength]);
                    }
                    blocks[index].matrix[iIndex%iStep][(jStep + (difference - remain) )] = array[iIndex][jIndex];
                }
                else if (rowOverflow){
                    int iLength = array.length - iStep *(blockCount -1);
                    int difference = iLength - iStep;
                    int remain = array.length - iIndex;
                    if (blocks[index].matrix.length != iLength){
                        int jLength = blocks[index].matrix[0].length;
                        blocks[index].matrix = Copy(blocks[index].matrix, new int[iLength][jLength]);
                    }
                    blocks[index].matrix[iStep + (difference - remain)][ jIndex%jStep]  = array[iIndex][jIndex];
                }
                else
                {
                    blocks[index].matrix[iIndex%iStep][jIndex%jStep] = array[iIndex][jIndex];
                }

                if((jIndex+1) % jStep == 0 && !columnOverflow){
                    j++;
                    index++;
                    if (array[iIndex].length - jIndex != 1 && j > max){
                        index--;
                        j--;
                        columnOverflow = true;
                    }
                }
                if (columnOverflow && array[iIndex].length - jIndex == 1){
                    index++;
                }
            }
            j = 0;
            if ((iIndex+1) % iStep == 0){
                i++;
            }
            else {
                index -= blockCount;
            }
        }
        return blocks;
    }
}