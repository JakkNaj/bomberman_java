package fel.cvut.cz.graphics;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class SpriteSheet { //class to store sprite-sheet of the game
    private final BufferedImage sheet;
    public SpriteSheet(BufferedImage sheet){
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x * width,y * height, width, height);
    }
}
