package fel.cvut.cz.board;

import fel.cvut.cz.tiles.Tile;
import fel.cvut.cz.utils.Utils;

import java.awt.*;

/** Class that represents Game board and is made of Tiles */
public class Gameboard {
    private int width, height;
    private int spawnX, spawnY;
    private int[][] board; //holds IDs of Tiles

    public Gameboard(String path){
        loadWorld(path);
    }

    public void tick(){

    }

    public void render(Graphics g){
        for (int y=0; y < height; y++){
            for (int x=0; x < width; x++){
                getTile(x, y).render(g, x * Tile.TILEWIDTH , y * Tile.TILEHEIGHT);
            }
        }
    }

    public Tile getTile(int x, int y){
        Tile t = Tile.tiles[board[x][y]];
        if (t == null) return Tile.grassTile;
        return t;
    }

    private void loadWorld(String path){ //loads world from file
        //TODO change later to load from file
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
                board[x][y] = Utils.parseInt(tokens[(x+y * width)] + 4);
            }
        }
    }
}
