package fel.cvut.cz.entities;

import java.awt.*;

public abstract class Entity {
    protected int x,y; //position

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
}
