package fel.cvut.cz;

import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.display.Display;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.gui.InGameMenu;
import fel.cvut.cz.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Main class of the game - starts and runs everything. */
public class Game extends Thread{ //can run on other thread than the rest of the program
    private Display display;
    private int width, height; //game class has access to these parameters of our display
    public String title;
    public static boolean running = false;

    public static String saveGameFile = "";

    public static boolean enableLogger = false;
    private BufferStrategy bs; //using buffers tell computer what to draw on screen

    private Graphics g;

    private String pathToLevelFile;

    public static Logger LOGGER = null;

    //BOARD
    private Gameboard gameboard;

    //INPUT
    private KeyManager keyManager;

    //CAMERA
    private GameCamera gameCamera;

    //HANDLER
    private GameHandler gameHandler;

    public Game(String title, int width, int height, String path){
        LOGGER = Logger.getLogger(Game.class.getName());
        this.width = width;
        this.height = height;
        this.title = title;
        this.pathToLevelFile = path;
        keyManager = new KeyManager();
    }

    private void initializeGraphics(){ //initialize all graphics for the game
        this.display = new Display(title, width, height);
        display.getFrame().addKeyListener(this.keyManager);
        Assets.init();

        gameHandler = new GameHandler(this);
        gameCamera = new GameCamera(gameHandler,0,0);

        gameboard = new Gameboard(gameHandler,pathToLevelFile);
        gameHandler.setGameboard(gameboard);
    }
    private void tick(){ //update positions etc. in game
        if (saveGameFile.isEmpty()){
            keyManager.tick();
            gameboard.tick();
        } else {
            //save to file and end the game
            gameboard.saveWorld(saveGameFile);
            running = false;
        }

    }
    /** Method that is rendering game on screen. */
    private void render(){ //render updated things in game
        bs = display.getCanvas().getBufferStrategy(); //BufferStrategy = current BS of our Game
        if (bs == null){ //canvas doesn't have bs - first time running the game
            display.getCanvas().createBufferStrategy(3); //initialize BS
            return;
        }
        g = bs.getDrawGraphics();

        //clear the screen
        g.clearRect(0,0, width, height);

        //Draw to the screen
        gameboard.render(g);

        //Done drawing
        bs.show();
        g.dispose();
    }
    /** Method invoked after start of a Game.
     *  Contains game loop. */
    public void run(){
        if (!enableLogger) LOGGER.setLevel(Level.OFF);
        else
            LOGGER.setLevel(Level.CONFIG);
        LOGGER.info("The Game has started");
        running = true;
        this.initializeGraphics();
        int fps = 60;
        double timePerTick = 1000000000 / (double)fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime(); //returns time of our computer in nanoseconds
        long timer = 0;

        //GAME-LOOP
        while (running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if (delta >= 1){
                tick();
                render();
                delta--;
            }

            if (timer >= 1000000000){
                timer = 0;
            }
        }
        LOGGER.info("The Game stopped.");
        display.getFrame().dispose();
    }

    //Getters and Setters
    public KeyManager getKeyManager(){
        return keyManager;
    }
    public GameCamera getGameCamera(){
        return gameCamera;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Display getDisplay() {
        return display;
    }
    public Graphics getG() {
        return g;
    }
}
