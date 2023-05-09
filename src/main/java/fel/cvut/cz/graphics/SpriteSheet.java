package fel.cvut.cz.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

public class SpriteSheet { //class to store sprite-sheet of the game
    private final BufferedImage sheet;
    public SpriteSheet (String path) {
        this.sheet = loadImage(path);
    }

    public BufferedImage loadImage(String path){
        try {
            return ImageIO.read(SpriteSheet.class.getResource(path)); //returns buffered image object of loaded image
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); //image not loaded
        }
        return null;
    }

    public BufferedImage crop(int x, int y, int width, int height){
        return sheet.getSubimage(x * width,y * height, width, height);
    }

    public BufferedImage cropGate(int x, int y, int width, int height){
        return sheet.getSubimage(x * width,y * height, width - 1, height);
    }
}
