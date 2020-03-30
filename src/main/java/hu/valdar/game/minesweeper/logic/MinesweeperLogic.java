package hu.valdar.game.minesweeper.logic;

import hu.valdar.game.common.model.GameStage;
import hu.valdar.game.common.model.Indices;
import hu.valdar.game.minesweeper.logic.model.FieldState;
import hu.valdar.game.minesweeper.logic.model.MinesweeperFieldModel;

import java.util.Arrays;
import java.util.Random;

public class MinesweeperLogic {

    private MinesweeperFieldModel[][] board;
    private int width;
    private int height;
    private Random random;
    private GameStage stage;

    public MinesweeperLogic() {
        stage = GameStage.NOT_STARTED;
    }

    public void newGame(int width, int height, int mineCount) {
        if (mineCount >= width * height) {
            return;
        }

        this.width = width;
        this.height = height;
        this.random = new Random();

        initializeBoard();
        createMines(mineCount);

        this.stage = GameStage.PLAYING;
    }

    private void createMines(int mineCount) {
        for (int i = 0; i < mineCount; ++i) {
            Indices indices = getNonMineFieldIndices();
            board[indices.getJ()][indices.getI()].setMine(true);

            setNeighbourCount(indices);
        }
    }

    private Indices getNonMineFieldIndices() {
        int i;
        int j;

        do {
            i = random.nextInt(width);
            j = random.nextInt(height);
        } while (board[j][i].isMine());

        return new Indices(i, j);
    }

    private void setNeighbourCount(Indices indices) {
        int x = indices.getI();
        int y = indices.getJ();

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                int i = x + dx;
                int j = y + dy;

                if (i >= 0 && i < width && j >= 0 && j < height) {
                    int neighbourCount = board[j][i].getNeighbourCount();
                    board[j][i].setNeighbourCount(neighbourCount + 1);
                }
            }
        }
    }

    private void initializeBoard() {
        board = new MinesweeperFieldModel[height][width];
        for (int j = 0; j < height; ++j) {
            for (int i = 0; i < width; ++i) {
                board[j][i] = new MinesweeperFieldModel();
            }
        }
    }

    public void reveal(int x, int y) {
        MinesweeperFieldModel field = board[y][x];

        if (field.getFieldState() != FieldState.UNREVEALED) {
            return;
        }

        field.setFieldState(FieldState.REVEALED);
        if (field.isMine()) {
            field.setBlownUp(true);
            gameOver();
            return;
        }

        if (field.getNeighbourCount() == 0) {
            revealNeighbours(x, y);
        }
    }

    private void revealNeighbours(int x, int y) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                int i = x + dx;
                int j = y + dy;
                if (i >= 0 && i < width && j >= 0 && j < height) {
                    reveal(i, j);
                }
            }
        }
    }

    private void gameOver() {
        stage = GameStage.GAME_OVER;
        revealAll();
    }

    private void revealAll() {
        for (MinesweeperFieldModel[] row : board) {
            for (MinesweeperFieldModel field : row) {
                field.setFieldState(FieldState.REVEALED);
            }
        }
    }

    public void toggleFlag(int x, int y) {
        MinesweeperFieldModel field = board[y][x];
        FieldState fieldState = field.getFieldState();

        if(fieldState == FieldState.REVEALED) {
            return;
        }

        if (fieldState == FieldState.FLAGGED) {
            field.setFieldState(FieldState.UNREVEALED);
        } else {
            field.setFieldState(FieldState.FLAGGED);
        }
    }

    public void checkForVictory() {
        if (stage != GameStage.PLAYING) {
            return;
        }

        boolean isGameWon = Arrays.stream(board).allMatch(row ->
                Arrays.stream(row).allMatch(field ->
                        field.isMine() || field.getFieldState() == FieldState.REVEALED
                )
        );

        if(isGameWon) {
            stage = GameStage.VICTORY;
            revealAll();
        }
    }

    public GameStage getStage() {
        return stage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MinesweeperFieldModel getField(int x, int y) {
        return board[y][x].copy();
    }
}
