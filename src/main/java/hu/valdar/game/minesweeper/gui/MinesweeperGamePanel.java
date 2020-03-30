package hu.valdar.game.minesweeper.gui;

import hu.valdar.game.common.gui.TabletopGameButton;
import hu.valdar.game.common.model.GameStage;
import hu.valdar.game.minesweeper.logic.MinesweeperLogic;
import hu.valdar.game.minesweeper.logic.model.FieldState;
import hu.valdar.game.minesweeper.logic.model.MinesweeperFieldModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperGamePanel extends JPanel {

    private final MinesweeperLogic logic;
    private final Font buttonFont;

    private final MinesweeperInfoPanel infoPanel;

    public MinesweeperGamePanel(MinesweeperLogic logic, MinesweeperInfoPanel infoPanel) {
        this.logic = logic;
        this.infoPanel = infoPanel;
        this.buttonFont = new Font(Font.MONOSPACED, Font.BOLD, 16);

        JLabel bombLabel = new JLabel(MinesweeperConstants.EMOJI_BOMB, SwingConstants.CENTER);
        bombLabel.setForeground(Color.WHITE);
        bombLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 180));
        bombLabel.setPreferredSize(new Dimension(MinesweeperConstants.FRAME_WIDTH, 400));
        add(bombLabel);

        JLabel text = new JLabel("Minesweeper", SwingConstants.CENTER);
        text.setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));
        text.setForeground(Color.YELLOW);
        add(text);
    }

    public void newGame() {
        setupGamePanel();
        refreshUI();
    }

    private void refreshUI() {
        GameStage stage = logic.getStage();
        infoPanel.setStatus(stage);

        for (Component component : getComponents()) {
            TabletopGameButton button = (TabletopGameButton) component;
            int rowIdx = button.getRowIndex();
            int colIdx = button.getColIndex();

            MinesweeperFieldModel field = logic.getField(rowIdx, colIdx);

            FieldState fieldState = field.getFieldState();
            if (fieldState == FieldState.REVEALED) {
                if (field.isMine()) {
                    button.setText(field.isBlownUp() ? MinesweeperConstants.EMOJI_EXPLODE
                            : MinesweeperConstants.EMOJI_BOMB);
                } else {
                    int neighbourCount = field.getNeighbourCount();
                    button.setText(neighbourCount > 0 ? String.valueOf(neighbourCount) : " ");
                }
                button.setEnabled(false);
            } else if (fieldState == FieldState.FLAGGED) {
                button.setText(MinesweeperConstants.EMOJI_FLAG);
                button.setForeground(Color.YELLOW);
            } else {
                button.setText(" ");
            }
        }
        revalidate();
        repaint();
    }

    private void setupGamePanel() {
        removeAll();
        int width = logic.getWidth();
        int height = logic.getHeight();
        setLayout(new GridLayout(width, height));
        for (int row = 0; row < width; ++row) {
            for (int column = 0; column < height; ++column) {
                final JButton button = new TabletopGameButton(row, column);
                button.setFocusable(false);
                button.setPreferredSize(new Dimension(40, 40));
                button.setFont(buttonFont);
                button.addActionListener(onClickActionListener);
                button.addMouseListener(mouseListener);
                add(button);
            }
        }
    }

    private final MouseListener mouseListener = new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent event) {
            Object eventSource = event.getSource();
            if (SwingUtilities.isRightMouseButton(event)) {
                GameStage stage = logic.getStage();
                if (stage != GameStage.PLAYING || !(eventSource instanceof TabletopGameButton)) {
                    return;
                }

                TabletopGameButton button = (TabletopGameButton) eventSource;
                int rowIndex = button.getRowIndex();
                int colIndex = button.getColIndex();

                logic.toggleFlag(rowIndex, colIndex);
                refreshUI();
            }

        }

    };

    private final ActionListener onClickActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent event) {
            GameStage stage = logic.getStage();
            if (stage != GameStage.PLAYING) {
                return;
            }

            TabletopGameButton source = (TabletopGameButton) event.getSource();
            int rowIndex = source.getRowIndex();
            int colIndex = source.getColIndex();

            logic.reveal(rowIndex, colIndex);
            logic.checkForVictory();

            refreshUI();
        }
    };

}
