package fel.cvut.cz.entities;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;

/** Class that represent bomb placed on gameboard by player */
public class Bomb extends Entity{
    private int lifeSpan = 180; //how many ticks the bomb lives
    public boolean canStopPlayer = false;

    private final Animation ticking;
    public Bomb(GameHandler gameHandler, float x, float y, int width, int height) {
        super(gameHandler, x, y, width, height);
        ticking = new Animation(100, Assets.bombTicking);
        //Bounding box
        bounds.x = 0;
        bounds.y = 0;
        bounds.width = 32;
        bounds.height = 32;
    }

    @Override
    public void tick() {
        lifeSpan--; //explodes on 0
        if (lifeSpan % 15 == 0) ticking.tick();
    }

    @Override
    public void render(Graphics g) {
        if (lifeSpan > 0)
            g.drawImage(ticking.getCurrentFrame(), (int)(this.x - gameHandler.getGameCamera().getxOffset()), (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height, null);
        else {
            explode(g);
        }
    }

    private void explode(Graphics g){
        // TODO check for collisions with entities and walls
        gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_center, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        int bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        //LEFT - EXPLOSION
        //look as far as the bomb should explode
        // -> stop at first wall
        // -> if wall id == 2 -> destroy
        // -> if wall id == 1 -> take step back
        int step = 0;
        boolean breakWall = false;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH - step, (int)y / Tile.TILEHEIGHT).getId();
            if (tileID == 0){
                bombStrength--;
            } else if (tileID == 1){
                step--;
                break;
            } else if (tileID == 2){
                //destroy wall - set it to grassTile or gate
                if (gameHandler.getGameboard().getxGate() == (int) x / Tile.TILEWIDTH - step &&
                    gameHandler.getGameboard().getyGate() == (int) y / Tile.TILEHEIGHT) {
                    //reveal gate
                    gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH - step, (int) y / Tile.TILEHEIGHT, 3);
                } else {
                    gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH - step, (int) y / Tile.TILEHEIGHT, 0);
                }
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            if (breakWall) {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.wallBreaking, x - (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT, true));
            } else {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_left_end, x - (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
            }
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_left, x - (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //RIGHT - EXPLOSION
        breakWall = false;
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
                //destroy wall - set it to grassTile or gate
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH + step, (int) y / Tile.TILEHEIGHT, 0);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            if (breakWall){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.wallBreaking, x + (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT, true));
            } else {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_right_end, x + (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
            }
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_right, x + (step  * Tile.TILEWIDTH), y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //UP - EXPLOSION
        breakWall = false;
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
                //destroy wall - set it to grassTile or gate
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT - step, 0);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            if (breakWall){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.wallBreaking, x , y - (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT, true));
            } else {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_up_end, x , y - (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
            }
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_up, x , y - (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                step--;
            }
        }

        //DOWN - EXPLOSION
        breakWall = false;
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
                //destroy wall - set it to grassTile or gate
                gameHandler.getGameboard().setTile((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT + step, 0);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        if (step > 0){
            if (breakWall){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.wallBreaking, x , y + (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT, true));
            } else {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_down_end, x , y + (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
            }
            step--;
            while(step > 0){
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_down, x , y + (step * Tile.TILEHEIGHT), Tile.TILEWIDTH, Tile.TILEHEIGHT));
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
