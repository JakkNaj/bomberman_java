package fel.cvut.cz.board;

import fel.cvut.cz.Game;
import fel.cvut.cz.tiles.Tile;
import fel.cvut.cz.utils.Utils;

import java.awt.*;

/** Class that represents Game board and is made of Tiles */
public class Gameboard {
    private Game game;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] board; //holds IDs of Tiles

    public Gameboard(Game game, String path){
        this.game = game;
        loadWorld(path);
    }

    public void tick(){

    }

    public void render(Graphics g){
        //we want to render only the screen we see, not the whole map
        int xStart =(int) Math.max(0, game.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (game.getGameCamera().getxOffset() + game.getWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, game.getGameCamera().getyOffset() / Tile.TILEWIDTH);
        int yEnd = (int) Math.min(height, (game.getGameCamera().getyOffset() + game.getHeight()) / Tile.TILEWIDTH + 1);

        for (int y=yStart; y < yEnd; y++){
            for (int x=xStart; x < xEnd; x++){
                getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - game.getGameCamera().getxOffset()) , (int)(y * Tile.TILEHEIGHT - game.getGameCamera().getyOffset()));
            }
        }
    }

    public Tile getTile(int x, int y){
        Tile t = Tile.tiles[board[x][y]];
        if (t == null) return Tile.grassTile;
        return t;
    }

    private void loadWorld(String path){ //loads world from file
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
        board  = new int[width][height];
        //load map
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                board[x][y] = Utils.parseInt(tokens[(x+y * width) + 4]);
            }
        }
    }
}
