package hu.valdar.game.minesweeper.gui;

import hu.valdar.game.common.io.ImageLoader;
import hu.valdar.game.minesweeper.logic.MinesweeperLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MinesweeperFrame extends JFrame {

    private final MinesweeperLogic logic;
    private final MinesweeperGamePanel minesweeperGamePanel;

    public MinesweeperFrame(MinesweeperLogic logic) {
        this.logic = logic;
        setTitle(MinesweeperConstants.TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(MinesweeperConstants.FRAME_WIDTH, MinesweeperConstants.FRAME_HEIGHT);
        setPreferredSize(new Dimension(MinesweeperConstants.FRAME_WIDTH, MinesweeperConstants.FRAME_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);

        setJMenuBar(new MinesweeperMenuBar());

        MinesweeperInfoPanel infoPanel = new MinesweeperInfoPanel();
        minesweeperGamePanel = new MinesweeperGamePanel(logic, infoPanel);
        getContentPane().add(infoPanel, BorderLayout.NORTH);
        getContentPane().add(minesweeperGamePanel, BorderLayout.CENTER);
        pack();
    }

    private class MinesweeperMenuBar extends JMenuBar {

        private final MinesweeperSettingsPanel settingsPanel;

        public MinesweeperMenuBar() {
            this.settingsPanel = new MinesweeperSettingsPanel();
            JMenu gameMenu = new JMenu("Game");
            JMenuItem newGameItem = new JMenuItem(createNewGameAction());
            newGameItem.setAccelerator(KeyStroke.getKeyStroke('N',
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
            gameMenu.add(newGameItem);
            gameMenu.add(new JSeparator());

            JMenuItem exitItem = new JMenuItem(createExitAction());
            exitItem.setMnemonic(KeyEvent.VK_X);
            gameMenu.add(exitItem);

            add(gameMenu);

            JMenu helpMenu = new JMenu("Help");

            JMenuItem helpMenuItem = new JMenuItem(createHelpAction());
            helpMenuItem.setMnemonic(KeyEvent.VK_H);
            try {
                BufferedImage helpImage = ImageLoader.readImage("/icons/help.png");
                helpMenuItem.setIcon(new ImageIcon(helpImage));
            } catch (IOException ignored) {
            }
            helpMenu.add(helpMenuItem);
            add(helpMenu);
        }

        private Action createHelpAction() {
            return new AbstractAction("<html><u>H</u>elp</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    JOptionPane.showMessageDialog(null, new MinesweeperHelpPanel(),
                            "Help", JOptionPane.PLAIN_MESSAGE, null);
                }
            };
        }

        private Action createExitAction() {
            return new AbstractAction("<html>E<u>x</u>it</html>") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    System.exit(0);
                }
            };
        }

        private Action createNewGameAction() {
            return new AbstractAction("New Game") {
                @Override
                public void actionPerformed(ActionEvent event) {
                    int result = JOptionPane.showOptionDialog(null, settingsPanel,
                            "Game settings", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                            null, new String[]{"Start game"}, "Start game");
                    if (result != JOptionPane.CLOSED_OPTION) {
                        logic.newGame(settingsPanel.getGameWidth(),
                                settingsPanel.getGameHeight(),
                                settingsPanel.getMineCount());
                        minesweeperGamePanel.newGame();
                    }
                }
            };
        }

    }

}
