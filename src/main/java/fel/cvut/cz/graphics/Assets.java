package fel.cvut.cz.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets { //loading in Images and crop them, so I don't have to crop them everytime I want to draw something.
    private static final int tileWidth = 16, tileHeight = 16;
    public static BufferedImage player, bomb, ghost, grass, hardWall, softWall;
    public static BufferedImage[] player_walkDown, player_walkUp, player_walkLeft, player_walkRight, player_standing;
    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/BombermanGeneralSprites.png"));

        player_walkDown = new BufferedImage[3];
        player_walkDown[0] = sheet.crop(4,0, tileWidth, tileHeight);
        player_walkDown[1] = sheet.crop(3,0, tileWidth, tileHeight);
        player_walkDown[2] = sheet.crop(5,0, tileWidth, tileHeight);

        player_walkUp = new BufferedImage[3];
        player_walkUp[0] = sheet.crop(4,1, tileWidth, tileHeight);
        player_walkUp[1] = sheet.crop(3,1, tileWidth, tileHeight);
        player_walkUp[2] = sheet.crop(5,1, tileWidth, tileHeight);

        player_walkLeft = new BufferedImage[3];
        player_walkLeft[0] = sheet.crop(1,0, tileWidth, tileHeight);
        player_walkLeft[1] = sheet.crop(0,0, tileWidth, tileHeight);
        player_walkLeft[2] = sheet.crop(2,0, tileWidth, tileHeight);

        player_walkRight = new BufferedImage[3];
        player_walkRight[0] = sheet.crop(1,1, tileWidth, tileHeight);
        player_walkRight[1] = sheet.crop(0,1, tileWidth, tileHeight);
        player_walkRight[2] = sheet.crop(2,1, tileWidth, tileHeight);

        player_standing = new BufferedImage[1];
        player_standing[0] = sheet.crop(4,0, tileWidth, tileHeight);

        bomb = sheet.crop(2,3, tileWidth, tileHeight);
        ghost = sheet.crop(0, 15, tileWidth, tileHeight);
        hardWall = sheet.crop(3,3, tileWidth, tileHeight);
        softWall = sheet.crop(4,3, tileWidth, tileHeight);

        grass = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = grass.createGraphics();
        Color greenGrass = new Color(56, 135, 0);
        g2d.setColor(greenGrass);
        g2d.fillRect(0, 0, grass.getWidth(), grass.getHeight());
    }
}
