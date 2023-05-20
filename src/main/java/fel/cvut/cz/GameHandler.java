package fel.cvut.cz;

import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.input.KeyManager;

/** Class that allows me to pass along different variables to different places.
 *  This class handles all the game data. */
public class GameHandler {
    private Game game;
    private Gameboard gameboard;
    public GameHandler(Game game){
        this.game = game;
    }

    //Getters and Setters
    public KeyManager getKeyManager(){
    return game.getKeyManager();
}
    public GameCamera getGameCamera(){
        return game.getGameCamera();
    }
    public int getGameWidth(){
        return game.getWidth();
    }
    public int getGameHeight(){
        return game.getHeight();
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public Gameboard getGameboard() {
        return gameboard;
    }
    public void setGameboard(Gameboard gameboard) {
        this.gameboard = gameboard;
    }
}
