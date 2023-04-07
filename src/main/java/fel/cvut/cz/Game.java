package fel.cvut.cz;

import fel.cvut.cz.display.Display;

/** Main class of the game - starts and runs everything */
public class Game implements Runnable{ //can run on other thread than the rest of the program
    private Display display;
    public int width, height; //game class has access to these parameters of our display
    public String title;
    private boolean running = false;
    private Thread thread;
    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){ //initialize all graphics for the game
        this.display = new Display(title, width, height);
    }
    private void tick(){ //update positions etc. in game

    }
    private void render(){ //render updated things in game

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
