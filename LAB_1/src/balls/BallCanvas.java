package balls;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel{
    private final ArrayList<Ball> balls = new ArrayList<>();
    private final ArrayList<Hole> holes = new ArrayList<>();
    private final Counter counter;

    public BallCanvas(Counter counter){
        this.counter = counter;
    }

    public void add(Ball b){
        this.balls.add(b);
    }
    public void add(Hole hole) {
        holes.add(hole);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        DisplayBalls(g2);

        DisplayHoles(g2);
    }
    private void DisplayBalls(Graphics2D g2){
        ArrayList<Ball> ballsToRemove = new ArrayList<>();
        for (Ball b : balls) {
            if (CheckTouch(b)) {
                ballsToRemove.add(b);
            }

            if (!b.isHit()) {
                b.draw(g2);
            }
        }
        for (Ball ball : ballsToRemove)  {
            balls.remove(ball);
        }
    }

    private Boolean CheckTouch(Ball b){
        for (Hole hole : holes) {
            if (hole.touch(b)) {
                b.hit();
                counter.increment();
                return true;
            }
        }
        return false;
    }
    private void DisplayHoles(Graphics2D g2){
        for (Hole hole: holes) {
            hole.draw(g2);
        }
    }
}
