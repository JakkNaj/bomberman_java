package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;

/** Class that represents a player in game*/
public class Player extends Beings{
    private int bombCount;
    private int bombStrength;
    private Game game;

    public Player(Game game, int x, int y) {
        super(x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        this.game = game;
    }

    @Override
    public void tick() {
        getInput();
        move();
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
        g.drawImage(Assets.player, this.x, this.y, this.width, this.height,null);
    }
}
