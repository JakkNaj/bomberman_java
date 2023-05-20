package fel.cvut.cz.utilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Image panel class used to display Bomberman logo in main menu. */
public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
            image = image.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            System.out.println("logo not loaded properly");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}
