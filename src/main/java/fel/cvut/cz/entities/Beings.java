package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.tiles.Tile;
/** Abstract class representing all moving entities in game */
public abstract class Beings extends Entity{
    public static final float DEFAULT_SPEED = 2;
    public static final int DEFAULT_BEING_WIDTH = 32;
    public static final int DEFAULT_BEING_HEIGHT = 32;

    protected float Xmovement, Ymovement; //direction of movement
    float speed; //speed of movement
    int health = 1;

    public Beings(GameHandler gameHandler, float x, float y, int width, int height) {
        super(gameHandler, x, y, width, height);
        speed = DEFAULT_SPEED;
        speed = DEFAULT_SPEED;
        Xmovement = 0;
        Ymovement = 0;
    }

    public void move(){
        moveX();
        moveY();
    }

    public boolean moveX(){
        if (Xmovement > 0){ //moving RIGHT
            //x coordinate of tile we are trying to move into
            int tempX = (int) (x + Xmovement + bounds.x + bounds.width) / Tile.TILEWIDTH;
            //check upper RIGHT corner first
            if (canWalkOnTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                canWalkOnTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += Xmovement; // we can move to the RIGHT
                return true;
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                x = tempX * Tile.TILEWIDTH - bounds.x - bounds.width - 1; //pixel coordinates
                // - 1 so we can move up and down and collision detection on move up and down doesn't catch it
                return false;
            }
        } else if (Xmovement < 0){ //moving LEFT
            //x coordinate of tile we are trying to move into
            int tempX = (int) (x + Xmovement + bounds.x) / Tile.TILEWIDTH;
            //check upper LEFT corner first
            if (canWalkOnTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                canWalkOnTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += Xmovement; // we can move to the LEFT
                return true;
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                x = tempX * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x; //pixel coordinates
                return false;
            }
        }
        return true;
    }
    public boolean moveY(){
        if (Ymovement > 0){ //moving DOWN
            //y coordinate of tile we are trying to move into
            int tempY = (int) (y + Ymovement + bounds.y + bounds.height) / Tile.TILEWIDTH;
            //check lower RIGHT corner first
            if (canWalkOnTile((int) (x + bounds.x + bounds.width) / Tile.TILEHEIGHT, tempY) &&
                canWalkOnTile((int) (x + bounds.x) / Tile.TILEHEIGHT, tempY)){
                y += Ymovement; // we can move DOWN
                return true;
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                y = tempY * Tile.TILEHEIGHT - bounds.y - bounds.height - 1; //pixel coordinates
                // - 1 so we can move left and right and collision detection on move left and right doesn't catch it
                return false;
            }
        } else if (Ymovement < 0){ //moving UP
            //y coordinate of tile we are trying to move into
            int tempY = (int) (y + Ymovement + bounds.y) / Tile.TILEWIDTH;
            //check upper RIGHT corner first
            if (canWalkOnTile((int) (x + bounds.x + bounds.width) / Tile.TILEHEIGHT, tempY) &&
                canWalkOnTile((int) (x + bounds.x) / Tile.TILEHEIGHT, tempY)){
                y += Ymovement; // we can move UP
                return true;
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                y = tempY * Tile.TILEHEIGHT + bounds.y + bounds.height; //pixel coordinates
                return false;
            }
        }
        return true;
    }

    protected boolean canWalkOnTile(int x, int y){
        return gameHandler.getGameboard().getTile(x,y).isWalkable();
    }

    //Getters and Setters
    public float getXmovement() {
        return Xmovement;
    }

    public void setXmovement(float xmovement) {
        this.Xmovement = xmovement;
    }

    public float getYmovement() {
        return Ymovement;
    }

    public void setYmovement(float ymovement) {
        this.Ymovement = ymovement;
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
