package balls;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Hole {
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private final int x;
    private final int y;


    public Hole(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void draw (Graphics2D g2){
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }
    private int getCenterX() {
        return x + XSIZE/2;
    }
    private int getCenterY() {
        return y + YSIZE/2;
    }
    public boolean touch(Ball ball){
        return Math.abs(getCenterX() - ball.getCenterX()) < XSIZE / 2
                && Math.abs(getCenterY() - ball.getCenterY()) < YSIZE / 2;
    }
}
