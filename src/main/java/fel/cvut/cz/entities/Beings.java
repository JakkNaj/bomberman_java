package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;

public abstract class Beings extends Entity{
    public static final int DEFAULT_SPEED = 20;
    public static final int DEFAULT_BEING_WIDTH = 32;
    public static final int DEFAULT_BEING_HEIGHT = 32;

    protected int xmove, ymove;
    int speed; //speed of movement

    public Beings(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        speed = DEFAULT_SPEED;
        xmove = 0;
        ymove = 0;
    }

    public void move(){
        x += xmove;
        y += ymove;
    }

    //Getters and Setters
    public int getXmove() {
        return xmove;
    }

    public void setXmove(int xmove) {
        this.xmove = xmove;
    }

    public int getYmove() {
        return ymove;
    }

    public void setYmove(int ymove) {
        this.ymove = ymove;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
