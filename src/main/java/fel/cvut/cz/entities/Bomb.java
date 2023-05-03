package fel.cvut.cz.entities;

import fel.cvut.cz.Handler;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;

public class Bomb extends Entity{
    private int lifeSpan = 180;
    public Bomb(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
    }

    @Override
    public void tick() {
        lifeSpan--;
        if (lifeSpan == 0) {
            explode();
            System.out.println("BOOM");
        } else if (lifeSpan%60 == 0){
            System.out.println("tick");
        }
    }

    @Override
    public void render(Graphics g) {
        if (lifeSpan > 0)
            g.drawImage(Assets.bomb, (int)(this.x - handler.getGameCamera().getxOffset()), (int)(this.y - handler.getGameCamera().getyOffset()), this.width, this.height, null);
        else
            //TODO expolosion animation
            System.out.println("explode animation");
    }

    private void explode(){
        //check for collisions with entities and walls
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public void roundCoords(){
        int roundX =(int) x % Tile.TILEWIDTH;
        if (roundX <= Tile.TILEWIDTH / 2){
            x -= roundX;
        } else {
            x += (Tile.TILEWIDTH - roundX);
        }
        int roundY =(int) y % Tile.TILEHEIGHT;
        if (roundY <= Tile.TILEHEIGHT / 2){
            y -= roundY;
        } else {
            y += (Tile.TILEHEIGHT - roundY);
        }

    }
}
