package fel.cvut.cz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class StartMenu extends JFrame {
    StartMenu(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton level1 = new JButton("Level 1");
                level1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D!", 600, 400, "src/main/resources/worlds/world1.txt");
                        game.start();
                    }
                });
                JButton level2 = new JButton("Level 2");
                level2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Game game = new Game("Bomberman 2D!", 600, 400,"src/main/resources/worlds/world2.txt");
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
                SettingsMenu settingsMenu = new SettingsMenu();
                settingsMenu.pack();
                settingsMenu.setVisible(true);
            }
        });
        panel.add(levelEditorButton);
        add(panel);
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
            System.out.println("ghost number: " + ghostCount.getText());
            System.out.println("bomb number: " + bombCount.getText());
            System.out.println("bomb strength number: " + bombStrength.getText());
            System.out.println("explosion boost: " + exploBoost.isSelected());
            System.out.println("bomb count boost: " + bombCntBoost.isSelected());
            System.out.println("run boost: " + runBoost.isSelected());
        }
    }
}
