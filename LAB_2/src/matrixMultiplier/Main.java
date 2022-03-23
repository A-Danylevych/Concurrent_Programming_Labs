package matrixMultiplier;

public class Main {

    public static void main(String[] args) {
        Tester tester = new Tester(100, 10, 1, 5);
        tester.GenerateTestData();
        tester.Test();
        tester.TestSingleProcess();
        tester.DisplayStats();
        //tester.DisplayResults();
    }
}
