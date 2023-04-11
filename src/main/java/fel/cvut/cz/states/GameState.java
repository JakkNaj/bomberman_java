package fel.cvut.cz.states;

import fel.cvut.cz.graphics.Assets;

import java.awt.*;

/** Where the actual gameplay is */
public class GameState extends State {

    public GameState(){

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ghost, 0, 0, null);
    }
}
