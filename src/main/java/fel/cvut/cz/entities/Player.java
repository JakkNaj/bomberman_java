package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.input.KeyManager;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.ArrayList;

/** Class that represents a player in game*/
public class Player extends Beings{
    private ArrayList<Bomb> bombs;
    private int bombCount = 1;
    private int bombStrength; //TODO

    private int health = 3;

    private final Animation animationDown, animationUp;
    private final Animation animationLeft, animationRight, animationStanding;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bombs = new ArrayList<Bomb>();

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
        if(KeyManager.bombAvailable > 0){
            placeBomb();
        }
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

    @Override
    public void move(){
        if (!checkCollisionsWithEntities(xmove, 0f)){
            moveX();
        } else {
            System.out.println("PLAYER DIED, current life: " + this.health);
            health--;
            setX(0);
            setY(0);
        }
        if (!checkCollisionsWithEntities(0f, ymove)){
            moveY();
        } else {
            System.out.println("PLAYER DIED, current life: " + this.health);
            health--;
            setX(0);
            setY(0);
        }
    }

    private void placeBomb(){
        if (bombs.size() < bombCount && KeyManager.bombAvailable > 0){
            Bomb b = new Bomb(handler,x,y,width,height);
            System.out.println("placed coords: x-" + x+" y-" + y);
            b.roundCoords();
            System.out.println("rounded coords: x-" + b.x+" y-" + b.y);
            bombs.add(b);
        }
        KeyManager.bombAvailable--;
    }
}
