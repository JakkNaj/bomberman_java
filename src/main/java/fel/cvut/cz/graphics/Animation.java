package fel.cvut.cz.graphics;

import java.awt.image.BufferedImage;

/** Animation class used to create new animations that ticks */
public class Animation {

    private int speed, idx;
    private BufferedImage[] frames;
    private long lastTime, timer;

    public Animation(int speed, BufferedImage[] frames){
        this.speed = speed;
        this.frames = frames;
        this.idx = 0;
        this.lastTime = System.currentTimeMillis();
        this.timer = 0;
    }

    public BufferedImage getCurrentFrame(){
        return frames[idx];
    }

    //TIMING system used in Player Walking
    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if (timer > speed){
            idx++;
            timer = 0;
            if (idx >= frames.length){
                idx = 0;
            }
        }
    }
}
