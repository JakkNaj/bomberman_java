package fel.cvut.cz.states;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;
import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.entities.Player;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
import java.io.File;

/** Where the actual gameplay is */
public class GameState extends State {

    private Player player;
    private Gameboard gameboard;

    public GameState(Handler handler){
        super(handler);
        gameboard = new Gameboard(handler,"src/main/resources/worlds/world1.txt");
        handler.setGameboard(gameboard);
        player = new Player(handler, 32, 32);
    }

    @Override
    public void tick() {
        gameboard.tick();
        player.tick();
    }

    @Override
    public void render(Graphics g) {
        gameboard.render(g);
        player.render(g);
    }
}
