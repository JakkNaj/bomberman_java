package fel.cvut.cz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
    StartMenu(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game("Bomberman 2D!", 600, 400);
                game.start();
            }
        });
        panel.add(startGameButton);

        JButton howToPlayButton = new JButton("How to play");
        howToPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea textArea = new JTextArea("Walk - use up/down/left/right arrows.\n" +
                                                    "Place bomb - press 'b'");
                JOptionPane.showMessageDialog(null, textArea);
            }
        });
        panel.add(howToPlayButton);

        JButton levelEditorButton = new JButton("Level Editor");
        levelEditorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Level Editor button clicked.");
            }
        });
        panel.add(levelEditorButton);
        add(panel);
    }
}
