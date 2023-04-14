package fel.cvut.cz;

import fel.cvut.cz.display.Display;

/** Class to start up my game */
public class Launcher {
    public static void main(String[] args) {
        Game game = new Game("Bomberman 2D!", 600, 400);
        game.start();
    }
}