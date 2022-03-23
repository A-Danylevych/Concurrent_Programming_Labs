package matrixMultiplier;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static matrixMultiplier.MatrixHelper.Copy;


public class StripeThread extends Thread{
    private StripeThread _next;
    private int[][] _rows;
    private int[][] _columns;
    private int[] _rowsIndexes;
    private int[] _columnsIndexes;
    private final int _iterationCount;
    private final Result _result;
    private boolean _columnSet;
    private final Lock lock = new ReentrantLock();
    private final Condition Multiplied = lock.newCondition();
    private final Condition NewColumn = lock.newCondition();

    public StripeThread(int[][] rows, int[][]  columns, int[] rowsIndexes, int[] columnsIndexes,
                        Result result, int threadCount){
        _rows = rows;
        _columns = columns;
        _columnsIndexes = columnsIndexes;
        _rowsIndexes = rowsIndexes;
        _iterationCount = threadCount;
        _result = result;
        _columnSet = true;
    }
    public void SetNext(StripeThread multiThread){
        _next = multiThread;
    }

    public void SetColumns(int[][] columns, int[] indexes){
        lock.lock();
        WaitMultiplication();
        _columns = columns;
        _columnsIndexes = indexes;
        _columnSet = true;
        NewColumn.signal();
        lock.unlock();
    }

    public void AddRow(int[] row, int index){
        _rows = Copy(_rows, new int[_rows.length+1][_rows[0].length]);
        _rows[_rows.length-1] = row;
        _rowsIndexes = AddElement(_rowsIndexes, index);
    }
    public void AddColumn(int[] column, int index){
        _columns = Copy(_columns, new int[_columns.length+1][_columns[0].length]);
        _columns[_rows.length-1] = column;
        _columnsIndexes = AddElement(_columnsIndexes, index);
    }


    @Override
    public  void run() {
        if (_next == null){
            SingleRun();
        }
        else{
            MultiRun();
        }

    }

    private void MultiRun() {
        for (int j = 0; j<_iterationCount; j++){
            Multiply();
            if(j != _iterationCount-1){
                _next.SetColumns(_columns, _columnsIndexes);
                WaitNextColumn();
            }
        }
    }

    private void SingleRun() {
        Multiply();
    }

    private void Multiply(){
        lock.lock();
        int result = 0;
        for (int n =0; n < _columnsIndexes.length; n++){
            for (int i = 0; i< _rowsIndexes.length; i++){
                for (int j = 0; j< _columns[n].length; j++){
                    result += _rows[i][j] * _columns[n][j];
                }
                _result.SetValue(_rowsIndexes[i],_columnsIndexes[n], result);
                result = 0;
            }
        }
        _columnSet = false;
        Multiplied.signal();
        lock.unlock();
    }

    private void WaitNextColumn(){
        lock.lock();
        while (!_columnSet) {
            try {
                NewColumn.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }

    private void WaitMultiplication() {
        while (_columnSet) {
            try {
                Multiplied.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private int[] AddElement(int[] array, int element){
        int[] result = new int[array.length+1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[result.length-1] = element;
        return result;
    }
}
