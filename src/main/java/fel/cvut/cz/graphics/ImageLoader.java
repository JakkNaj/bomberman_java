package fel.cvut.cz.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(ImageLoader.class.getResource(path)); //returns buffered image object of loaded image
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); //image not loaded
        }
        return null;
    }

}
