package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
/** Class representing enemies in game.
 *  They walk in given pattern and if player collides with them, then dies. */
public class Ghost extends Beings{
    private boolean lookingForChangeOfDirection; //state
    private int bounceCounter = 0;

    private final Animation dieing;
    public Ghost(GameHandler gameHandler, int x, int y) {
        super(gameHandler, x * DEFAULT_BEING_WIDTH, y * DEFAULT_BEING_HEIGHT, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        this.setSpeed(1f);
        bounds.x = 1;
        bounds.y = 1;
        bounds.width = 30;
        bounds.height = 30;
        lookingForChangeOfDirection = false;
        Xmovement = speed;
        Ymovement = 0;

        //Animations
        dieing = new Animation(100, Assets.ghostDieing);
    }

    /** Creating new Ghost with given direction of walking */
    public Ghost(GameHandler gameHandler, float x, float y, float xmove, float ymove) {
        super(gameHandler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        this.setSpeed(1f);
        bounds.x = 1;
        bounds.y = 1;
        bounds.width = 30;
        bounds.height = 30;
        lookingForChangeOfDirection = false;
        Xmovement = xmove;
        Ymovement = ymove;

        //Animations
        dieing = new Animation(100, Assets.ghostDieing);
    }

    @Override
    public void tick() {
        checkCollisions();
        getMove();
    }

    private void checkCollisions(){
        if (checkCollisionWithExplosion(Xmovement, 0f) || checkCollisionWithExplosion(0f, Ymovement)){
            Game.LOGGER.info("Ghost exploded!");
            health--;
        }
    }
    private void lookForWalkableTilesInDifferentDirection(){
        if (x % 32 == 0 && y % 32 == 0){
            //ghost is standing in the middle of tile
            if (Xmovement != 0){ //ghost is walking horizontally
                //first look if he can go up
                Tile tile = gameHandler.getGameboard().getTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT - 1);
                if (tile.getId() == 0){
                    Xmovement = 0;
                    Ymovement = -speed;
                    bounceCounter = 0;
                    lookingForChangeOfDirection = false;
                } else {
                    //look if he can go down
                    tile = gameHandler.getGameboard().getTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT + 1);
                    if (tile.getId() == 0){
                        Xmovement = 0;
                        Ymovement = speed;
                        bounceCounter = 0;
                        lookingForChangeOfDirection = false;
                    }
                }
            } else if (Ymovement != 0){ //ghost is walking vertically
                //first look if he can go left
                Tile tile = gameHandler.getGameboard().getTile((int) x / Tile.TILEWIDTH - 1, (int) y / Tile.TILEHEIGHT);
                if (tile.getId() == 0){
                    Xmovement = -speed;
                    Ymovement = 0;
                    bounceCounter = 0;
                    lookingForChangeOfDirection = false;
                } else {
                    //look if he can go right
                    tile = gameHandler.getGameboard().getTile((int) x / Tile.TILEWIDTH + 1, (int) y / Tile.TILEHEIGHT);
                    if (tile.getId() == 0){
                        Xmovement = speed;
                        Ymovement = 0;
                        bounceCounter = 0;
                        lookingForChangeOfDirection = false;
                    }
                }
            }
        }
    }
    private void moveHorizontally(){
        float oldXm = Xmovement;
        if (!checkCollisionWithBomb(Xmovement,Ymovement)){
            //bomb not there, try to move there
            if (!moveX()){
                //collision with detected
                if (Xmovement > 0)
                    //bounce
                    Xmovement = -speed;
                else
                    Xmovement = speed;
                if (oldXm != Xmovement)
                    bounceCounter++;
                if (bounceCounter == 5){
                    lookingForChangeOfDirection = true;
                }
            }
        } else {
            //collide with bomb -> bounce
            Xmovement = -Xmovement;
        }
    }
    private void moveVertically(){
        float oldYm = Ymovement;
        if (!checkCollisionWithBomb(Xmovement,Ymovement)){
            //bomb not there, try to move there
            if (!moveY()){
                //collision with tile detected
                if (Ymovement > 0)
                    Ymovement = -speed;
                else
                    Ymovement = speed;
                if (oldYm != Ymovement)
                    bounceCounter++;
                if (bounceCounter == 5){
                    lookingForChangeOfDirection = true;
                }
            }
        } else {
            //collide with bomb -> bounce
            Ymovement = -Ymovement;
        }
    }
    private void getMove(){
        if (lookingForChangeOfDirection){ //ghost wants to change direction of movement
            lookForWalkableTilesInDifferentDirection();
        }
        moveHorizontally();
        moveVertically();
    }

    private boolean checkCollisionWithBomb(float xOff, float yOff){
        for ( Bomb b : gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombs() ){
            if (b.getCollisionBox(0f,0f).intersects(getCollisionBox(xOff, yOff))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ghost, (int)(this.x - gameHandler.getGameCamera().getxOffset()), (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height, null);
        //test bounding box
        /*g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - gameHandler.getGameCamera().getxOffset()), (int)(y + bounds.y - gameHandler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);*/
    }
}
