package fel.cvut.cz.board;

import fel.cvut.cz.tiles.Tile;

import java.awt.*;

/** Class that represents Game board and is made of Tiles */
public class Gameboard {
    private int width, height;
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
        width = 10;
        height = 10;
        board = new int[width][height];

        for (int x=0; x < width; x++){
            for (int y=0; y < height; y++){
                board[x][y] = 1;
            }
        }
    }
}
