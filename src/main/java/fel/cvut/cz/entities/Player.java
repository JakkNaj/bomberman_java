package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;

/** Class that represents a player in game*/
public class Player extends Beings{
    private int bombCount; //TODO
    private int bombStrength; //TODO

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bounds.x = 8;
        bounds.y = 4;
        bounds.width = 16;
        bounds.height = 24;
    }

    @Override
    public void tick() {
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

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, (int)(this.x - handler.getGameCamera().getxOffset()),
                (int)(this.y - handler.getGameCamera().getyOffset()), this.width, this.height,null);
        g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
    }
}
