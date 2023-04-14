package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;

/** Class that represents a player in game*/
public class Player extends Beings{
    private int bombCount; //TODO
    private int bombStrength; //TODO

    public Player(Game game, float x, float y) {
        super(game, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
    }

    @Override
    public void tick() {
        getInput();
        move();
        game.getGameCamera().centerOnEntity(this);
    }

    private void getInput(){
        xmove = 0;
        ymove = 0;

        if(game.getKeyManager().up)
            ymove = -speed;
        if(game.getKeyManager().down)
            ymove = speed;
        if(game.getKeyManager().left)
            xmove = -speed;
        if(game.getKeyManager().right)
            xmove = speed;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, (int)(this.x - game.getGameCamera().getxOffset()), (int)(this.y - game.getGameCamera().getyOffset()), this.width, this.height,null);
    }
}
