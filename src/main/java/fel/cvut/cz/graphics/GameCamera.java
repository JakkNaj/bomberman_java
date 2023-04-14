package fel.cvut.cz.graphics;

import fel.cvut.cz.Game;
import fel.cvut.cz.entities.Entity;

public class GameCamera {
    private Game game;
    private float xOffset, yOffset;

    public GameCamera(Game game, float xOffset, float yOffset){
        this.game = game;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - (float)(game.getWidth() / 2 + e.getWidth()/2);
        yOffset = e.getY() - (float)(game.getHeight() / 2 + e.getWidth()/2);
    }

    public void move(float xAm, float yAm){
        xOffset += xAm;
        yOffset += yAm;
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
