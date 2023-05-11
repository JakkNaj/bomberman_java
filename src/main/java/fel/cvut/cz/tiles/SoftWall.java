package fel.cvut.cz.tiles;

import fel.cvut.cz.graphics.Assets;

import java.awt.image.BufferedImage;
/** Soft wall can be destroyed by bomb */
public class SoftWall extends Tile{
    public SoftWall(int id) {
        super(Assets.softWall, id);
    }
    @Override
    public boolean isWalkable() {
        return false;
    }
}
