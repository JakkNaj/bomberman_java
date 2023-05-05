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
        gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_center, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        int bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        //LEFT - EXPLOSION
        //look as far as the bomb should explode
        // -> stop at first wall
        // -> if wall id == 2 -> destroy
        // -> if wall id == 1 -> take step back
        int step = 0;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH - step, (int)y / Tile.TILEHEIGHT).getId();
            if (tileID == 0){
                bombStrength--;
            } else if (tileID == 1){
                step--;
                break;
            } else if (tileID == 2){
                //destroy wall - set it to grassTile
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH - step, (int) y / Tile.TILEHEIGHT, 0);
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_left_end, x - (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_left, x - (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //RIGHT - EXPLOSION
        step = 0;
        bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH + step, (int)y / Tile.TILEHEIGHT).getId();
            if (tileID == 0){
                bombStrength--;
            } else if (tileID == 1){
                step--;
                break;
            } else if (tileID == 2){
                //destroy wall - set it to grassTile
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH + step, (int) y / Tile.TILEHEIGHT, 0);
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_right_end, x + (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_right, x + (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //UP - EXPLOSION
        step = 0;
        bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH, (int)y / Tile.TILEHEIGHT - step).getId();
            if (tileID == 0){
                bombStrength--;
            } else if (tileID == 1){
                step--;
                break;
            } else if (tileID == 2){
                //destroy wall - set it to grassTile
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT - step, 0);
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_up_end, x , y - (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_up, x , y - (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //DOWN - EXPLOSION
        step = 0;
        bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH, (int)y / Tile.TILEHEIGHT + step).getId();
            if (tileID == 0){
                bombStrength--;
            } else if (tileID == 1){
                step--;
                break;
            } else if (tileID == 2){
                //destroy wall - set it to grassTile
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT + step, 0);
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_down_end, x , y + (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addEntity(new ExplodedBomb(gameHandler, Assets.explosion_down, x , y + (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }
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
