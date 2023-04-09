package fel.cvut.cz;

import fel.cvut.cz.display.Display;
import fel.cvut.cz.graphics.ImageLoader;
import fel.cvut.cz.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/** Main class of the game - starts and runs everything */
public class Game implements Runnable{ //can run on other thread than the rest of the program
    private Display display;
    public int width, height; //game class has access to these parameters of our display
    public String title;
    private boolean running = false;
    private Thread thread;
    private BufferStrategy bs; //using buffers tell computer what to draw on screen
    private Graphics g;
    private BufferedImage testImage;
    private SpriteSheet spriteSheet;
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){ //initialize all graphics for the game
        this.display = new Display(title, width, height);
        testImage = ImageLoader.loadImage("/textures/BombermanGeneralSprites.png");
        spriteSheet = new SpriteSheet(testImage);
    }
    private void tick(){ //update positions etc. in game

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

        g.drawImage(spriteSheet.crop(4,0, 16, 16),5,5,null);

        //Done drawing
        bs.show();
        g.dispose();
    }
    public void run(){ //majority of game code will be there
        this.init();
        //GAMELOOP
        while (running){
            tick();
            render();
        }
        this.stop();
    }
    public synchronized void start(){
        if (running) return; //game already running
        running = true;
        this.thread = new Thread(this);
        this.thread.start(); //call run method
    }
    public synchronized void stop(){
        if (!running) return; //nothing to stop
        running = false;
        try{
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
