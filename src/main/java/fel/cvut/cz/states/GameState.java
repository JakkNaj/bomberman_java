package fel.cvut.cz.states;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.board.Gameboard;

import java.awt.*;

/** Where the actual gameplay is */
public class GameState extends State {
    private Gameboard gameboard;

    public GameState(GameHandler gameHandler){
        super(gameHandler);
        gameboard = new Gameboard(gameHandler,"src/main/resources/worlds/world1.txt");
        gameHandler.setGameboard(gameboard);
    }

    @Override
    public void tick() {
        gameboard.tick();
    }

    @Override
    public void render(Graphics g) {
        gameboard.render(g);
    }
}
