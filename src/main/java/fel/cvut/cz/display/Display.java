package fel.cvut.cz.display;

import javax.swing.*;
import java.awt.*;

/** Class that makes frame to display game */
public class Display {
    private JFrame frame;
    private Canvas canvas; //draw graphics in JFrame
    private final String title;
    private final int width;
    private final int height;

    public Display(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        createDisplay();
    }
    /** Initialize JFrame + Canvas */
    private void createDisplay(){
        this.frame = new JFrame(this.title);
        this.frame.setSize(width,height);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window closes down properly
        this.frame.setResizable(false); //User cannot resize window
        this.frame.setLocationRelativeTo(null); //Window appears in the center of the screen
        this.frame.setVisible(true); //Otherwise we won't see anything

        this.canvas = new Canvas();
        this.canvas.setPreferredSize(new Dimension(this.width, this.height)); //sets size of my canvas.
        this.canvas.setMaximumSize(new Dimension(this.width, this.height)); //so that canvas stays the same
        this.canvas.setMinimumSize(new Dimension(this.width, this.height)); //width and height always

        this.frame.add(this.canvas);
        frame.pack(); //resize window a little, so we can see the canvas fully
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
