package fel.cvut.cz.display;

import javax.swing.*;

/** Class that makes frame to display game */
public class Display {
    private JFrame frame;
    private final String title;
    private final int width;
    private final int height;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }
    /** Initialize JFrame */
    private void createDisplay(){
        this.frame = new JFrame(this.title);
        this.frame.setSize(width,height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window closes down properly
        this.frame.setResizable(false); //User cannot resize window
        this.frame.setLocationRelativeTo(null); //Window appears in the center of the screen
        this.frame.setVisible(true); //Otherwise we won't see anything
    }
}
