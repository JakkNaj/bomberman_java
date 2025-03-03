package fel.cvut.cz.entities;

import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.graphics.Animation;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.input.KeyManager;
import fel.cvut.cz.tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Class that represents a Player in game*/
public class Player extends Beings{
    private ArrayList<Bomb> bombs;
    private int bombCount = 1;
    private int bombStrength = 1;
    private final Animation animationDown, animationUp;
    private final Animation animationLeft, animationRight, animationStanding;

    public Player(GameHandler gameHandler, float x, float y, int health) {
        super(gameHandler, x, y, DEFAULT_BEING_WIDTH, DEFAULT_BEING_HEIGHT);
        bombs = new ArrayList<Bomb>();
        this.health = health;
        //Bounding box
        bounds.x = 8;
        bounds.y = 4;
        bounds.width = 16;
        bounds.height = 24;

        //Animations
        animationDown = new Animation(100, Assets.player_walkDown);
        animationUp = new Animation(100, Assets.player_walkUp);
        animationLeft = new Animation( 100, Assets.player_walkLeft);
        animationRight = new Animation(100, Assets.player_walkRight);
        animationStanding = new Animation(100, Assets.player_standing);
    }

    /** Updates players values and change animation idx */
    @Override
    public void tick() {
        //Animation
        animationDown.tick();
        animationUp.tick();
        animationRight.tick();
        animationLeft.tick();
        animationStanding.tick();

        //tick bombs
        for (int i = 0; i < bombs.size();){
            Bomb b = bombs.get(i);
            b.tick();
            if (b.getLifeSpan() <= 0){
                //bomb exploded
                bombs.remove(b);
            } else {
                i++;
            }
        }

        getInput();
        move();

        gameHandler.getGameCamera().centerCameraOnEntity(this);
    }

    /** Gets input from keyboard */
    private void getInput(){
        Xmovement = 0;
        Ymovement = 0;

        if(gameHandler.getKeyManager().up)
            Ymovement = -speed;
        if(gameHandler.getKeyManager().down)
            Ymovement = speed;
        if(gameHandler.getKeyManager().left)
            Xmovement = -speed;
        if(gameHandler.getKeyManager().right)
            Xmovement = speed;
        if(KeyManager.bombAvailable > 0){
            placeBomb();
        }
    }

    /** Render Player to screen */
    public void render(Graphics g) {
        g.drawImage(getAnimationFrame(), (int)(this.x - gameHandler.getGameCamera().getxOffset()),
                (int)(this.y - gameHandler.getGameCamera().getyOffset()), this.width, this.height,null);

        //test bounding box
/*
        g.setColor(Color.red);
        g.fillRect((int)(x + bounds.x - handler.getGameCamera().getxOffset()), (int)(y + bounds.y - handler.getGameCamera().getyOffset()),
                bounds.width, bounds.height);
*/
        //draw bombs
        for (Bomb b : bombs) {
            b.render(g);
        }
    }

    private BufferedImage getAnimationFrame(){
        if (Ymovement < 0) {
            return animationUp.getCurrentFrame();
        } else if (Ymovement > 0){
            return animationDown.getCurrentFrame();
        } else if (Xmovement < 0){
            return animationLeft.getCurrentFrame();
        } else if (Xmovement > 0){
            return animationRight.getCurrentFrame();
        }
        return animationStanding.getCurrentFrame();
    }

    /** Moves player - dependent on XMovement and YMovement values */
    @Override
    public void move(){
        if (!checkCollisionWithGhost(Xmovement, 0f) &&
            !checkCollisionWithExplosion(Xmovement, 0f)){
            if (!checkCollisionWithBomb(Xmovement, 0f))
                //check if player doesn't collide with bomb
                moveX();
        } else {
            gameHandler.getGameboard().reset(gameHandler, health - 1);
        }
        if (!checkCollisionWithGhost(0f, Ymovement) &&
            !checkCollisionWithExplosion(0f, Ymovement)){
            if (!checkCollisionWithBomb(0f, Ymovement))
                //check if player doesn't collide with bomb
                moveY();
        } else {
            Game.LOGGER.info("Player died!");
            gameHandler.getGameboard().reset(gameHandler, health - 1);
        }
        checkSpecialTileCollisions();
    }

