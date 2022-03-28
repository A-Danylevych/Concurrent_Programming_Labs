package matrixMultiplier;

import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Tester {
    private final int[][][] _testData;
    private final int _startSize;
    private final int _iterations;
    private final int _dataCount;
    private final int _processIteration;
    private final Result[] _stripeResults;
    private final Result[] _foxResults;
    private Result[] _singleResults;

    final int incrementor = 2;
    private int _startProcessCount = 4;
    private int _startAddition =5;

    public Tester(int startSize, int iterations, int dataCount, int processIteration){
        _startSize = startSize;
        _iterations = iterations;
        _dataCount = dataCount;
        _processIteration = processIteration;
        _testData = new int[_dataCount*_iterations][][];
        _foxResults = new Result[_dataCount*_iterations*_processIteration];
        _stripeResults = new Result[_dataCount*_iterations*_processIteration];
    }

    public void GenerateTestData(){
        int index = 0;
        for (int i = _startSize; i<=_iterations*_startSize; i+= _startSize ){
            for (int j = 0; j< _dataCount; j++){
                _testData[index] = DataGenerator.Generate(i, i);
                index++;
            }
        }
    }

    public void TestSingleProcess(){
        _singleResults = new Result[_iterations*_dataCount];

        int index = 0;
        for (int i =0; i < _iterations; i++){
            for (int j = 0; j< _dataCount; j ++) {
                long start = System.currentTimeMillis();
                _singleResults[index] = new Result(_testData[i+j].length, _testData[i+j][0].length);
                _singleResults[index].GetResult(_testData[i*_dataCount + j], _testData[i*_dataCount + j]);
                long end = System.currentTimeMillis();
                _singleResults[index].Elapsed = end - start;
                index++;
            }
        }
    }

    public void SetTestData(int[][] matrix){
        _testData[0] = matrix;
    }

    public void IncreaseProcessCount(int times){
        for (int i = 0; i < times; i++) {
            _startProcessCount += _startAddition;
            _startAddition += incrementor;
        }
    }

    public void Test(){
        int index = 0;
        int dataIndex = 0;
        for (int k = 0; k< _iterations; k++) {
            int addition = _startAddition;
            int processCount = _startProcessCount;
            for (int i = 0; i < _processIteration; i++) {
                for (int j = 0; j < _dataCount; j++) {
                    _foxResults[index] = Multiplier.FoxMultiply(_testData[dataIndex], _testData[dataIndex], processCount);
                    _foxResults[index].ProcessCount = processCount;
                    _stripeResults[index] = Multiplier.StripeMultiply(_testData[dataIndex], _testData[dataIndex], processCount);
                    _stripeResults[index].ProcessCount = processCount;
                    index++;
                    dataIndex++;
                }
                dataIndex -= _dataCount;
                processCount += addition;
                addition += incrementor;
            }
            dataIndex += _dataCount;
        }

    }
    public void DisplayStats(){
        AsciiTable asciiTable = new AsciiTable();
        List<String> list = Arrays.stream(GetHeader()).toList();
        List<String> helpList = new ArrayList<>();
        helpList.add("Size");
        helpList.add("Single");
        List<String> header = Stream.concat(helpList.stream(), list.stream()).toList();

        asciiTable.addRule();
        asciiTable.addRow(header);
        for(int i =0; i< _iterations; i++){
            asciiTable.addRule();
            asciiTable.addRow(Arrays.stream(GetRow(i)).toList());
        }
        asciiTable.addRule();
        String rend = asciiTable.render();
        System.out.println(rend);

    }

    private String[] GetHeader(){
        String[] algoInfo = new String[_processIteration*2];
        int index =0;
        for (int i = 0; i < algoInfo.length; i++){
            if (i % 2 == 0){
                algoInfo[i] = "Fox - " + _foxResults[index*_dataCount].ProcessCount;
            }
            else {
                algoInfo[i] = "Stripe - " + _stripeResults[index*_dataCount].ProcessCount;
                index++;
            }
        }
        return algoInfo;
    }
    private String[] GetRow(int iteration){
        String[] row = GetFirstColumns(iteration);
        int index = 2;
        for (int i = 0; i < _processIteration; i++){
            int foxValue = 0;
            int stripeValue = 0;
            int start = iteration * _dataCount * _processIteration + i *_dataCount;
            for (int j = start; j < start + _dataCount; j++){
                foxValue += _foxResults[j].Elapsed;
                stripeValue += _stripeResults[j].Elapsed;
            }
            foxValue /= _dataCount;
            stripeValue /= _dataCount;
            row[index] = String.valueOf(foxValue);
            index++;
            row[index] = String.valueOf(stripeValue);
            index++;
        }
        return row;
    }
    private String[] GetFirstColumns(int iteration){
        String[] row = new String[2 + _processIteration*2];
        int index =0;
        row[index] = String.valueOf(_foxResults[iteration*(_dataCount*_processIteration)].GetMatrix().length);
        index ++;
        if (_singleResults != null) {
            row[index] = String.valueOf(SingleMeanTime(iteration));
        }else {
            row[index] = "Not Measured";
        }
        return row;
    }
    private int SingleMeanTime(int iteration){
        int meanTime = 0;
        for (int i = 0; i<_dataCount; i++){
            meanTime += _singleResults[iteration*_dataCount + i].Elapsed;
        }
        meanTime /= _dataCount;
        return meanTime;
    }
    public void DisplayResults(){
        for (int i = 0; i<_iterations*_dataCount*_processIteration; i++){
            System.out.println("Fox - Size - " + _foxResults[i].GetMatrix().length + " ProcessCount - "
                    + _foxResults[i].ProcessCount + " Elapsed - " + _foxResults[i].Elapsed);
            _foxResults[i].Display();
            System.out.println("Stripe - Size - " + _stripeResults[i].GetMatrix().length + " ProcessCount - "
                    + _stripeResults[i].ProcessCount + " Elapsed - " + _stripeResults[i].Elapsed);
            _stripeResults[i].Display();
        }
        for (int i = 0; i<_iterations*_dataCount; i++){
            System.out.println("Single - Size - " + _singleResults[i].GetMatrix().length
                    + " Elapsed - " + _singleResults[i].Elapsed);
            _singleResults[i].Display();
        }
    }
}
