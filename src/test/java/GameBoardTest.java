import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.entities.Bomb;
import fel.cvut.cz.entities.Entity;
import fel.cvut.cz.entities.Ghost;
import fel.cvut.cz.tiles.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class GameBoardTest {

    private Gameboard gameboard;
    @Mock
    private GameHandler gameHandler;

    @Mock
    private Game game;

    @BeforeEach
    public void prepare(){
        MockitoAnnotations.openMocks(this);
        Game.LOGGER = Logger.getLogger(Game.class.getName());
        Game.LOGGER.setLevel(Level.OFF);
    }

    private String[] loadFileInStringArray(String path) throws IOException {
        Path file = Paths.get(path);
        String str = Files.readString(file);
        return str.split("\\s+");
    }

    @ParameterizedTest
    @CsvSource({"src/main/resources/worlds/world1.txt", "src/main/resources/worlds/world2.txt"}) //PREMADE LEVELS
    public void constructorValid(String path) {
        gameboard = new Gameboard(gameHandler, path);

        String[]tokens = new String[0];
        try {
            tokens = loadFileInStringArray(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ASSERTIONS
        int cnt = 0;
        //width and height
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getWidth());
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getHeight());

        //player coordinates and health
        Assertions.assertEquals(Float.parseFloat(tokens[cnt++]), gameboard.getEntitiesManager().getPlayer().getX());
        Assertions.assertEquals(Float.parseFloat(tokens[cnt++]), gameboard.getEntitiesManager().getPlayer().getY());
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getEntitiesManager().getPlayer().getHealth());

        //ghosts
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getEntitiesManager().getGhostList().size());

        //player bomb count and strength
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getEntitiesManager().getPlayer().getBombCount());
        Assertions.assertEquals(Integer.parseInt(tokens[cnt++]), gameboard.getEntitiesManager().getPlayer().getBombStrength());

        //gate placed in gameboard behind breakable wall
        Assertions.assertTrue(gameboard.getSpecialTiles().getxGate() >= 0);
        Assertions.assertTrue(gameboard.getSpecialTiles().getxGate() <= gameboard.getWidth());
        Assertions.assertTrue(gameboard.getSpecialTiles().getyGate() >= 0);
        Assertions.assertTrue(gameboard.getSpecialTiles().getyGate() <= gameboard.getWidth());
        Assertions.assertEquals(2, gameboard.getTile(gameboard.getSpecialTiles().getxGate(), gameboard.getSpecialTiles().getyGate()).getId());

        //same for the boosts
        //Explosion BOOST
        if (Integer.parseInt(tokens[cnt++]) == 0){
            //explosion boost place in board
            Assertions.assertTrue(gameboard.getSpecialTiles().getxExploB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getxExploB() <= gameboard.getWidth());
            Assertions.assertTrue(gameboard.getSpecialTiles().getyExploB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyExploB() <= gameboard.getWidth());
            Assertions.assertEquals(2, gameboard.getTile(gameboard.getSpecialTiles().getxExploB(), gameboard.getSpecialTiles().getyExploB()).getId());
        } else {
            //explosion boost out of map
            Assertions.assertTrue(gameboard.getSpecialTiles().getxExploB() == -1);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyExploB() == -1);
        }

        //Bomb count BOOST
        if (Integer.parseInt(tokens[cnt++]) == 0){
            //bomb count boost place in board
            Assertions.assertTrue(gameboard.getSpecialTiles().getxBombB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getxBombB() <= gameboard.getWidth());
            Assertions.assertTrue(gameboard.getSpecialTiles().getyBombB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyBombB() <= gameboard.getWidth());
            Assertions.assertEquals(2, gameboard.getTile(gameboard.getSpecialTiles().getxBombB(), gameboard.getSpecialTiles().getyBombB()).getId());
        } else {
            //bomb count boost out of map
            Assertions.assertTrue(gameboard.getSpecialTiles().getxBombB() == -1);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyBombB() == -1);
        }

        //Run BOOST
        if (Integer.parseInt(tokens[cnt++]) == 0){
            //run boost place in board
            Assertions.assertTrue(gameboard.getSpecialTiles().getxRunB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getxRunB() <= gameboard.getWidth());
            Assertions.assertTrue(gameboard.getSpecialTiles().getyRunB() >= 0);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyRunB() <= gameboard.getWidth());
            Assertions.assertEquals(2, gameboard.getTile(gameboard.getSpecialTiles().getxRunB(), gameboard.getSpecialTiles().getyRunB()).getId());
        } else {
            //run boost out of map
            Assertions.assertTrue(gameboard.getSpecialTiles().getxRunB() == -1);
            Assertions.assertTrue(gameboard.getSpecialTiles().getyRunB() == -1);
        }
    }

    @Test
    public void saveThenLoadTestAfterChanges(){
        constructorValid("src/main/resources/worlds/world1.txt");
        // "play the game" - move player, kill one ghost, break walls, place bomb THEN SAVE the game
        //move player
        gameboard.getEntitiesManager().getPlayer().setX(3* Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().setY(8* Tile.TILEHEIGHT);
        //kill ghost
        gameboard.getEntitiesManager().getGhostList().remove(0);
        int ghostNum = gameboard.getEntitiesManager().getGhostList().size();
        //break walls
        gameboard.setTile(3,1, 0); //0 - grass tile
        //place bomb
        gameboard.getEntitiesManager().getPlayer().getBombs().add(new Bomb(gameHandler, 3*Tile.TILEWIDTH, 6*Tile.TILEHEIGHT, 60, Tile.TILEWIDTH, Tile.TILEHEIGHT));
        //SAVE the world in File
        gameboard.saveWorld("saveTest.txt");

        String path = "src/main/resources/savedGames/saveTest.txt";
        gameboard = new Gameboard(gameHandler, path);

        //Assertions
        constructorValid(path);
        int cnt = 0;
        //Player coordinates
        Assertions.assertEquals(3* Tile.TILEWIDTH, gameboard.getEntitiesManager().getPlayer().getX());
        Assertions.assertEquals(8* Tile.TILEHEIGHT, gameboard.getEntitiesManager().getPlayer().getY());
        //ghost count
        Assertions.assertEquals(ghostNum, gameboard.getEntitiesManager().getGhostList().size());
        //broken wall
        Assertions.assertEquals(0, gameboard.getTile(3,1).getId());
        //bomb in Play
        Assertions.assertEquals(1, gameboard.getEntitiesManager().getPlayer().getBombs().size());
    }

    @ParameterizedTest
    @CsvSource({"src/main/resources/worlds/world1.txt", "src/main/resources/worlds/world2.txt"})
    public void resetBoardTest(String path){
        constructorValid(path);
        when(gameHandler.getGameboard()).thenReturn(gameboard);

        String strBoard = gameboard.printWorld();
        int ghostNum = gameboard.getEntitiesManager().getGhostList().size();

        //move player in the ghost or explode
        //player has more than 0 lifes left - reset level

        //player
        gameboard.getEntitiesManager().getPlayer().setX(5 * Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().setY(3 * Tile.TILEHEIGHT);

        //ghost - placed 1 pixel next to player
        Entity g = gameboard.getEntitiesManager().getGhostList().get(0);
        g.setX(5 * Tile.TILEWIDTH + 1);
        g.setY(3 * Tile.TILEWIDTH + 1);

        //move player -> collided with ghost -> died -> RESET HAPPENS
        gameboard.getEntitiesManager().getPlayer().setXmovement(1);
        gameboard.getEntitiesManager().getPlayer().move();

        //Assertions
        //player lost life
        Assertions.assertEquals(2, gameboard.getEntitiesManager().getPlayer().getHealth());
        //board reseted to previous state
        String strResetedBoard = gameboard.printWorld();
        Assertions.assertEquals(strBoard, strResetedBoard);
        //ghost respawned in previous count
        int ghostNumReseted = gameboard.getEntitiesManager().getGhostList().size();
        Assertions.assertEquals(ghostNum, ghostNumReseted);
    }
}
