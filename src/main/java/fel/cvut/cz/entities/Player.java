package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/** Class that represents a player in game*/
public class Player extends Beings{
    private int bombCount; //TODO
    private int bombStrength; //TODO

    private final Animation animationDown, animationUp;
    private final Animation animationLeft, animationRight, animationStanding;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bounds.x = 8;
        bounds.y = 4;
        bounds.width = 16;
        bounds.height = 24;

        //Animations
        animationDown = new Animation(100, Assets.player_walkDown);
        animationUp = new Animation(100, Assets.player_walkUp);
        animationLeft = new Animation( 100, Assets.player_walkLeft);
        animationRight = new Animation(100, Assets.player_walkRight);
        animationStanding = new Animation(100, Assets.player_standing);
    }

    @Override
    public void tick() {
        //Animation
        animationDown.tick();
        animationUp.tick();
        animationRight.tick();
        animationLeft.tick();
        animationStanding.tick();

        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput(){
        xmove = 0;
        ymove = 0;

        if(handler.getKeyManager().up)
            ymove = -speed;
        if(handler.getKeyManager().down)
            ymove = speed;
        if(handler.getKeyManager().left)
            xmove = -speed;
        if(handler.getKeyManager().right)
            xmove = speed;
    }

    public void render(Graphics g) {
        BufferedImage animFrame = getAnimationFrame();

        g.drawImage(getAnimationFrame(), (int)(this.x - handler.getGameCamera().getxOffset()),
                (int)(this.y - handler.getGameCamera().getyOffset()), this.width, this.height,null);

        //test bounding box
/*
        g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
*/

    }

    private BufferedImage getAnimationFrame(){
        if (ymove < 0) {
            return animationUp.getCurrentFrame();
        } else if (ymove > 0){
            return animationDown.getCurrentFrame();
        } else if (xmove < 0){
            return animationLeft.getCurrentFrame();
        } else if (xmove > 0){
            return animationRight.getCurrentFrame();
        }
        return animationStanding.getCurrentFrame();
    }
}
