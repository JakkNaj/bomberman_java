package fel.cvut.cz;

import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.display.Display;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.input.KeyManager;

import java.awt.*;
import java.awt.image.BufferStrategy;

/** Main class of the game - starts and runs everything */
public class Game implements Runnable{ //can run on other thread than the rest of the program
    private Display display;
    private int width, height; //game class has access to these parameters of our display
    public String title;
    private boolean running = false;
    private Thread thread;
    private BufferStrategy bs; //using buffers tell computer what to draw on screen
    private Graphics g;

    private String pathToLevelFile;

    //BOARD
    private Gameboard gameboard;

    //INPUT
    private KeyManager keyManager;

    //CAMERA
    private GameCamera gameCamera;

    //HANDLER
    private GameHandler gameHandler;

    public Game(String title, int width, int height, String path){
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
        keyManager.tick();
        gameboard.tick();
    }
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
    public void run(){ //majority of game code will be there
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
        this.stop();
    }
    public synchronized void start(){ //starts thread
        if (running) return; //game already running
        running = true;
        this.thread = new Thread(this);
        this.thread.start(); //calls run method
    }
    public synchronized void stop(){ //stops thread
        if (!running) return; //nothing to stop
        running = false;
        try{
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
}
