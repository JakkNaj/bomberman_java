package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Ghost extends Beings{
    private int changeRandMoveCounter = 240;
    public Ghost(GameHandler gameHandler, float x, float y) {
        super(gameHandler, x * DEFAULT_BEING_WIDTH, y * DEFAULT_BEING_HEIGHT, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        this.setSpeed(0.5f);
        bounds.x = 4;
        bounds.y = 4;
        bounds.width = 24;
        bounds.height = 24;
        setXmovement(0+speed);
    }

    @Override
    public void tick() {
        //TODO REPAIR RANDOM WALKING
        /*changeRandMoveCounter--;
        if (changeRandMoveCounter == 0){
            changeRandMoveCounter = 240;
            setMove();
        }
        changeMoveIfCollision();*/
    }

    private void setMove(){
        int randXmove = ThreadLocalRandom.current().nextInt(0,3);
        switch (randXmove){
            case 0: setXmovement(0);
            case 1: setXmovement(0 - speed);
            case 2: setXmovement(0 + speed);
        }
        int randYmove = ThreadLocalRandom.current().nextInt(0,3);
        switch (randYmove){
            case 0: setYmovement(0);
            case 1: setYmovement(0 + speed);
            case 2: setYmovement(0 - speed);
        }
    }
    private void changeMoveIfCollision(){
        if (!moveX()){
            if (Xmovement < 0) {
                setXmovement(0+speed);
                System.out.println("ghost moving right");
            }
            else if (Xmovement > 0) {
                setXmovement(0-speed);
                System.out.println("ghost moving left");
            }
        }
        if (!moveY()){
            if (Ymovement < 0) {
                setXmovement(0+speed);
                System.out.println("ghost moving down");
            }
            else if (Ymovement > 0) {
                setXmovement(0-speed);
                System.out.println("ghost moving up");
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ghost, (int)(this.x - gameHandler.getGameCamera().getxOffset()), (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height, null);
        //test bounding box
        /*g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);*/
    }
}
