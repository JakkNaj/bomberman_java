import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;
import fel.cvut.cz.board.Gameboard;
import fel.cvut.cz.entities.Bomb;
import fel.cvut.cz.graphics.GameCamera;
import fel.cvut.cz.input.KeyManager;
import fel.cvut.cz.tiles.Tile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        //tick player to register collision with explosion
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
        //prepare board - place Bomb count boost in breakable wall 2 tiles beneath player
        gameboard.setTile();

        //take 3 steps right ( each time to the middle of the neighbour tile)
        for (int i = 0; i < 2; i++){
            gameboard.getEntitiesManager().getPlayer().setYmovement(+Tile.TILEHEIGHT);
            gameboard.getEntitiesManager().getPlayer().move();
        }
    }
}
