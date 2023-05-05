package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Class that represents a player in game*/
public class Player extends Beings{
    private ArrayList<Bomb> bombs;

    private int bombCount = 1;

    private int bombStrength = 2;

    private final Animation animationDown, animationUp;
    private final Animation animationLeft, animationRight, animationStanding;

    public Player(GameHandler gameHandler, float x, float y) {
        super(gameHandler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bombs = new ArrayList<Bomb>();
        health = 3;
        //Bounding box
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
        //tick bombs
        for (Bomb b : bombs){
            b.tick();
        }
        gameHandler.getGameCamera().centerCameraOnEntity(this);
    }

    private void getInput(){
        Xmovement = 0;
        Ymovement = 0;

        if(gameHandler.getKeyManager().up)
            Ymovement = -speed;
        if(gameHandler.getKeyManager().down)
            Ymovement = speed;
        if(gameHandler.getKeyManager().left)
            Xmovement = -speed;
        if(gameHandler.getKeyManager().right)
            Xmovement = speed;
        if(KeyManager.bombAvailable > 0){
            placeBomb();
        }
    }

    public void render(Graphics g) {
        BufferedImage animFrame = getAnimationFrame();

        g.drawImage(getAnimationFrame(), (int)(this.x - gameHandler.getGameCamera().getxOffset()),
                (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height,null);

        //test bounding box
/*
        g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
*/
        //draw bombs
        for (int i = 0; i < bombs.size();){
            Bomb b = bombs.get(i);
            b.render(g);
            if (b.getLifeSpan() == 0){
                //bomb exploded
                bombs.remove(b);
            } else {
                i++;
            }
        }

    }

    private BufferedImage getAnimationFrame(){
        if (Ymovement < 0) {
            return animationUp.getCurrentFrame();
        } else if (Ymovement > 0){
            return animationDown.getCurrentFrame();
        } else if (Xmovement < 0){
            return animationLeft.getCurrentFrame();
        } else if (Xmovement > 0){
            return animationRight.getCurrentFrame();
        }
        return animationStanding.getCurrentFrame();
    }

    @Override
    public void move(){
        if (!checkCollisionWithGhost(Xmovement, 0f) &&
            !checkCollisionWithExplosion(Xmovement, 0f)){
            moveX();
        } else {
            System.out.println("PLAYER DIED, current life: " + (this.health -1) );
            health--;
            setX(0);
            setY(0);
        }
        if (!checkCollisionWithGhost(0f, Ymovement) &&
            !checkCollisionWithExplosion(0f, Ymovement)){
            moveY();
        } else {
            System.out.println("PLAYER DIED, current life: " + (this.health -1) );
            health--;
            setX(0);
            setY(0);
        }
    }

    private void placeBomb(){
        if (bombs.size() < bombCount && KeyManager.bombAvailable > 0){
            Bomb b = new Bomb(gameHandler,x,y,width,height);
            b.roundCoords();
            bombs.add(b);
        }
        KeyManager.bombAvailable--;
    }

    public int getBombStrength() {
        return bombStrength;
    }
}
