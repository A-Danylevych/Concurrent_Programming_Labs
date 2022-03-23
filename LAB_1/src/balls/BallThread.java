package balls;

public class BallThread extends Thread{
    private final Ball b;
    private BallThread threadToJoin;

    public BallThread(Ball ball, int priority){
        b = ball;
        setPriority(priority);

    }

    public void JoinThread(BallThread ballThread){
        threadToJoin = ballThread;
    }

    @Override
    public void run(){
        try{
            if (threadToJoin != null){
                threadToJoin.join();
            }
            for(int i=1; i<1000000; i++){
                b.move();
                System.out.println("Thread name = "
                        + Thread.currentThread().getName());
                System.out.println("Thread priority = "
                        + Thread.currentThread().getPriority());
                Thread.sleep(5);

                if (b.isHit()){
                    return;
                }

            }
        } catch(InterruptedException ignored){

        }
    }
}
