package fel.cvut.cz.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets { //loading in Images and crop them, so I don't have to crop them everytime I want to draw something.
    private static final int tileWidth = 16, tileHeight = 16;
    public static BufferedImage player, bomb, ghost, grass, hardWall, softWall;
    public static BufferedImage[] player_walkDown, player_walkUp, player_walkLeft, player_walkRight, player_standing;
    public static BufferedImage[] explosion_left, explosion_left_end,
                                  explosion_right, explosion_right_end,
                                  explosion_up, explosion_up_end,
                                  explosion_down, explosion_down_end;
    public static BufferedImage[] explosion_center;
    public static void init(){
        SpriteSheet sheet = new SpriteSheet("/textures/BombermanGeneralSprites.png");

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

        ghost = sheet.crop(0, 15, tileWidth, tileHeight);
        hardWall = sheet.crop(3,3, tileWidth, tileHeight);
        softWall = sheet.crop(4,3, tileWidth, tileHeight);

        bomb = sheet.crop(2,3, tileWidth, tileHeight);

        explosion_center = new BufferedImage[4];
        explosion_center[3] = sheet.crop(2,6, tileWidth, tileHeight);
        explosion_center[2] = sheet.crop(7,6, tileWidth, tileHeight);
        explosion_center[1] = sheet.crop(2,11, tileWidth, tileHeight);
        explosion_center[0] = sheet.crop(7,11,tileWidth, tileHeight);

        //left explosion
        explosion_left = new BufferedImage[4];
        explosion_left[3] = sheet.crop(1,6, tileWidth, tileHeight);
        explosion_left[2] = sheet.crop(6,6, tileWidth, tileHeight);
        explosion_left[1] = sheet.crop(1,11, tileWidth, tileHeight);
        explosion_left[0] = sheet.crop(6,11, tileWidth, tileHeight);
        explosion_left_end = new BufferedImage[4];
        explosion_left_end[3] = sheet.crop(0,6, tileWidth, tileHeight);
        explosion_left_end[2] = sheet.crop(5,6, tileWidth, tileHeight);
        explosion_left_end[1] = sheet.crop(0,11, tileWidth, tileHeight);
        explosion_left_end[0] = sheet.crop(5,11, tileWidth, tileHeight);

        //right explosion
        explosion_right = new BufferedImage[4];
        explosion_right[3] = sheet.crop(3,6, tileWidth, tileHeight);
        explosion_right[2] = sheet.crop(8,6, tileWidth, tileHeight);
        explosion_right[1] = sheet.crop(3,11, tileWidth, tileHeight);
        explosion_right[0] = sheet.crop(8,11, tileWidth, tileHeight);
        explosion_right_end = new BufferedImage[4];
        explosion_right_end[3] = sheet.crop(4,6, tileWidth, tileHeight);
        explosion_right_end[2] = sheet.crop(9,6, tileWidth, tileHeight);
        explosion_right_end[1] = sheet.crop(4,11, tileWidth, tileHeight);
        explosion_right_end[0] = sheet.crop(9,11, tileWidth, tileHeight);

        //up explosion
        explosion_up = new BufferedImage[4];
        explosion_up[3] = sheet.crop(2,5, tileWidth, tileHeight);
        explosion_up[2] = sheet.crop(7,5, tileWidth, tileHeight);
        explosion_up[1] = sheet.crop(2,10, tileWidth, tileHeight);
        explosion_up[0] = sheet.crop(7,10, tileWidth, tileHeight);
        explosion_up_end = new BufferedImage[4];
        explosion_up_end[3] = sheet.crop(2,4, tileWidth, tileHeight);
        explosion_up_end[2] = sheet.crop(7,4, tileWidth, tileHeight);
        explosion_up_end[1] = sheet.crop(2,9, tileWidth, tileHeight);
        explosion_up_end[0] = sheet.crop(7,9, tileWidth, tileHeight);

        //down explosion
        explosion_down = new BufferedImage[4];
        explosion_down[3] = sheet.crop(2,7, tileWidth, tileHeight);
        explosion_down[2] = sheet.crop(7,7, tileWidth, tileHeight);
        explosion_down[1] = sheet.crop(2,12, tileWidth, tileHeight);
        explosion_down[0] = sheet.crop(7,12, tileWidth, tileHeight);
        explosion_down_end = new BufferedImage[4];
        explosion_down_end[3] = sheet.crop(2,8, tileWidth, tileHeight);
        explosion_down_end[2] = sheet.crop(7,8, tileWidth, tileHeight);
        explosion_down_end[1] = sheet.crop(2,13, tileWidth, tileHeight);
        explosion_down_end[0] = sheet.crop(7,13, tileWidth, tileHeight);

        grass = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = grass.createGraphics();
        Color greenGrass = new Color(56, 135, 0);
        g2d.setColor(greenGrass);
        g2d.fillRect(0, 0, grass.getWidth(), grass.getHeight());
    }
}
