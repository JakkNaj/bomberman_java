package fel.cvut.cz.board;

import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.entities.EntityManager;
import fel.cvut.cz.entities.Ghost;
import fel.cvut.cz.entities.Player;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.tiles.Tile;
import fel.cvut.cz.utilities.Utilities;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


/** Class that represents Game board and is made of Tiles (their IDs).
 *  This class also holds Entities manager with all entities present on game board. */
public class Gameboard {
    private GameHandler gameHandler;
    private int width, height;
    private float spawnX, spawnY;
    private String pathToLevelFile;
    private SpecialTileHandler specialTiles;
    private int[][] board; //holds IDs of Tiles

    //Entities
    private EntityManager entitiesManager;
    private int ghostNumber = 0;

    public EntityManager getEntitiesManager() {
        return entitiesManager;
    }

    public Gameboard(GameHandler gameHandler, String path){
        this.gameHandler = gameHandler;
        entitiesManager = new EntityManager(gameHandler, new Player(gameHandler, 32, 32, 3));
        specialTiles = new SpecialTileHandler();
        pathToLevelFile = path;
        loadWorld(path);

        //placeGhosts
        if (entitiesManager.getGhostList().isEmpty()){
            for (int i = 0; i < ghostNumber; i++){
                placeGhost();
            }
        }

        entitiesManager.getPlayer().setX(spawnX);
        entitiesManager.getPlayer().setY(spawnY);
    }

    /** Method called when player dies - resets level */
    public void reset(GameHandler gameHandler, int playerHealth){
         if (playerHealth == 0){
             Game.LOGGER.info("No more lifes left, YOU LOSE!");
             Game.running = false;
         }
        Game.LOGGER.info("LEVEL RESETED\nHealth left: " + playerHealth);
        this.gameHandler = gameHandler;
        entitiesManager = new EntityManager(gameHandler, new Player(gameHandler, 1*Tile.TILEWIDTH, 1*Tile.TILEHEIGHT, playerHealth));
        specialTiles = new SpecialTileHandler();
        loadWorld(pathToLevelFile);
        entitiesManager.getPlayer().setHealth(playerHealth);
        //placeGhosts
        for (int i = 0; i < ghostNumber; i++){
            placeGhost();
        }
    }

    private void placeGhost(){
        Random random = new Random();
        int randomNumber = random.nextInt((width - 1) * (height - 1));
        int x = 0;
        while ( x < width){
            int y = 0;
            while ( y < height){
                if (x != 1 && x != 2 && y != 1 && y!= 2 &&
                        board[x][y] == 0){
                    randomNumber--;
                }
                if (randomNumber == 0){
                    Game.LOGGER.info("Ghost placed on: [" + x + ", " + y + "]");
                    entitiesManager.addGhostEntity(new Ghost(gameHandler, x, y));
                    return;
                }
                y++;
            }
            x++;
            if (x == width) x = 0;
        }
    }

    public void tick(){
        entitiesManager.tick();
    }

