import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.entities.Bomb;
import fel.cvut.cz.entities.Entity;
import fel.cvut.cz.entities.Explosion;
import fel.cvut.cz.graphics.Assets;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.input.KeyManager;
import fel.cvut.cz.tiles.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProcessTests {
    private Gameboard gameboard;

    private KeyManager keyManager;
    private GameCamera gameCamera;

    @Mock
    private GameHandler gameHandler;

    @Mock
    private Game game;

    @BeforeEach
    public void prepare(){
        MockitoAnnotations.openMocks(this);
        Game.LOGGER = Logger.getLogger(Game.class.getName());
        Assets.init();
        gameboard = new Gameboard(gameHandler, "src/main/resources/testBoards/testworld1.txt");
        when(gameHandler.getGameboard()).thenReturn(gameboard);
        keyManager = new KeyManager();
        when(gameHandler.getKeyManager()).thenReturn(keyManager);
        gameCamera = new GameCamera(gameHandler, 0 , 0);
        when(gameHandler.getGameCamera()).thenReturn(gameCamera);
        when(gameHandler.getGame()).thenReturn(game);
    }

    @Test
    public void playerDieToBomb(){
        int playerLives = gameboard.getEntitiesManager().getPlayer().getHealth();
        //take 3 steps right ( each time to the middle of the neighbour tile)
        for (int i = 0; i < 3; i++){
            gameboard.getEntitiesManager().getPlayer().setXmovement(+Tile.TILEWIDTH);
            gameboard.getEntitiesManager().getPlayer().move();
        }

        //simulate pressing 'b' key
        KeyManager.bombAvailable++;
        //tick player -> places bomb
        gameboard.getEntitiesManager().getPlayer().tick();
        //assert that bomb is placed
        Assertions.assertEquals(1, gameboard.getEntitiesManager().getPlayer().getBombs().size());
        //tick bomb till explosion
        Bomb b = gameboard.getEntitiesManager().getPlayer().getBombs().get(0);
        for (int i = b.getLifeSpan(); i > 0; i--){
            b.tick();
        }
        gameboard.getEntitiesManager().getPlayer().tick();


        //Assertions
        //assert that player has fewer lives
        Assertions.assertEquals(playerLives - 1, gameboard.getEntitiesManager().getPlayer().getHealth());
        //assert that level was reseted - player on starting position, bombs placed - 0, no explosions on board
        Assertions.assertEquals(Tile.TILEWIDTH, gameboard.getEntitiesManager().getPlayer().getX());
        Assertions.assertEquals(Tile.TILEHEIGHT, gameboard.getEntitiesManager().getPlayer().getY());
        Assertions.assertEquals(0, gameboard.getEntitiesManager().getPlayer().getBombs().size());
        Assertions.assertEquals(0, gameboard.getEntitiesManager().getExplosionList().size());
    }

    @Test
    public void playerTakeBombCountBoost(){
        int plLives = gameboard.getEntitiesManager().getPlayer().getHealth();
        int plX =(int) gameboard.getEntitiesManager().getPlayer().getX() / Tile.TILEWIDTH;
        int plY =(int) gameboard.getEntitiesManager().getPlayer().getY() / Tile.TILEHEIGHT;
        //prepare board - place Bomb count boost in breakable wall 2 tiles beneath player
        gameboard.getSpecialTiles().setxBombB(plX);
        gameboard.getSpecialTiles().setyBombB(plY + 3);

        //take 2 steps down (each time to the middle of the neighbour tile)
        for (int i = 0; i < 2; i++){
            gameboard.getEntitiesManager().getPlayer().setYmovement(+Tile.TILEHEIGHT);
            gameboard.getEntitiesManager().getPlayer().move();
        }

        //simulate pressing 'b' key
        KeyManager.bombAvailable++;
        //tick player -> places bomb
        gameboard.getEntitiesManager().getPlayer().tick();

        //take 2 steps back (up) (each time to the middle of the neighbour tile)
        for (int i = 0; i < 2; i++){
            //System.out.println("coords: " + (int)gameboard.getEntitiesManager().getPlayer().getX() / Tile.TILEWIDTH + " " + (int)gameboard.getEntitiesManager().getPlayer().getY() / Tile.TILEHEIGHT);
            gameboard.getEntitiesManager().getPlayer().setYmovement(-Tile.TILEHEIGHT);
            gameboard.getEntitiesManager().getPlayer().move();
        }

        //tick bomb till explosion
        Bomb b = gameboard.getEntitiesManager().getPlayer().getBombs().get(0);
        for (int i = b.getLifeSpan(); i > 0; i--){
            b.tick();
        }
        //stop player from moving
        gameboard.getEntitiesManager().getPlayer().setYmovement(0);
        //tick explosion till they expire
        for (int i = 20; i > 0; i--){
            for (Entity e : gameboard.getEntitiesManager().getExplosionList()){
                Explosion expl = (Explosion) e;
                expl.tick();
            }
        }
        //tick entity manager to register expire of explosion
        gameboard.getEntitiesManager().tick();

        //assert that no explosions are present
        Assertions.assertEquals(0, gameboard.getEntitiesManager().getExplosionList().size());

        //assert player's life not changed
        Assertions.assertEquals(plLives, gameboard.getEntitiesManager().getPlayer().getHealth());

        //take 3 steps down and pickup bomb count boost
        for (int i = 0; i < 3; i++){
            gameboard.getEntitiesManager().getPlayer().setYmovement(+Tile.TILEHEIGHT);
            gameboard.getEntitiesManager().getPlayer().move();
        }

        //The player should be now able to place 2 bombs at the same time
        KeyManager.bombAvailable = 2;
        //tick player -> places bomb
        gameboard.getEntitiesManager().getPlayer().tick();
        //take step up
        gameboard.getEntitiesManager().getPlayer().setYmovement(-Tile.TILEHEIGHT);
        gameboard.getEntitiesManager().getPlayer().move();
        //tick player -> places bomb
        gameboard.getEntitiesManager().getPlayer().tick();

        //Assertions
        //assert that two bombs are placed at the same time
        Assertions.assertEquals(2, gameboard.getEntitiesManager().getPlayer().getBombs().size());
        //assert bomb positions
        Assertions.assertEquals(plX * Tile.TILEWIDTH, gameboard.getEntitiesManager().getPlayer().getBombs().get(0).getX());
        Assertions.assertEquals((plY + 3) * Tile.TILEHEIGHT, gameboard.getEntitiesManager().getPlayer().getBombs().get(0).getY());
        Assertions.assertEquals(plX * Tile.TILEWIDTH, gameboard.getEntitiesManager().getPlayer().getBombs().get(1).getX());
        Assertions.assertEquals((plY + 2) * Tile.TILEHEIGHT, gameboard.getEntitiesManager().getPlayer().getBombs().get(1).getY());
    }
}
