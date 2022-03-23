package balls;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame implements Counter{
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private final JLabel counter;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Bounce programme");
        this.canvas = new BallCanvas(this);

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        counter = CreateCounter();

        JPanel buttonPanel = CreateButtonPanel(counter);

        CreateHoles();

        content.add(buttonPanel, BorderLayout.SOUTH);
    }
    @Override
    public void increment(){
        int value = Integer.parseInt(counter.getText());
        value++;
        counter.setText(String.valueOf(value));
    }
    private JLabel CreateCounter(){
        JLabel counter = new JLabel();
        counter.setText("0");
        return counter;
    }
    private BallThread CreateBall(Color color, int priority){
        Ball b = new Ball(canvas, color);
        canvas.add(b);
        return new BallThread(b, priority);
    }
    private void CreateBalls(Color color, int count, int priority){
        for (int i =0; i<count; i++) {
            Ball b = new Ball(canvas, color);
            canvas.add(b);

            BallThread thread = new BallThread(b, priority);
            thread.start();
        }
    }
    private void CreateHoles(){
        canvas.add(new Hole(0,0));
        canvas.add(new Hole(WIDTH-40,0));
        canvas.add(new Hole(0, HEIGHT-100));
        canvas.add(new Hole(WIDTH-40,HEIGHT-100));
    }
    private JPanel CreateButtonPanel(JLabel counter) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);
        JButton buttonStop = new JButton("Stop");
        JButton buttonBlue = new JButton("Add Blue");
        JButton buttonRed = new JButton("Add Red");
        JButton buttonBoth = new JButton("Add Both");
        buttonStop.addActionListener(e -> System.exit(0));
        buttonBlue.addActionListener(e -> {
            BallThread thread = CreateBall(Color.blue, 2);

            thread.start();

            System.out.println("In Frame Thread name = " +
                    Thread.currentThread().getName());
            System.out.println("In Frame Thread name = " +
                    thread.getName());
        });
        buttonRed.addActionListener(e -> {

            BallThread thread = CreateBall(Color.RED, 7);


            BallThread secondThread = CreateBall(Color.RED, 7);

            secondThread.JoinThread(thread);

            thread.start();
            secondThread.start();
            System.out.println("In Frame Thread name = " +
                    Thread.currentThread().getName());
            System.out.println("In Frame Thread name = " +
                    thread.getName());

        });
        buttonBoth.addActionListener(e -> {

            CreateBalls(Color.blue, 500, 2);

            CreateBalls(Color.RED, 1, 10);
        });
        buttonPanel.add(counter);
        buttonPanel.add(buttonBoth);
        buttonPanel.add(buttonBlue);
        buttonPanel.add(buttonRed);
        buttonPanel.add(buttonStop);
        return buttonPanel;
    }
}
