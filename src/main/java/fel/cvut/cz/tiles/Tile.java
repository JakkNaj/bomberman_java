package fel.cvut.cz.tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

/** Abstract class that represents one tile on Game board */
public abstract class Tile {
    //STATIC
    public static Tile[] Alltiles = new Tile[7];
    //we create only one instance of each tile type
    public static Tile grassTile = new GrassTile(0);
    public static Tile hardWall = new HardWall(1);
    public static Tile softWall = new SoftWall(2);
    public static Tile gate = new Gate(3);

    public static Tile explosionBoost = new ExplosionBoost(4);
    public static Tile extraBombBoost = new ExtraBombBoost(5);
    public static Tile runningBoost = new RunningBoost(6);
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

    /** Render tile to the screen. */
    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    /** If Ghost or Player can step on this tile. */
    public boolean isWalkable(){
        return true;
    }
}
