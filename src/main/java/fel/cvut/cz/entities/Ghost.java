package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;

public class Ghost extends Beings{
    private boolean lookingForChangeOfDirection; //state
    private int bounceCounter = 0;

    private final Animation dieing;
    public Ghost(GameHandler gameHandler, float x, float y) {
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

    @Override
    public void tick() {
        checkCollisions();
        getMove();
    }

    private void checkCollisions(){
        if (checkCollisionWithExplosion(Xmovement, 0f) || checkCollisionWithExplosion(0f, Ymovement)){
            health--;
        }
    }

    private void getMove(){
        if (lookingForChangeOfDirection){ //ghost wants to change direction of movement
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

        float oldXm = Xmovement;
        float oldYm = Ymovement;
        if (!moveX()){
            if (Xmovement > 0)
                Xmovement = -speed;
            else
                Xmovement = speed;
            if (oldXm != Xmovement)
                bounceCounter++;
            if (bounceCounter == 5){
                lookingForChangeOfDirection = true;
            }

        }

        if (!moveY()){
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
