package hu.valdar.game.common.gui;

import javax.swing.*;

public class TabletopGameButton extends JButton {
    private final int rowIndex;
    private final int colIndex;

    public TabletopGameButton(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

}
