package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
/** Abstract class of all Entities in game */
public abstract class Entity {
    protected GameHandler gameHandler;
    protected float x,y; //position
    protected int width, height;

    public Rectangle getBounds() {
        return bounds;
    }

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
    public boolean checkCollisionWithGhost(float xOff, float yOff){
        for(Entity e : gameHandler.getGameboard().getEntitiesManager().getGhostList()){
            if (e.equals(this)) continue;
            if (e.getCollisionBox(0f, 0f).intersects(getCollisionBox(xOff,yOff))){
                Game.LOGGER.info("Player collided with ghost");
                return true;
            }
        }
        return false;
    }
    public boolean checkCollisionWithExplosion(float xOff, float yOff){
        for(Entity e : gameHandler.getGameboard().getEntitiesManager().getExplosionList()){
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

    public int getXrounded(){
        return Math.round(x / Tile.TILEWIDTH) - 1;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public int getYrounded(){
        return Math.round(y / Tile.TILEWIDTH) - 1;
    }

    public void setY(float y) {
        this.y = y;
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
