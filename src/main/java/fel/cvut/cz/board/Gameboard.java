package fel.cvut.cz.board;

import fel.cvut.cz.GameHandler;
import fel.cvut.cz.entities.EntityManager;
import fel.cvut.cz.entities.Ghost;
import fel.cvut.cz.entities.Player;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;
import fel.cvut.cz.utilities.Utilities;

import java.awt.*;
import java.util.Random;

/** Class that represents Game board and is made of Tiles */
public class Gameboard {
    private GameHandler gameHandler;
    private int width, height;
    private int spawnX, spawnY;

    public int getxGate() {
        return xGate;
    }

    public int getyGate() {
        return yGate;
    }

    private int xGate, yGate;
    private int[][] board; //holds IDs of Tiles

    //Entities
    private EntityManager entitiesManager;

     public EntityManager getEntitiesManager() {
        return entitiesManager;
    }

    public Gameboard(GameHandler gameHandler, String path){
        this.gameHandler = gameHandler;
        entitiesManager = new EntityManager(gameHandler, new Player(gameHandler, 0, 0));
        entitiesManager.addGhostEntity(new Ghost(gameHandler, 6, 12));

        loadWorld(path);
        xGate = 2;
        yGate = 2;
        entitiesManager.getPlayer().setX(spawnX);
        entitiesManager.getPlayer().setY(spawnY);
    }

    public void tick(){
        entitiesManager.tick();
    }

    public void render(Graphics g){
        //we want to render only the screen we see, not the whole map
        int xStart =(int) Math.max(0, gameHandler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (gameHandler.getGameCamera().getxOffset() + gameHandler.getGameWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, gameHandler.getGameCamera().getyOffset() / Tile.TILEWIDTH);
        int yEnd = (int) Math.min(height, (gameHandler.getGameCamera().getyOffset() + gameHandler.getGameHeight()) / Tile.TILEWIDTH + 1);

        for (int y=yStart; y < yEnd; y++){
            for (int x=xStart; x < xEnd; x++){
                if (x == xGate && y == yGate && getTile(x,y).getId() == 0){
                    g.drawImage(Assets.gate,(int) (x * Tile.TILEWIDTH - gameHandler.getGameCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - gameHandler.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
                } else {
                    getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - gameHandler.getGameCamera().getxOffset()) , (int)(y * Tile.TILEHEIGHT - gameHandler.getGameCamera().getyOffset()));
                }
            }
        }

        //render entities
        entitiesManager.render(g);
    }

    public Tile getTile(int x, int y){
        if ( x < 0 || y < 0 || x >= width || y >= height){
            return Tile.grassTile; //game thinks he is on grass tile - player outside of map -> prevent errors
        }

        Tile t = Tile.Alltiles[board[x][y]];
        if (t == null) return Tile.grassTile;
        return t;
    }

    public void setTile(int x, int y, int id){
         if ( x > 0 || y > 0 || x < width || y < height ){
             board[x][y] = id;
         }
    }

    public void printWorld(){
         for (int i = 0; i < width; i++){
             for (int k = 0; k < height; k++){
                 System.out.printf(board[i][k] + " ");
             }
             System.out.print("\n");
         }
    }

    private void loadWorld(String path){ //loads world from file
        String file = Utilities.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utilities.parseInt(tokens[0]);
        height = Utilities.parseInt(tokens[1]);
        spawnX = Utilities.parseInt(tokens[2]);
        spawnY = Utilities.parseInt(tokens[3]);
        board  = new int[width][height];
        //load map
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                board[x][y] = Utilities.parseInt(tokens[(x+y * width) + 4]);
            }
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
}
