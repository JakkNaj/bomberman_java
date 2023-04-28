package fel.cvut.cz.entities;

import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Assets;

import java.awt.*;

public class Ghost extends Beings{

    public Ghost(Handler handler, float x, float y) {
        super(handler, x * DEFAULT_BEING_WIDTH, y * DEFAULT_BEING_HEIGHT, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bounds.x = 4;
        bounds.y = 4;
        bounds.width = 24;
        bounds.height = 24;
    }

    @Override
    public void tick() {
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ghost, (int)(this.x), (int)(this.y), this.width, this.height, null);
        //test bounding box
        g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
    }
}
