package fel.cvut.cz.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/** Class that represents one tile on Game board */
public abstract class Tile {
    //STATIC
    public static Tile[] Alltiles = new Tile[3];
    //we create only one instance of each tile type
    public static Tile grassTile = new GrassTile(0);
    public static Tile hardWall = new HardWall(1);
    public static Tile softWall = new SoftWall(2);

    public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
    protected BufferedImage texture;
    protected final int id;
    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;

        Alltiles[id] = this;
    }

    public int getId(){
        return id;
    }
    public void tick(){

    }
    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    public boolean isWalkable(){
        return true;
    }
}
