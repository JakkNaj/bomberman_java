package fel.cvut.cz.tiles;

import fel.cvut.cz.graphics.Assets;

/** Tile that add extra bomb to be placed by player, when stepped on it. */
public class ExtraBombBoost extends Tile{
    public ExtraBombBoost(int id) {
        super(Assets.bombBoost, id);
    }
}
