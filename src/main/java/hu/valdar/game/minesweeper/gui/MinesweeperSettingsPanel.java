package hu.valdar.game.minesweeper.gui;

import javax.swing.*;
import javax.swing.event.ChangeListener;

public class MinesweeperSettingsPanel extends JPanel {

    private final JSpinner widthSpinner;
    private final JSpinner heightSpinner;
    private final JSpinner mineCountSpinner;

    public MinesweeperSettingsPanel() {
        widthSpinner = new JSpinner(
                new SpinnerNumberModel(MinesweeperConstants.MIN_GAME_WIDTH, MinesweeperConstants.MIN_GAME_WIDTH,
                        MinesweeperConstants.MAX_GAME_WIDTH, 1)
        );

        ChangeListener spinnerChangeListener = createSpinnerChangeListener();
        widthSpinner.addChangeListener(spinnerChangeListener);
        heightSpinner = new JSpinner(
                new SpinnerNumberModel(MinesweeperConstants.MIN_GAME_HEIGHT, MinesweeperConstants.MIN_GAME_HEIGHT,
                        MinesweeperConstants.MAX_GAME_HEIGHT, 1)
        );
        heightSpinner.addChangeListener(spinnerChangeListener);
        int minMineCount = (MinesweeperConstants.MIN_GAME_WIDTH * MinesweeperConstants.MIN_GAME_HEIGHT) /
                (MinesweeperConstants.MIN_GAME_WIDTH + MinesweeperConstants.MIN_GAME_HEIGHT);
        int maxMineCount = (int) (MinesweeperConstants.MIN_GAME_WIDTH * MinesweeperConstants.MIN_GAME_HEIGHT * 0.65);
        mineCountSpinner = new JSpinner(
                new SpinnerNumberModel(getStarterMineCount(minMineCount, maxMineCount),
                        minMineCount, maxMineCount, 1)
        );

        add(new JLabel("Width: ", SwingConstants.CENTER));
        add(widthSpinner);
        add(new JLabel("Height: ", SwingConstants.CENTER));
        add(heightSpinner);
        add(new JLabel("Mine count: ", SwingConstants.CENTER));
        add(mineCountSpinner);
    }

    private ChangeListener createSpinnerChangeListener() {
        return event -> {
            Integer width = (Integer) widthSpinner.getValue();
            Integer height = (Integer) heightSpinner.getValue();

            int minMineCount = (width * height) / (width + height);
            int maxMineCount = (int) (width * height * 0.75);

            SpinnerNumberModel model = new SpinnerNumberModel(getStarterMineCount(minMineCount, maxMineCount),
                    minMineCount, maxMineCount, 1);
            mineCountSpinner.setModel(model);
        };
    }

    private int getStarterMineCount(int minMineCount, int maxMineCount) {
        int starterMineCount = maxMineCount / 2;
        if (starterMineCount < minMineCount) {
            starterMineCount = minMineCount;
        }
        return starterMineCount;
    }

    public int getGameWidth() {
        return (Integer) widthSpinner.getValue();
    }

    public int getGameHeight() {
        return (Integer) heightSpinner.getValue();
    }

    public int getMineCount() {
        return (Integer) mineCountSpinner.getValue();
    }
}