    public void render(Graphics g){
        //we want to render only the screen we see, not the whole map
        int xStart =(int) Math.max(0, gameHandler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (gameHandler.getGameCamera().getxOffset() + gameHandler.getGameWidth()) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0, gameHandler.getGameCamera().getyOffset() / Tile.TILEWIDTH);
        int yEnd = (int) Math.min(height, (gameHandler.getGameCamera().getyOffset() + gameHandler.getGameHeight()) / Tile.TILEWIDTH + 1);

        for (int y=yStart; y < yEnd; y++){
            for (int x=xStart; x < xEnd; x++){
                if (x == specialTiles.getxGate() && y == specialTiles.getyGate() && getTile(x,y).getId() == 0){
                    g.drawImage(Assets.gate,(int) (x * Tile.TILEWIDTH - gameHandler.getGameCamera().getxOffset()), (int)(y * Tile.TILEHEIGHT - gameHandler.getGameCamera().getyOffset()), Tile.TILEWIDTH, Tile.TILEHEIGHT, null);
                } else {
                    getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - gameHandler.getGameCamera().getxOffset()) , (int)(y * Tile.TILEHEIGHT - gameHandler.getGameCamera().getyOffset()));
                }
            }
        }

        //render entities
        entitiesManager.render(g);
    }

    public Tile getTile(int x, int y){
        if ( x < 0 || y < 0 || x >= width || y >= height){
            return Tile.grassTile; //game thinks he is on grass tile - player outside of map -> prevent errors
        }

        Tile t = Tile.Alltiles[board[x][y]];
        if (t == null) return Tile.grassTile;
        return t;
    }

    /** Set tile on giver coordinations to given id */
    public void setTile(int x, int y, int id){
         if ( x > 0 || y > 0 || x < width || y < height ){
             board[x][y] = id;
         }
    }

    /** Returns String object of given world */
    public String printWorld(){
         String res = "";
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                res += board[x][y] + " ";
            }
            res += "\n";
        }
         return res;
    }

    private void loadWorld(String path){ //loads world from file
        String file = Utilities.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utilities.parseInt(tokens[0]);
        height = Utilities.parseInt(tokens[1]);
        spawnX = Utilities.parseFloat(tokens[2]);
        spawnY = Utilities.parseFloat(tokens[3]);
        entitiesManager.getPlayer().setHealth(Utilities.parseInt(tokens[4]));
        ghostNumber = Utilities.parseInt(tokens[5]);
        entitiesManager.getPlayer().setBombCount(Utilities.parseInt(tokens[6]));
        entitiesManager.getPlayer().setBombStrength(Utilities.parseInt(tokens[7]));
        board = new int[width][height];
        int numOfStringsBeforeBoard = 11;
        int cnt = numOfStringsBeforeBoard;
        //load map
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (x == 4 && y == 1) board[x][y] = 0;
                else
                    board[x][y] = Utilities.parseInt(tokens[(x+y * width) + numOfStringsBeforeBoard]);
                cnt++;
            }
        }
        placeGate();
        if (Utilities.parseInt(tokens[8]) == 0)
            placeExplosionBoost();
        if (Utilities.parseInt(tokens[9]) == 0)
            placeBombCntBoost();
        if (Utilities.parseInt(tokens[10]) == 0)
            placeRunBoost();

        int ghostCnt = ghostNumber;
        for (int i = cnt; i < tokens.length;){
            if (ghostCnt != 0){
                entitiesManager.addGhostEntity(new Ghost(gameHandler, Utilities.parseFloat(tokens[i]), Utilities.parseFloat(tokens[i + 1]),
                        Utilities.parseFloat(tokens[i + 2]), Utilities.parseFloat(tokens[i + 3])));

                ghostCnt--;
                i += 4;
            } else {
                entitiesManager.getPlayer().placeBombDirectCoords(Utilities.parseInt(tokens[i]), Utilities.parseInt(tokens[i + 1]), Utilities.parseInt(tokens[i + 2]));
                i += 3;
            }

        }
    }

    /** Method used to save world in given file */
    public void saveWorld(String fileName){ //saves world to file
        String directoryPath = "src/main/resources/savedGames";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            boolean result = directory.mkdirs();
            if (!result) {
                System.err.println("An error occurred while creating the directory: " + directoryPath);
                return;
            }
        }

        File file = new File(directory, fileName);
        try{
            FileWriter writer = new FileWriter(file);
            String result = "";
            result += width +" "+ height +"\n";
            result += entitiesManager.getPlayer().getX() +" "+ entitiesManager.getPlayer().getY() +" "+ entitiesManager.getPlayer().getHealth() + "\n";
            result += entitiesManager.getGhostList().size()+"\n";
            result += entitiesManager.getPlayer().getBombCount()+"\n";
            result += entitiesManager.getPlayer().getBombStrength()+"\n";
            result += specialTiles.saveToFile();
            result += saveBoardToFile();
            result += entitiesManager.saveGhostsToFile();
            result += entitiesManager.getPlayer().saveBombsToFile();
            writer.write(result);
            writer.close();
        } catch (IOException e){
            System.err.println("An error occurred while saving to the file: " + e.getMessage());
        }
    }

    private String saveBoardToFile(){
        String result = "";
        for(int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (x == width - 1) result += board[x][y];
                else result += board[x][y] + " ";
            }
            result += "\n";
        }
        return result;
    }

    private void placeGate(){
        Random random = new Random();
        int randomNumber = random.nextInt((width - 1) * (height - 1));
        int x = 0;
        while ( x < width){
            int y = 0;
            while ( y < height){
                if (x != 1 && x != 2 && y != 1 && y!= 2 &&
                        board[x][y] == 2){
                    randomNumber--;
                }
                if (randomNumber == 0){
                    Game.LOGGER.info("GATE placed on: [" + x +", "+ y +"]");
                    specialTiles.setxGate(x);
                    specialTiles.setyGate(y);
                    return;
                }
                y++;
            }
            x++;
            if (x == width) x = 0;
        }
    }

    private void placeExplosionBoost(){
        Random random = new Random();
        int randomNumber = random.nextInt((width - 1) * (height - 1));
        int x = 0;
        while ( x < width){
            int y = 0;
            while ( y < height){
                if (x != 1 && x != 2 && y != 1 && y!= 2 &&
                        board[x][y] == 2){
                    randomNumber--;
                }
                if (randomNumber == 0){
                    Game.LOGGER.info("Explosion BOOST placed on: [" + x +", "+ y +"]");
                    specialTiles.setxExploB(x);
                    specialTiles.setyExploB(y);
                    return;
                }
                y++;
            }
            x++;
            if (x == width) x = 0;
        }
    }

    private void placeBombCntBoost(){
        Random random = new Random();
        int randomNumber = random.nextInt((width - 1) * (height - 1));
        int x = 0;
        while ( x < width){
            int y = 0;
            while ( y < height){
                if (x != 1 && x != 2 && y != 1 && y!= 2 &&
                        board[x][y] == 2){
                    randomNumber--;
                }
                if (randomNumber == 0){
                    Game.LOGGER.info("Bomb count BOOST placed on: [" + x +", "+ y +"]");
                    specialTiles.setxBombB(x);
                    specialTiles.setyBombB(y);
                    return;
                }
                y++;
            }
            x++;
            if (x == width) x = 0;
        }
    }

    private void placeRunBoost(){
        Random random = new Random();
        int randomNumber = random.nextInt((width - 1) * (height - 1));
        int x = 0;
        while ( x < width){
            int y = 0;
            while ( y < height){
                if (x != 1 && x != 2 && y != 1 && y!= 2 &&
                        board[x][y] == 2){
                    randomNumber--;
                }
                if (randomNumber == 0){
                    Game.LOGGER.info("Run BOOST placed on: [" + x +", "+ y +"]");
                    specialTiles.setxRunB(x);
                    specialTiles.setyRunB(y);
                    return;
                }
                y++;
            }
            x++;
            if (x == width) x = 0;
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }

    public SpecialTileHandler getSpecialTiles() {
        return specialTiles;
    }
}
