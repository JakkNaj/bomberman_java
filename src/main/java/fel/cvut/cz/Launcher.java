package fel.cvut.cz;

/** Class to start up my game */
public class Launcher {
    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu(600, 400);
        startMenu.setVisible(true);
    }
}