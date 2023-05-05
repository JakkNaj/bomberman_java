package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
/** Abstract class of all Entities in game */
public abstract class Entity {
    protected GameHandler gameHandler;
    protected float x,y; //position
    protected int width, height;

    protected Rectangle bounds;

    public Entity(GameHandler gameHandler, float x, float y, int width, int height){
        this.gameHandler = gameHandler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(0,0, width, height); //full sprite bounding box
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    //helper entity collision functions
    public boolean checkCollisionsWithEntities(float xOff, float yOff){
        for(Entity e : gameHandler.getGameboard().getEntitiesManager().getEntityList()){
            if (e.equals(this)) continue;
            if (e.getCollisionBox(0f, 0f).intersects(getCollisionBox(xOff,yOff))){
                return true;
            }
        }
        return false;
    }
    public Rectangle getCollisionBox(float xOff, float yOff){
        return new Rectangle((int) (x + bounds.x + xOff), (int)(y + bounds.y + yOff), bounds.width, bounds.height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x * Tile.TILEWIDTH + Tile.TILEWIDTH;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y * Tile.TILEHEIGHT + Tile.TILEHEIGHT;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
