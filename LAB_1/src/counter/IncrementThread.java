package counter;

public class IncrementThread extends Thread{
    private final Counter counter;
    private final int count;

    public IncrementThread(Counter counter, int count){
        this.counter = counter;
        this.count = count;
    }

    @Override
    public void run(){
        for (int i = 0; i< count; i++){
            counter.incrementLock();
        }
    }
}
