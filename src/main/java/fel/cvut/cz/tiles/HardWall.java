package fel.cvut.cz.tiles;

import fel.cvut.cz.graphics.Assets;

public class HardWall extends Tile{
    public HardWall(int id) {
        super(Assets.hardWall, id);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }
}
