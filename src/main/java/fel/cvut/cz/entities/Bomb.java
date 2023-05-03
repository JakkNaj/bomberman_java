package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
/** Class that represent bomb placed on gameboard by player */
public class Bomb extends Entity{
    private int lifeSpan = 180; //how many ticks the bomb lives
    public Bomb(GameHandler gameHandler, float x, float y, int width, int height) {
        super(gameHandler, x, y, width, height);
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
            g.drawImage(Assets.bomb, (int)(this.x - gameHandler.getGameCamera().getxOffset()), (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height, null);
        else {
            explode(g);
            System.out.println("explode animation");
        }
    }

    private void explode(Graphics g){
        // TODO check for collisions with entities and walls
        gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_center, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //left
        int bombStrength = gameHandler.getGameboard().getEntityManager().getPlayer().getBombStrength();
        if ( bombStrength > 1) {
            for (int i = 0; i < bombStrength - 1; i++){
                //left
                gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_left, x - ((i+1)  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                //right
                gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_right, x + ((i+1)  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                //up
                gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_up, x, y - ((i+1)  * Tile.TILEWIDTH), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                //down
                gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_down, x, y + ((i+1)  * Tile.TILEWIDTH), Tile.TILEWIDTH, Tile.TILEHEIGHT));
            }
        }
        //left-end
        gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_left_end, x - (bombStrength * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //right-end
        gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_right_end, x + (bombStrength * Tile.TILEWIDTH) , y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //up-end
        gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_up_end, x, y - (bombStrength * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //down-end
        gameHandler.getGameboard().getEntityManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_down_end, x, y + (bombStrength * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
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
