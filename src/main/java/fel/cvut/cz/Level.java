package fel.cvut.cz;

import fel.cvut.cz.board.Gameboard;

/** Class representing level in Bomberman game
 *  - this class will load in parameters from json to manipulate parameters and create own level */
public class Level {
    private Gameboard gameboard;
    private int enemyCount;
    private int boosterCount;
    private int xGate,yGate; //position of level-ending gate

    //TODO method to manipulate those atributes and function to load parameters from json file
}
