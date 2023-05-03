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
            System.out.println("BOOM");
        } else if (lifeSpan%60 == 0){
            System.out.println("tick");
        }
    }

    @Override
    public void render(Graphics g) {
        if (lifeSpan > 0)
            g.drawImage(Assets.bomb, (int)(this.x - handler.getGameCamera().getxOffset()), (int)(this.y - handler.getGameCamera().getyOffset()), this.width, this.height, null);
        else {
            explode(g);
            System.out.println("explode animation");
        }
    }

    private void explode(Graphics g){
        // TODO check for collisions with entities and walls
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_center, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //left
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_left, x - Tile.TILEWIDTH, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_left_end, x - (2* Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //right
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_right, x + Tile.TILEWIDTH, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_right_end, x + (2 * Tile.TILEWIDTH) , y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //up
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_up, x, y - Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_up_end, x, y - (2 * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //down
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_down, x, y + Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        handler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(handler, Assets.explosion_down_end, x, y + (2 * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
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
