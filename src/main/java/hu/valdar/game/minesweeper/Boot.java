package hu.valdar.game.minesweeper;

import com.formdev.flatlaf.FlatDarkLaf;
import hu.valdar.game.minesweeper.gui.MinesweeperFrame;
import hu.valdar.game.minesweeper.logic.MinesweeperLogic;

import javax.swing.*;

public class Boot {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            setupLookAndFeel();
            new MinesweeperFrame(new MinesweeperLogic()).setVisible(true);
        });
    }

    private static void setupLookAndFeel() {
        try{
            UIManager.setLookAndFeel( new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            throw new IllegalStateException("Unsupported Look & Feel: FlatDark!", ex);
        }
    }

}
