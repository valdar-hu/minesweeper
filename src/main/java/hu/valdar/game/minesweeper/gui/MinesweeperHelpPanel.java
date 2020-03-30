package hu.valdar.game.minesweeper.gui;

import hu.valdar.game.common.io.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MinesweeperHelpPanel extends JPanel {

    private final Font labelFont;

    public MinesweeperHelpPanel() {
        this.labelFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
        setPreferredSize(new Dimension(300, 300));

        setLayout(new GridLayout(3, 2));

        JLabel leftClickImg = createImageLabel("/icons/left_click.png");
        JLabel leftClickText = createTextLabel("Reveal field");

        JLabel rightClickImg = createImageLabel("/icons/right_click.png");
        JLabel rightClickText = createTextLabel("Toggle flag");

        JLabel newGameOption = createTextLabel("New game");
        newGameOption.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel newGameText = createTextLabel("<html>Use the Game menu/New Game option or the CTRL+N shortcut key.</html>");

        add(leftClickImg);
        add(leftClickText);

        add(rightClickImg);
        add(rightClickText);

        add(newGameOption);
        add(newGameText);
    }

    private JLabel createTextLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel createImageLabel(String imagePath) {
        try {
            BufferedImage image = ImageLoader.readImage(imagePath);
            return new JLabel(new ImageIcon(image), SwingConstants.CENTER);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load image from path: " + imagePath + "!", ex);
        }
    }

}
