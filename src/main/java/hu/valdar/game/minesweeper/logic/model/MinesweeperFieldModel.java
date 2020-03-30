package hu.valdar.game.minesweeper.logic.model;

public class MinesweeperFieldModel {

    private boolean mine;
    private boolean blownUp;
    private FieldState fieldState;
    private int neighbourCount;

    public MinesweeperFieldModel() {
        fieldState = FieldState.UNREVEALED;
    }

    public MinesweeperFieldModel(boolean mine, boolean blownUp, FieldState fieldState, int neighbourCount) {
        this.mine = mine;
        this.blownUp = blownUp;
        this.fieldState = fieldState;
        this.neighbourCount = neighbourCount;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isBlownUp() {
        return blownUp;
    }

    public void setBlownUp(boolean blownUp) {
        this.blownUp = blownUp;
    }

    public FieldState getFieldState() {
        return fieldState;
    }

    public void setFieldState(FieldState fieldState) {
        this.fieldState = fieldState;
    }

    public int getNeighbourCount() {
        return neighbourCount;
    }

    public void setNeighbourCount(int neighbourCount) {
        this.neighbourCount = neighbourCount;
    }

    public MinesweeperFieldModel copy() {
        return new MinesweeperFieldModel(mine, blownUp, fieldState, neighbourCount);
    }
}
