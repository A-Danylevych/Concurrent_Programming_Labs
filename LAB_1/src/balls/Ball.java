package balls;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {
    private final Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;
    private boolean hit;
    private Color color;

    public Ball(Component canvas, Color color){
        this.canvas = canvas;
        this.color = color;

        x = 25;
        y = 25;

    }

    public Ball(Component c) {
        this(c, Color.darkGray);

        if(Math.random()<0.5){
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        }else{
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public void draw (Graphics2D g2){
        g2.setColor(color);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }
    public void hit(){
        hit = true;
        color = Color.green;
    }

    public void move(){
        x+=dx;
        y+=dy;
        if(x<0){
            x = 0;
            dx = -dx;
        }
        if(x+XSIZE>=this.canvas.getWidth()){
            x = this.canvas.getWidth()-XSIZE;
            dx = -dx;
        }
        if(y<0){
            y=0;
            dy = -dy;
        }
        if(y+YSIZE>=this.canvas.getHeight()){
            y = this.canvas.getHeight()-YSIZE;
            dy = -dy;
        }
        this.canvas.repaint();
    }
    public int getCenterX(){
        return x + XSIZE/2;
    }
    public int getCenterY(){
        return y + YSIZE/2;
    }
    public boolean isHit(){
        return hit;
    }
}
