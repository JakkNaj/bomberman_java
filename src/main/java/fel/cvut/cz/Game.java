package fel.cvut.cz;

import fel.cvut.cz.display.Display;

/** Main class of the game - starts and runs everything */
public class Game {
    private Display display;
    public int width, height; //game class has access to these parameters of our display
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.display = new Display(title, width, height);
    }
}
