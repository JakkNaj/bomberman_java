package fel.cvut.cz.graphics;

import fel.cvut.cz.Game;
import fel.cvut.cz.Handler;
import fel.cvut.cz.entities.Entity;
import fel.cvut.cz.tiles.Tile;

public class GameCamera {
    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - (float)(handler.getGameWidth() / 2 + e.getWidth()/2);
        yOffset = e.getY() - (float)(handler.getGameHeight() / 2 + e.getWidth()/2);
        checkBlankSpace();
    }

    public void move(float xAm, float yAm){
        xOffset += xAm;
        yOffset += yAm;
        checkBlankSpace();
    }

    public void checkBlankSpace(){
        if (xOffset < 0){
            xOffset = 0;
        } else if (xOffset > handler.getGameboard().getWidth() * Tile.TILEWIDTH - handler.getGameWidth()) {
            xOffset = handler.getGameboard().getWidth() * Tile.TILEWIDTH - handler.getGameWidth();
        }
        if (yOffset < 0){
            yOffset = 0;
        } else if (yOffset > handler.getGameboard().getHeight() * Tile.TILEHEIGHT - handler.getGameHeight()) {
            yOffset = handler.getGameboard().getHeight() * Tile.TILEHEIGHT - handler.getGameHeight();
        }
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
