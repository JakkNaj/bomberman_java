package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Class that represent one square of explosion in game */
public class Explosion extends Entity{
    //private final Animation animationLeft, animationRight, animationUp, animationDown;
    private final Animation explodeAnimation;
    public int getLifeSpan() {
        return lifeSpan;
    }

    private int lifeSpan = 50;
    public Explosion(GameHandler gameHandler, BufferedImage[] explosion, float x, float y, int width, int height) {
        super(gameHandler, x, y, width, height);
        //Animations
        this.explodeAnimation = new Animation(100, explosion);
    }

    @Override
    public void tick() {
        lifeSpan--;
        if (lifeSpan  == 30) explodeAnimation.tick();
        if (lifeSpan == 15) explodeAnimation.tick();
        if (lifeSpan == 5) explodeAnimation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(explodeAnimation.getCurrentFrame(), (int)(this.x - gameHandler.getGameCamera().getxOffset()),
                (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height,null);

    }
}
