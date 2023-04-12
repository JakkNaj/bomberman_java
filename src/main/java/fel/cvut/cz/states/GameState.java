package fel.cvut.cz.states;

import fel.cvut.cz.Game;
import fel.cvut.cz.entities.Player;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;

/** Where the actual gameplay is */
public class GameState extends State {

    private Player player;

    public GameState(Game game){
        super(game);
        player = new Player(game, 16, 16);
    }

    @Override
    public void tick() {
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);
        Tile.tiles[0].render(g, 0,0);
    }
}
