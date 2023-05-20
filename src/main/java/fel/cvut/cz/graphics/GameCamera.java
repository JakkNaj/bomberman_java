package fel.cvut.cz.graphics;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.entities.Entity;
import fel.cvut.cz.tiles.Tile;

/** Game Camera centered around player.
 *  It's offsets are used to tell game, which part of the map to render. */
public class GameCamera {
    private GameHandler gameHandler;
    private float xOffset, yOffset;

    public GameCamera(GameHandler gameHandler, float xOffset, float yOffset){
        this.gameHandler = gameHandler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /** Centering the game camera on entity */
    public void centerCameraOnEntity(Entity e){
        xOffset = e.getX() - (float)(gameHandler.getGameWidth() / 2 + e.getWidth()/2);
        yOffset = e.getY() - (float)(gameHandler.getGameHeight() / 2 + e.getWidth()/2);
        checkBlankSpace();
    }

    /** Method to move camera in given directions */
    public void move(float xAm, float yAm){
        xOffset += xAm;
        yOffset += yAm;
        checkBlankSpace();
    }

    private void checkBlankSpace(){
        if (xOffset < 0){
            xOffset = 0;
        } else if (xOffset > gameHandler.getGameboard().getWidth() * Tile.TILEWIDTH - gameHandler.getGameWidth()) {
            xOffset = gameHandler.getGameboard().getWidth() * Tile.TILEWIDTH - gameHandler.getGameWidth();
        }
        if (yOffset < 0){
            yOffset = 0;
        } else if (yOffset > gameHandler.getGameboard().getHeight() * Tile.TILEHEIGHT - gameHandler.getGameHeight()) {
            yOffset = gameHandler.getGameboard().getHeight() * Tile.TILEHEIGHT - gameHandler.getGameHeight();
        }
    }

    //Getters and Setters
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
