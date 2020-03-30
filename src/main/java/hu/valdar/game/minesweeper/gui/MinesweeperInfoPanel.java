package hu.valdar.game.minesweeper.gui;

import hu.valdar.game.common.model.GameStage;

import javax.swing.*;
import java.awt.*;

public class MinesweeperInfoPanel extends JPanel {

    private final JLabel infoLabel;

    public MinesweeperInfoPanel() {
        setPreferredSize(new Dimension(MinesweeperConstants.FRAME_WIDTH, 50));
        infoLabel = new JLabel("", SwingConstants.CENTER);
        Font buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 40);
        infoLabel.setFont(buttonFont);
        add(infoLabel);
    }

    public void setStatus(GameStage stage) {
        if(stage == GameStage.PLAYING) {
            infoLabel.setText(MinesweeperConstants.EMOJI_PLAYING);
            infoLabel.setForeground(Color.YELLOW);
        } else if (stage == GameStage.GAME_OVER) {
            infoLabel.setText("  " + MinesweeperConstants.EMOJI_GAME_OVER);
            infoLabel.setForeground(Color.RED);
        } else if (stage == GameStage.VICTORY) {
            infoLabel.setText(MinesweeperConstants.EMOJI_WIN);
            infoLabel.setForeground(Color.GREEN);
        } else {
            infoLabel.setText(" ");
        }
    }

}
