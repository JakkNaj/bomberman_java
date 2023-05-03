package fel.cvut.cz.entities;

import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class ExplodedBomb extends Entity{
    //private final Animation animationLeft, animationRight, animationUp, animationDown;
    private final Animation animation;
    public int getLifeSpan() {
        return lifeSpan;
    }

    private int lifeSpan = 60;
    public ExplodedBomb(Handler handler,BufferedImage[] explosion, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        //Animations
        this.animation = new Animation(100, explosion);
    }

    @Override
    public void tick() {
        lifeSpan--;
        if (lifeSpan  == 50) animation.tick();
        if (lifeSpan == 30) animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), (int)(this.x - handler.getGameCamera().getxOffset()),
                (int)(this.y - handler.getGameCamera().getyOffset()), this.width, this.height,null);
    }
}
