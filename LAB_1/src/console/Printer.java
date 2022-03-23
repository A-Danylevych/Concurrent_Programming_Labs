package console;

public class Printer implements Runnable {
    private final char character;
    private final boolean first;
    private final int count;
    private static final Sync sync = new Sync();

    public Printer(char character, boolean first, int count) {
        this.character = character;
        this.first = first;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++){
            sync.print(character, first);
        }
    }
}
