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
        super(x, y);
        this.game = game;
    }

    @Override
    public void tick() {
        if(game.getKeyManager().up)
            y -= 3;
        if(game.getKeyManager().down)
            y += 3;
        if(game.getKeyManager().left)
            x -= 3;
        if(game.getKeyManager().right)
            x += 3;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, this.x, this.y, null);
    }
}
