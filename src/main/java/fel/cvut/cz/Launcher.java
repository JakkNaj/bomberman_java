package fel.cvut.cz;

import fel.cvut.cz.gui.StartMenu;

/** Class to start up game */
public class Launcher {
    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu(340, 240);
        startMenu.setVisible(true);
    }
}