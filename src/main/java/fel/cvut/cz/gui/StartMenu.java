package fel.cvut.cz.gui;

import fel.cvut.cz.Game;
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

import static java.lang.System.exit;
import static java.lang.System.in;

public class StartMenu extends JFrame {
    public StartMenu(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        int x = 0;
        int y = 0;
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton startGameButton = new JButton("Start Game");
        JCheckBox checkBox = new JCheckBox("LOGGER");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton level1 = new JButton("Level 1");
                level1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D! - level 1", 600, 400, "src/main/resources/worlds/world1.txt");
                        if (checkBox.isSelected()) Game.enableLogger = true;
                        game.start();
                        InGameMenu inMenu = new InGameMenu(250, 160);
                        inMenu.setLocation(game.getDisplay().getFrame().getX() - 250, game.getDisplay().getFrame().getY());
                        inMenu.setVisible(true);
                    }
                });
                JButton level2 = new JButton("Level 2");
                level2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D! - level 2", 600, 400,"src/main/resources/worlds/world2.txt");
                        if (checkBox.isSelected()) Game.enableLogger = true;
                        game.start();
                        InGameMenu inMenu = new InGameMenu(250, 160);
                        inMenu.setLocation(game.getDisplay().getFrame().getX() - 250, game.getDisplay().getFrame().getY());
                        inMenu.setVisible(true);
                    }
                });
                JPanel panel2 = new JPanel();
                panel2.setLayout(new FlowLayout());
                panel2.add(level1);
                panel2.add(level2);
                JOptionPane.showMessageDialog(null, panel2, "A plain message", JOptionPane.PLAIN_MESSAGE);

            }
        });
        panel.add(startGameButton, constraints);

        constraints.gridy = 1;
        JButton howToPlayButton = new JButton("How to play");
        howToPlayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea textArea = new JTextArea("Walk - use up/down/left/right arrows.\n" +
                                                    "Place bomb - press 'b'");
                JOptionPane.showMessageDialog(null, textArea, "A plain message", JOptionPane.PLAIN_MESSAGE);
            }
        });
        panel.add(howToPlayButton, constraints);

        constraints.gridy = 2;
        JButton levelEditorButton = new JButton("Level Editor");
        levelEditorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsMenu settingsMenu = new SettingsMenu();
                settingsMenu.setLocationRelativeTo(null);
                settingsMenu.pack();
                settingsMenu.setVisible(true);
            }
        });
        panel.add(levelEditorButton, constraints);

        constraints.gridy = 4;
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                exit(0);
            }
        });
        panel.add(exit, constraints);

        constraints.gridy = 3;
        JButton load = new JButton("Load game");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame inputFrame = new JFrame("load game input");
                inputFrame.setSize(300, 80);
                JTextField txtField = new JTextField(10);
                JButton loadGame = new JButton("load");
                loadGame.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String path = "src/main/resources/savedGames/" + txtField.getText();
                        Game game = new Game("Bomberman 2D! - level 2", 600, 400, path);
                        if (checkBox.isSelected()) Game.enableLogger = true;
                        game.start();
                        InGameMenu inMenu = new InGameMenu(250, 160);
                        inMenu.setLocation(game.getDisplay().getFrame().getX() - 250, game.getDisplay().getFrame().getY());
                        inMenu.setVisible(true);
                        inputFrame.dispose();
                    }
                });
                inputFrame.add(txtField, BorderLayout.NORTH);
                inputFrame.add(loadGame, BorderLayout.EAST);
                inputFrame.setLocationRelativeTo(panel);
                inputFrame.setVisible(true);
            }
        });
        panel.add(load, constraints);


        add(panel, BorderLayout.EAST);
        add(new ImagePanel("src/main/resources/textures/Bomberman_Logo.png"), BorderLayout.CENTER);

        add(checkBox, BorderLayout.SOUTH);
        x = this.getX();
        y = this.getY();
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
            format1.setMinimumIntegerDigits(2);
            format1.setMinimumIntegerDigits(1);
            format1.setMinimumFractionDigits(0);
            format1.setMaximumFractionDigits(0);
            format1.setParseIntegerOnly(true);
            format1.setGroupingUsed(false);

            ghostCount = new JFormattedTextField(format1);
            ghostCount.addPropertyChangeListener("value", evt -> {
                Object value = ghostCount.getValue();
                if (value instanceof Number) {
                    int intValue = ((Number) value).intValue();
                    if (intValue < 0 || intValue > 10) {
                        ghostCount.setValue(1);
                    }
                }
            });

            ghostCount.setColumns(8);
            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(ghostNum, constraints);
            constraints.gridx = 1;
            panel.add(ghostCount, constraints);

            //Bomb strength
            JLabel bombsStrengthLabel = new JLabel("Enter strength of bombs (minimum 1): ");
            bombStrength = new JFormattedTextField(format1);
            bombStrength.addPropertyChangeListener("value", evt -> {
                Object value = bombStrength.getValue();
                if (value instanceof Number) {
                    int intValue = ((Number) value).intValue();
                    if (intValue < 0) {
                        bombStrength.setValue(1);
                    }
                }
            });

            bombStrength.setColumns(8);
            constraints.gridx = 0;
            constraints.gridy = 1;
            panel.add(bombsStrengthLabel, constraints);
            constraints.gridx = 1;
            panel.add(bombStrength, constraints);

            //Number of bombs in stash
            JLabel bombsNum = new JLabel("Enter number of bombs in stash (minimum 1): ");
            bombCount = new JFormattedTextField(format1);
            bombCount.addPropertyChangeListener("value", evt -> {
                Object value = bombCount.getValue();
                if (value instanceof Number) {
                    int intValue = ((Number) value).intValue();
                    if (intValue < 0) {
                        bombCount.setValue(1);
                    }
                }
            });

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

            JButton sendButton = new JButton("Play!");
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
                writer.write("21 15\n32 32 3\n");
                writer.close();

            } catch (IOException exception){
                System.out.println("Error writing to file.");
            }
            if (ghostCount.getText().isEmpty()){
                Utilities.writeToBottomOfFile(ownLevelFilePath, "1");
            } else
                Utilities.writeToBottomOfFile(ownLevelFilePath, ghostCount.getText());

            if (bombCount.getText().isEmpty())
                Utilities.writeToBottomOfFile(ownLevelFilePath, "1");
            else
                Utilities.writeToBottomOfFile(ownLevelFilePath, bombCount.getText());

            if (bombStrength.getText().isEmpty())
                Utilities.writeToBottomOfFile(ownLevelFilePath, "1");
            else
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
            Game game1 = new Game("Bomberman 2D! - own settings", 600, 400,ownLevelFilePath);
            Game.enableLogger = true;
            game1.start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            InGameMenu inMenu = new InGameMenu(250, 160);
            inMenu.setLocation(game1.getDisplay().getFrame().getX() - 250, game1.getDisplay().getFrame().getY());
            inMenu.setVisible(true);
        }
    }
}
