package fel.cvut.cz.states;


import fel.cvut.cz.GameHandler;

import java.awt.*;

/** Abstractly represents a State of the game - Settings, Menu, Game */
public abstract class State {

    //GAME-STATE manager
    private static State currentState = null;
    public static void setState(State state){
        currentState = state;
    }
    public static State getState(){
        return currentState;
    }

    //CLASS
    protected GameHandler gameHandler;
    public abstract void tick();
    public abstract void render(Graphics g);

    public State(GameHandler gameHandler){
        this.gameHandler = gameHandler;
    }

}
