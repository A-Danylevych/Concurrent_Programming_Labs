package counter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Counter {
    private int value;
    private final Lock writeLock = new ReentrantLock();

    public void increment(){
        this.value++;
    }

    public void decrement(){
        this.value--;
    }

    public synchronized void incrementSync(){
        this.value++;
    }

    public synchronized void decrementSync(){
        this.value--;
    }

    public void incrementBlock(){
        synchronized (this){
            this.value++;
        }
    }

    public void decrementBlock(){
        synchronized (this){
            this.value--;
        }
    }


    public void decrementLock(){
        writeLock.lock();
        this.value--;
        writeLock.unlock();
    }
    public void incrementLock(){
        writeLock.lock();
        this.value++;
        writeLock.unlock();
    }

    public void display(){
        System.out.println(value);
    }
}
