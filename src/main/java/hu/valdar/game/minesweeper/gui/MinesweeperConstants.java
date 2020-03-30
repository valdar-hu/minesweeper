package hu.valdar.game.minesweeper.gui;

public final class MinesweeperConstants {

    public static final String TITLE = "Minesweeper";
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 700;

    public static final int MIN_GAME_WIDTH = 5;
    public static final int MIN_GAME_HEIGHT = 5;
    public static final int MAX_GAME_WIDTH = 12;
    public static final int MAX_GAME_HEIGHT = 12;


    public static final String EMOJI_BOMB = "\uD83D\uDCA3";
    public static final String EMOJI_EXPLODE = "\uD83D\uDCA5";
    public static final String EMOJI_WIN = "\uD83E\uDD20";
    public static final String EMOJI_GAME_OVER = "☠️️";
    public static final String EMOJI_PLAYING = "\uD83E\uDD14";
    public static final String EMOJI_FLAG = "\uD83C\uDFF4";

    private MinesweeperConstants() {
        throw new AssertionError("You must not instantiate an utility class!");
    }
}
