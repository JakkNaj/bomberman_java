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

    public Bomb(GameHandler gameHandler, float x, float y, int ttl, int width, int height) {
        super(gameHandler, x, y, width, height);
        ticking = new Animation(100, Assets.bombTicking);
        lifeSpan = ttl;
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
        gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_center, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        int bombStrength = gameHandler.getGameboard().getEntitiesManager().getPlayer().getBombStrength();
        //LEFT - EXPLOSION
        //look as far as the bomb should explode
        // -> stop at first wall
        // -> if wall id == 2 -> destroy wall
        // -> if wall id == 1 -> take step back (unbreakable wall)
        leftExplode(bombStrength);
        //RIGHT - EXPLOSION
        rightExplode(bombStrength);
        //UP - EXPLOSION
        upExplode(bombStrength);
        //DOWN - EXPLOSION
        downExplode(bombStrength);
    }
    private void leftExplode(int bombStrength){
        int step = 0;
        boolean breakWall = false;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH - step, (int)y / Tile.TILEHEIGHT).getId();
            if (tileID == 0){ //tile is grass
                bombStrength--;
            } else if (tileID == 1){ //tile is HardWall
                step--;
                break;
            } else if (tileID == 2){ //tile is SoftWall
                //destroy wall - set it to grass or SpecialTile
                destroyWall((int) x / Tile.TILEWIDTH - step, (int) y / Tile.TILEHEIGHT);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        placeExplosionEntities(x  - (step * Tile.TILEWIDTH), y, "left", step, breakWall);
    }

    private void rightExplode(int bombStrength){
        int step = 0;
        boolean breakWall = false;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH + step, (int)y / Tile.TILEHEIGHT).getId();
            if (tileID == 0){ //tile is grass
                bombStrength--;
            } else if (tileID == 1){ //tile is HardWall
                step--;
                break;
            } else if (tileID == 2){ //tile is SoftWall
                //destroy wall - set it to grass or SpecialTile
                destroyWall((int) x / Tile.TILEWIDTH + step, (int) y / Tile.TILEHEIGHT);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        placeExplosionEntities(x + (step  * Tile.TILEWIDTH), y, "right", step, breakWall);
    }

    private void upExplode(int bombStrength){
        int step = 0;
        boolean breakWall = false;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH, (int)y / Tile.TILEHEIGHT - step).getId();
            if (tileID == 0){ //tile is grass
                bombStrength--;
            } else if (tileID == 1){ //tile is HardWall
                step--;
                break;
            } else if (tileID == 2){ //tile is SoftWall
                //destroy wall - set it to grass or SpecialTile
                destroyWall((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT - step);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        placeExplosionEntities(x, y - (step * Tile.TILEWIDTH), "up", step, breakWall);
    }

    private void downExplode(int bombStrength){
        int step = 0;
        boolean breakWall = false;
        while(bombStrength > 0){
            step++;
            int tileID = gameHandler.getGameboard().getTile((int)x / Tile.TILEWIDTH, (int)y / Tile.TILEHEIGHT + step).getId();
            if (tileID == 0){ //tile is grass
                bombStrength--;
            } else if (tileID == 1){ //tile is HardWall
                step--;
                break;
            } else if (tileID == 2){ //tile is SoftWall
                //destroy wall - set it to grass or SpecialTile
                destroyWall((int) x / Tile.TILEWIDTH, (int) y / Tile.TILEHEIGHT + step);
                breakWall = true;
                break;
            }
        }
        //end of left explosion placed on step coords
        placeExplosionEntities(x, y + (step * Tile.TILEWIDTH), "down", step, breakWall);
    }

    private void destroyWall(int currX, int currY){
        if (gameHandler.getGameboard().getSpecialTiles().collideWithGate(currX, currY)){
            //reveal gate
            gameHandler.getGameboard().setTile(currX, currY, 3); //gate
        } else if(gameHandler.getGameboard().getSpecialTiles().collideWithExploBoost(currX, currY)){
            //reveal explosion boost
            gameHandler.getGameboard().setTile(currX, currY, 4); //explosion boost
        } else if(gameHandler.getGameboard().getSpecialTiles().collideWithBombBoost(currX, currY)){
            //reveal bomb boost
            gameHandler.getGameboard().setTile(currX, currY, 5); //bomb boost
        } else if(gameHandler.getGameboard().getSpecialTiles().collideWithRunBoost(currX, currY)){
            //reveal run boost
            gameHandler.getGameboard().setTile(currX, currY, 6); //run boost
        } else {
            gameHandler.getGameboard().setTile(currX, currY, 0); //grass
        }
    }
    private void placeExplosionEntities(float currX, float currY, String direction, int step, boolean wallBreaked){
        if (step > 0){
            if (wallBreaked) {
                gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.wallBreaking, currX, currY, Tile.TILEWIDTH, Tile.TILEHEIGHT, true));
            } else {
                switch (direction) {
                    case "left" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_left_end, currX, currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "right" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_right_end, currX, currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "up" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_up_end, currX, currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "down" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_down_end, currX, currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                }
            }
            step--;
            while(step > 0){
                switch (direction) {
                    case "left" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_left, currX + (step * Tile.TILEWIDTH), currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "right" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_right, currX - (step * Tile.TILEWIDTH), currY, Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "up" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_up, currX, currY + (step * Tile.TILEWIDTH), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                    case "down" ->
                            gameHandler.getGameboard().getEntitiesManager().addExplosionEntity(new Explosion(gameHandler, Assets.explosion_down, currX, currY - (step * Tile.TILEWIDTH), Tile.TILEWIDTH, Tile.TILEHEIGHT));
                }
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