    private void checkSpecialTileCollisions(){
        if (checkGateCollision() && gameHandler.getGameboard().getEntitiesManager().getGhostList().isEmpty()){
            Game.LOGGER.info("YOU WIN!");
            Game.running = false;
        }
        if (checkExploBoostCollision()){
            gameHandler.getGameboard().getSpecialTiles().setxExploB(-1);
            gameHandler.getGameboard().getSpecialTiles().setyExploB(-1);
            gameHandler.getGameboard().setTile(Math.round(x / Tile.TILEWIDTH), Math.round(y / Tile.TILEHEIGHT), 0);
            Game.LOGGER.info("Player picked up Explosion boost");
            bombStrength++;
        }
        if (checkBombBoostCollision()){
            gameHandler.getGameboard().getSpecialTiles().setxBombB(-1);
            gameHandler.getGameboard().getSpecialTiles().setyBombB(-1);
            gameHandler.getGameboard().setTile(Math.round(x / Tile.TILEWIDTH), Math.round(y / Tile.TILEHEIGHT), 0);
            Game.LOGGER.info("Player can now place additional bomb");
            bombCount++;
        }
        if (checkRunBoostCollision()){
            gameHandler.getGameboard().getSpecialTiles().setxRunB(-1);
            gameHandler.getGameboard().getSpecialTiles().setyRunB(-1);
            gameHandler.getGameboard().setTile(Math.round(x / Tile.TILEWIDTH), Math.round(y / Tile.TILEHEIGHT), 0);
            Game.LOGGER.info("Player is now running faster");
            speed *= 1.2;
        }
    }
    private boolean checkGateCollision(){
        int currX = Math.round(x / Tile.TILEWIDTH);
        int currY = Math.round(y / Tile.TILEHEIGHT);
        return currX == gameHandler.getGameboard().getSpecialTiles().getxGate() &&
                currY == gameHandler.getGameboard().getSpecialTiles().getyGate();
    }
    private boolean checkExploBoostCollision(){
        int currX = Math.round(x / Tile.TILEWIDTH);
        int currY = Math.round(y / Tile.TILEHEIGHT);
        return currX == gameHandler.getGameboard().getSpecialTiles().getxExploB() &&
                currY == gameHandler.getGameboard().getSpecialTiles().getyExploB();
    }
    private boolean checkBombBoostCollision(){
        int currX = Math.round(x / Tile.TILEWIDTH);
        int currY = Math.round(y / Tile.TILEHEIGHT);
        return currX == gameHandler.getGameboard().getSpecialTiles().getxBombB() &&
                currY == gameHandler.getGameboard().getSpecialTiles().getyBombB();
    }
    private boolean checkRunBoostCollision(){
        int currX = Math.round(x / Tile.TILEWIDTH);
        int currY = Math.round(y / Tile.TILEHEIGHT);
        return currX == gameHandler.getGameboard().getSpecialTiles().getxRunB() &&
                currY == gameHandler.getGameboard().getSpecialTiles().getyRunB();
    }
    /** Player places bomb under him */
    private void placeBomb(){
        if (bombs.size() < bombCount && KeyManager.bombAvailable > 0){
            Bomb b = new Bomb(gameHandler,x,y,width,height);
            b.roundCoords();
            bombs.add(b);
            Game.LOGGER.info("BOMB placed on: [" + b.x / Tile.TILEWIDTH + ", " + b.y / Tile.TILEHEIGHT + "]");
        }
        KeyManager.bombAvailable--;
    }

    /** Placing bomb on direct coordinations with given time to live */
    public void placeBombDirectCoords(int inx, int iny, int ttl){
        Game.LOGGER.info("BOMB placed on: [" + inx + ", " + iny + "]");
        Bomb b = new Bomb(gameHandler,inx * Tile.TILEWIDTH,iny * Tile.TILEHEIGHT,ttl,width,height);
        bombs.add(b);
    }
    /** Returns the right string to write to file */
    public String saveBombsToFile(){
        String result = "";
        for(Bomb b : bombs){
            result += b.getXrounded() + " " + b.getYrounded() +" " + b.getLifeSpan() +"\n";
        }
        return result;
    }
    /** Method to check that player cannot walk on placed bomb */
    public boolean checkCollisionWithBomb(float xOff, float yOff){
        for (Bomb b : bombs){
            if (b.getCollisionBox(0f, 0f).intersects(getCollisionBox(xOff, yOff))){
                if (!b.canStopPlayer) continue;
                return true;
            } else {
                if (!b.canStopPlayer) b.canStopPlayer = true;
            }
        }
        return false;
    }


    //Getters and Setters
    public int getBombStrength() {
        return bombStrength;
    }
    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }
    public void setBombStrength(int bombStrength) {
        this.bombStrength = bombStrength;
    }
    public int getBombCount() {
        return bombCount;
    }
}
