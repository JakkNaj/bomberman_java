package fel.cvut.cz.entities;

import fel.cvut.cz.Handler;
import fel.cvut.cz.tiles.Tile;

public abstract class Beings extends Entity{
    public static final int DEFAULT_SPEED = 3;
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
        moveX();
        moveY();
    }

    public void moveX(){
        if (xmove > 0){ //moving RIGHT
            //x coordinate of tile we are trying to move into
            int tempX = (int) (x + xmove + bounds.x + bounds.width) / Tile.TILEWIDTH;
            //check upper RIGHT corner first
            if (canWalkOnTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                canWalkOnTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += xmove; // we can move to the RIGHT
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                x = tempX * Tile.TILEWIDTH - bounds.x - bounds.width - 1; //pixel coordinates
                // - 1 so we can move up and down and collision detection on move up and down doesn't catch it
            }
        } else if (xmove < 0){ //moving LEFT
            //x coordinate of tile we are trying to move into
            int tempX = (int) (x + xmove + bounds.x) / Tile.TILEWIDTH;
            //check upper LEFT corner first
            if (canWalkOnTile(tempX, (int) (y + bounds.y) / Tile.TILEHEIGHT) &&
                canWalkOnTile(tempX, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
                x += xmove; // we can move to the LEFT
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                x = tempX * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x; //pixel coordinates
            }
        }
    }
    public void moveY(){
        if (ymove > 0){ //moving DOWN
            //y coordinate of tile we are trying to move into
            int tempY = (int) (y + ymove + bounds.y + bounds.height) / Tile.TILEWIDTH;
            //check lower RIGHT corner first
            if (canWalkOnTile((int) (x + bounds.x + bounds.width) / Tile.TILEHEIGHT, tempY) &&
                canWalkOnTile((int) (x + bounds.x) / Tile.TILEHEIGHT, tempY)){
                y += ymove; // we can move DOWN
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                y = tempY * Tile.TILEHEIGHT - bounds.y - bounds.height - 1; //pixel coordinates
                // - 1 so we can move left and right and collision detection on move left and right doesn't catch it
            }
        } else if (ymove < 0){ //moving UP
            //y coordinate of tile we are trying to move into
            int tempY = (int) (y + ymove + bounds.y) / Tile.TILEWIDTH;
            //check upper RIGHT corner first
            if (canWalkOnTile((int) (x + bounds.x + bounds.width) / Tile.TILEHEIGHT, tempY) &&
                canWalkOnTile((int) (x + bounds.x) / Tile.TILEHEIGHT, tempY)){
                y += ymove; // we can move UP
            } else { //cannot walk on that tile
                //reset x position on player -> bounding box right next to Tile we cannot step on
                y = tempY * Tile.TILEHEIGHT + bounds.y + bounds.height; //pixel coordinates
            }
        }
    }

    protected boolean canWalkOnTile(int x, int y){
        return handler.getGameboard().getTile(x,y).isWalkable();
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
