package fel.cvut.cz.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets { //loading in Images and crop them, so I don't have to crop them everytime I want to draw something.
    private static final int tileWidth = 16, tileHeight = 16;
    public static BufferedImage player, bomb, ghost;
    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/BombermanGeneralSprites.png"));
        player = sheet.crop(4,0,tileWidth, tileHeight);
        bomb = sheet.crop(2,3,tileWidth,tileHeight);
        ghost = sheet.crop(0, 15,tileWidth,tileHeight);
    }
}
