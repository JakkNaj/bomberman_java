import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.entities.Player;
import fel.cvut.cz.tiles.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;

public class PlayerMovesTest {
    private Gameboard gameboard;

    @Mock
    private GameHandler gameHandler;
    @Mock
    private Game game;

    @BeforeEach
    public void prepare() {
        MockitoAnnotations.openMocks(this);
        Game.LOGGER = Logger.getLogger(Game.class.getName());
        Game.LOGGER.setLevel(Level.OFF);
        gameboard = new Gameboard(gameHandler, "src/main/resources/worlds/world1.txt");
        when(gameHandler.getGameboard()).thenReturn(gameboard);
    }

    @Test
    public void constructorTest(){
        Player player = new Player(gameHandler, 1* Tile.TILEWIDTH, 1* Tile.TILEHEIGHT, 5);

        //Assertions
        Assertions.assertEquals(5, player.getHealth());
        Assertions.assertEquals(Tile.TILEWIDTH, player.getX());
        Assertions.assertEquals(Tile.TILEHEIGHT, player.getY());
    }

    @Test
    public void moveRightToGrassTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();

        //MOVING RIGHT ON GRASS TILE
        Assertions.assertEquals(0, gameboard.getTile((int)(oldX / Tile.TILEWIDTH) + 1, (int)oldY / Tile.TILEHEIGHT).getId());
        gameboard.getEntitiesManager().getPlayer().setXmovement(+Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().move();

        //move success
        Assertions.assertEquals(oldX + Tile.TILEWIDTH, gameboard.getEntitiesManager().getPlayer().getX());
    }

    @Test
    public void moveRightToWallTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();

        //MOVING RIGHT TO THE WALL
        gameboard.setTile((int)(oldX / Tile.TILEWIDTH) + 1,(int)oldY / Tile.TILEHEIGHT, 2);
        gameboard.getEntitiesManager().getPlayer().setXmovement(+Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().move();

        //move NOT success - player get stuck right next to the wall
        int tileOnTheRightX = (int)(oldX / Tile.TILEWIDTH) + 1;
        Player player = gameboard.getEntitiesManager().getPlayer();
        Float expectedX = (float)tileOnTheRightX * Tile.TILEWIDTH - player.getBounds().x - player.getBounds().width - 1;
        Assertions.assertEquals(expectedX, gameboard.getEntitiesManager().getPlayer().getX());
    }

    @Test
    public void moveLeftToWallTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();

        //MOVING LEFT TO THE WALL
        //try to move to the center of left tile - wall
        gameboard.getEntitiesManager().getPlayer().setXmovement(-Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().move();

        //move NOT success - player get stuck right next to the wall
        int tileOnTheLeftX = (int)(oldX / Tile.TILEWIDTH) - 1;
        Player player = gameboard.getEntitiesManager().getPlayer();
        //expectedX is value of x coordinates right next to the wall
        Float expectedX = (float)tileOnTheLeftX * Tile.TILEWIDTH + Tile.TILEWIDTH - player.getBounds().x;
        Assertions.assertEquals(expectedX, gameboard.getEntitiesManager().getPlayer().getX());

    }

    @Test
    public void moveLeftToGrassTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();
        //reset player
        gameboard.getEntitiesManager().getPlayer().setX(oldX + Tile.TILEWIDTH);

        //MOVING LEFT ON GRASS TILE
        //check if tile on left is grass
        Assertions.assertEquals(0, gameboard.getTile((int)(oldX / Tile.TILEWIDTH), (int)(oldY / Tile.TILEHEIGHT)).getId());
        //move to the center of left tile
        gameboard.getEntitiesManager().getPlayer().setXmovement(-Tile.TILEWIDTH);
        gameboard.getEntitiesManager().getPlayer().move();
        //move success
        Assertions.assertEquals(oldX, gameboard.getEntitiesManager().getPlayer().getX());
    }

    @Test
    public void moveUpToGrassTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();
        //reset player
        gameboard.getEntitiesManager().getPlayer().setY(oldY + Tile.TILEHEIGHT);

        //MOVING LEFT ON GRASS TILE
        //check if tile above is grass
        Assertions.assertEquals(0, gameboard.getTile((int)(oldX / Tile.TILEHEIGHT), (int)(oldY / Tile.TILEHEIGHT)).getId());
        //move to the center of tile above
        gameboard.getEntitiesManager().getPlayer().setYmovement(-Tile.TILEHEIGHT);
        gameboard.getEntitiesManager().getPlayer().move();

        //move success
        Assertions.assertEquals(oldY, gameboard.getEntitiesManager().getPlayer().getY());
    }

    @Test
    public void moveUpToWallTest(){
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();

        //MOVING UP TO THE WALL
        //try to move up to the center of tile - wall
        gameboard.getEntitiesManager().getPlayer().setYmovement(-Tile.TILEHEIGHT);
        gameboard.getEntitiesManager().getPlayer().move();

        //move NOT success - player get stuck right next to the wall
        int tileUpY = (int)(oldY / Tile.TILEHEIGHT) - 1;
        Player player = gameboard.getEntitiesManager().getPlayer();
        //expectedY is value of x coordinates right next to the wall
        Float expectedY = (float)tileUpY * Tile.TILEHEIGHT + player.getBounds().height + player.getBounds().y;
        Assertions.assertEquals(expectedY, gameboard.getEntitiesManager().getPlayer().getY());
    }

    @Test
    public void moveDownToGrassTest(){
        float oldX = gameboard.getEntitiesManager().getPlayer().getX();
        float oldY = gameboard.getEntitiesManager().getPlayer().getY();

        //MOVING LEFT ON GRASS TILE
        //check if tile down is grass
        Assertions.assertEquals(0, gameboard.getTile((int)(oldX / Tile.TILEHEIGHT), (int)(oldY / Tile.TILEHEIGHT) + 1).getId());
        //move to the center of tile down
        gameboard.getEntitiesManager().getPlayer().setYmovement(+Tile.TILEHEIGHT);
        gameboard.getEntitiesManager().getPlayer().move();

        //move success
        Assertions.assertEquals(oldY + Tile.TILEHEIGHT, gameboard.getEntitiesManager().getPlayer().getY());
    }
}
