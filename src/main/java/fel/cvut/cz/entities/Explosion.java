package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;

import java.awt.*;
import java.awt.image.BufferedImage;

/** Class that represent one square of explosion in game */
public class Explosion extends Entity{
    private final Animation explodeAnimation;
    public int getLifeSpan() {
        return lifeSpan;
    }
    boolean wallbreaker = false;

    private int lifeSpan = 20;
    public Explosion(GameHandler gameHandler, BufferedImage[] explosion, float x, float y, int width, int height) {
        super(gameHandler, x, y, width, height);
        //Animations
        this.explodeAnimation = new Animation(100, explosion);
    }

    /** Explosion simulating falling wall */
    public Explosion(GameHandler gameHandler, BufferedImage[] explosion, float x, float y, int width, int height, boolean wallBreaker) {
        super(gameHandler, x, y, width, height);
        this.wallbreaker = wallBreaker;
        //Animations
        this.explodeAnimation = new Animation(100, explosion);
    }

    @Override
    public void tick() {
        lifeSpan--;
        if (wallbreaker){ //wallbreaker ticking 6 times
            if (lifeSpan % 10 == 0 && lifeSpan != 0){
                explodeAnimation.tick();
            }
        } else { //normal explosion
            if (lifeSpan  == 12) explodeAnimation.tick();
            if (lifeSpan == 8) explodeAnimation.tick();
            if (lifeSpan == 4) explodeAnimation.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(explodeAnimation.getCurrentFrame(), (int)(this.x - gameHandler.getGameCamera().getxOffset()),
                (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height,null);

    }
}
