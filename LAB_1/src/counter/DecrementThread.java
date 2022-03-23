package counter;

public class DecrementThread extends Thread{
    private final Counter counter;
    private final int count;

    public DecrementThread(Counter counter, int count){
        this.counter = counter;
        this.count = count;
    }

    @Override
    public void run(){
        for (int i = 0; i< count; i++){
            counter.decrementLock();
        }
    }
}