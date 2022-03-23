package console;

public class Main {
    public static void main(String[] args){

        Thread firstThread = new Thread(new Printer('-', true, 50));
        firstThread.start();

        Thread secondThread = new Thread(new Printer('|', false, 50));
        secondThread.start();
    }
}
