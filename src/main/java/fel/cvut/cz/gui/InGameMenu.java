package fel.cvut.cz.gui;

import fel.cvut.cz.Game;
import fel.cvut.cz.GameHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameMenu extends JFrame {
    public InGameMenu(int width, int height){
        setSize(width,height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton endGamebutton = new JButton("End Game");
        endGamebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.running = false;
                System.out.println("game stopped from In-Game menu");
                dispose();
            }
        });
        panel.add(endGamebutton);
        add(panel);
    }
}
