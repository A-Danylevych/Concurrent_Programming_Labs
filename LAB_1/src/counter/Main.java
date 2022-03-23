package counter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        int count = 100000;

        IncrementThread incrementThread = new IncrementThread(counter, count);
        DecrementThread decrementThread = new DecrementThread(counter, count);

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        counter.display();
    }
}
