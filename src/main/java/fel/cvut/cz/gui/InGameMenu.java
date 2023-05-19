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
        setTitle("IN-GAME MENU");
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,5,5,5);
        constraints.gridx = 0;
        constraints.gridy = 2;
        JButton endGamebutton = new JButton("End Game");
        endGamebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.running = false;
                Game.LOGGER.info("Game stopped from in-game menu");
                dispose();
            }
        });
        panel.add(endGamebutton, constraints);

        constraints.gridy = 0;
        JPanel middlePanel = new JPanel();
        JLabel label = new JLabel("file name");
        JTextField txtField = new JTextField(20);
        txtField.setColumns(10);
        middlePanel.add(label);
        middlePanel.add(txtField);
        panel.add(middlePanel,constraints);

        constraints.gridy = 1;
        JButton send = new JButton("save game");
        panel.add(send, constraints);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = "";
                if (txtField.getText().isEmpty()){
                    fileName = "savedGame" + System.currentTimeMillis() + ".txt";
                    Game.saveGameFile = fileName;
                } else {
                    fileName = txtField.getText() + ".txt";
                    Game.saveGameFile = fileName;
                }
                JFrame frame = new JFrame("message");
                JOptionPane.showMessageDialog(frame,"saved to file: " + fileName + "\n" +
                        "SAVE THE FILE NAME IN ORDER TO LOAD THE GAME LATER", "A plain message", JOptionPane.PLAIN_MESSAGE);
                dispose();
            }
        });

        add(panel);
    }
}
