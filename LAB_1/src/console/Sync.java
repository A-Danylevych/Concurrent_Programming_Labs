package console;

public class Sync {
    private boolean firstTime = true;

    public synchronized void print(char character, boolean first){

        WaitTime(first);

        System.out.print(character);

        ChangeTime();

    }

    private void WaitTime(boolean first){
        while (firstTime != first){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChangeTime(){
        firstTime = !firstTime;
        notifyAll();
    }
}
