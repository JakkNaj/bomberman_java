package fel.cvut.cz;

import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.input.KeyManager;

/** Class that allows me to pass along different variables to different places */
public class Handler {
    private Game game;
    private Gameboard gameboard;
    public Handler(Game game){
        this.game = game;
    }
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

//Getters and Setters
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
