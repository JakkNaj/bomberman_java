package fel.cvut.cz.states;


import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;

import java.awt.*;

/** Abstractly represents a State of the game - Settings, Menu, Game */
public abstract class State {

    //TODO SettingsState

    //GAME-STATE manager
    private static State currentState = null;
    public static void setState(State state){
        currentState = state;
    }
    public static State getState(){
        return currentState;
    }

    //CLASS
    protected Handler handler;
    public abstract void tick();
    public abstract void render(Graphics g);

    public State(Handler handler){
        this.handler = handler;
    }

}
