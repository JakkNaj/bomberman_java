package fel.cvut.cz;

import fel.cvut.cz.utilities.ImagePanel;
import fel.cvut.cz.utilities.Utilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

public class StartMenu extends JFrame {
    StartMenu(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton level1 = new JButton("Level 1");
                level1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D! - level 1", 600, 400, "src/main/resources/worlds/world1.txt");
                        game.start();
                    }
                });
                JButton level2 = new JButton("Level 2");
                level2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D! - level 2", 600, 400,"src/main/resources/worlds/world2.txt");
                        game.start();
                    }
                });
                JPanel panel2 = new JPanel();
                panel2.setLayout(new FlowLayout());
                panel2.add(level1);
                panel2.add(level2);
                JOptionPane.showMessageDialog(null, panel2);

            }
        });
        panel.add(startGameButton, constraints);

        constraints.gridy = 1;
        JButton howToPlayButton = new JButton("How to play");
        howToPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea textArea = new JTextArea("Walk - use up/down/left/right arrows.\n" +
                                                    "Place bomb - press 'b'");
                JOptionPane.showMessageDialog(null, textArea);
            }
        });
        panel.add(howToPlayButton, constraints);

        constraints.gridy = 2;
        JButton levelEditorButton = new JButton("Level Editor");
        levelEditorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsMenu settingsMenu = new SettingsMenu();
                settingsMenu.pack();
                settingsMenu.setVisible(true);
            }
        });
        panel.add(levelEditorButton, constraints);

        constraints.gridy = 3;
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(exit, constraints);

        add(panel, BorderLayout.EAST);
        add(new ImagePanel("src/main/resources/textures/Bomberman_Logo.png"), BorderLayout.CENTER);
    }

    public class SettingsMenu extends JFrame implements ActionListener{
        JFormattedTextField ghostCount, bombCount, bombStrength;
        JCheckBox exploBoost, bombCntBoost, runBoost;
        public SettingsMenu(){
            setTitle("Setting parameters for own level");
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(new EmptyBorder(10,10,10,10));
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(5, 5, 5, 5);
            //Number of Ghosts
            JLabel ghostNum = new JLabel("Enter number of ghosts (0 - 10): ");
            NumberFormat format1 = NumberFormat.getIntegerInstance();
            NumberFormatter formatter1 = new NumberFormatter(format1);
            formatter1.setMaximum(10);
            formatter1.setMinimum(0);
            formatter1.setAllowsInvalid(false);
            ghostCount = new JFormattedTextField(formatter1);
            ghostCount.setColumns(8);
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(ghostNum, constraints);
            constraints.gridx = 1;
            panel.add(ghostCount, constraints);

            //Bomb strength
            JLabel bombsStrengthLabel = new JLabel("Enter strength of bombs (minimum 1): ");
            formatter1.setMaximum(Integer.MAX_VALUE);
            formatter1.setMinimum(0);
            formatter1.setAllowsInvalid(false);
            bombStrength = new JFormattedTextField(formatter1);
            bombStrength.setColumns(8);
            constraints.gridx = 0;
            constraints.gridy = 1;
            panel.add(bombsStrengthLabel, constraints);
            constraints.gridx = 1;
            panel.add(bombStrength, constraints);

            //Number of bombs in stash
            JLabel bombsNum = new JLabel("Enter number of bombs in stash (minimum 1): ");
            formatter1.setMaximum(Integer.MAX_VALUE);
            formatter1.setMinimum(0);
            formatter1.setAllowsInvalid(false);
            bombCount = new JFormattedTextField(formatter1);
            bombCount.setColumns(8);
            constraints.gridx = 0;
            constraints.gridy = 2;
            panel.add(bombsNum, constraints);
            constraints.gridx = 1;
            panel.add(bombCount, constraints);

            //Boost checkboxes
            exploBoost = new JCheckBox("Explosion Boost:");
            exploBoost.setHorizontalTextPosition(SwingConstants.LEFT);
            constraints.gridx = 0;
            constraints.gridy = 3;
            panel.add(exploBoost, constraints);

            bombCntBoost = new JCheckBox("Bomb count Boost:");
            bombCntBoost.setHorizontalTextPosition(SwingConstants.LEFT);
            constraints.gridx = 0;
            constraints.gridy = 4;
            panel.add(bombCntBoost, constraints);

            runBoost = new JCheckBox("Run Boost:");
            runBoost.setHorizontalTextPosition(SwingConstants.LEFT);
            constraints.gridx = 0;
            constraints.gridy = 5;
            panel.add(runBoost, constraints);

            JButton sendButton = new JButton("send!");
            constraints.gridx = 1;
            panel.add(sendButton,constraints);
            sendButton.addActionListener(this);
            add(panel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String ownLevelFilePath = "src/main/resources/worlds/ownLevel.txt";
            try{
                FileWriter writer = new FileWriter(ownLevelFilePath, false);
                //overwrite last setting of own level
                writer.write("21 15\n0 0\n");
                writer.close();

            } catch (IOException exception){
                System.out.println("Error writing to file.");
            }
            Utilities.writeToBottomOfFile(ownLevelFilePath, ghostCount.getText());
            Utilities.writeToBottomOfFile(ownLevelFilePath, bombCount.getText());
            Utilities.writeToBottomOfFile(ownLevelFilePath, bombStrength.getText());
            if (exploBoost.isSelected())
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(0));
            else
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(1));
            if (bombCntBoost.isSelected())
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(0));
            else
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(1));
            if (runBoost.isSelected())
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(0));
            else
                Utilities.writeToBottomOfFile(ownLevelFilePath, String.valueOf(1));
            Utilities.writeToBottomOfFile(ownLevelFilePath,
                    """
                            1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
                            1 0 0 2 2 2 2 2 0 0 2 0 0 0 2 0 0 2 0 2 1
                            1 0 1 2 1 0 1 2 1 2 1 0 1 0 1 2 1 0 1 0 1
                            1 2 0 2 0 0 0 0 2 2 2 0 0 0 0 2 0 2 0 0 1
                            1 0 1 0 1 0 1 0 1 0 1 0 1 0 1 0 1 2 1 0 1
                            1 0 0 0 0 2 2 0 2 0 0 0 2 0 2 0 0 0 0 2 1
                            1 0 1 0 1 2 1 2 1 2 1 0 1 0 1 0 1 0 1 2 1
                            1 0 0 0 0 2 0 2 2 2 2 0 0 0 0 2 2 2 0 0 1
                            1 0 1 0 1 0 1 0 1 2 1 0 1 0 1 0 1 2 1 0 1
                            1 2 2 2 0 2 0 0 0 0 0 2 2 2 2 0 0 2 0 0 1
                            1 0 1 2 1 0 1 0 1 0 1 0 1 0 1 0 1 2 1 0 1
                            1 2 0 2 2 2 0 2 0 0 0 2 0 2 2 2 2 2 0 2 1
                            1 0 1 0 1 2 1 0 1 0 1 0 1 2 1 0 1 2 1 2 1
                            1 0 2 0 2 0 0 0 0 0 2 0 0 2 2 0 2 0 0 0 1
                            1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1""");
            dispose();
            Game game = new Game("Bomberman 2D! - own settings", 600, 400,ownLevelFilePath);
            game.start();
        }
    }
}
