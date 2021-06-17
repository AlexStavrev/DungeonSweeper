package ui;

import model.enums.Value;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * A Singleton class to provide useful utilities
 *
 * @author WrexBG
 */
public class Utils {
    // The size of the tiles in pixels
    public static final int TILE_SIZE = 70;
    // The singleton instance
    private static Utils instance;

    /**
     * private constructor to complete the Singleton pattern
     */
    private Utils() {}

    /**
     * A method to get the Singleton instance or create one if there isn't
     * @return instance
     */
    public static Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * A method to play a .wav sound from the resources folder
     * @param name name of the file
     */
    public void playSound(String name) {
        SwingUtilities.invokeLater(() -> {
            try(InputStream is = getClass().getResourceAsStream(String.format("audio/%s.wav", name));
                AudioInputStream sound = AudioSystem.getAudioInputStream(is)
            ){
                DataLine.Info info = new DataLine.Info (Clip.class, sound.getFormat());
                final Clip clip = (Clip)AudioSystem.getLine(info);
                clip.open(sound);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.err.println("ERROR: Playing sound has failed");
                e.printStackTrace();
            }
        });
    }

    /**
     * A method to get a random coins icon
     * @return icon
     */
    public ImageIcon getRandomTreasureIcon() {
        return resizedImage(String.format("images/coins (%d).jpg", new Random().nextInt(2) + 1), TILE_SIZE, TILE_SIZE);
    }

    /**
     * A method to get a random flagged tile icon
     * @return icon
     */
    public ImageIcon getRandomFlagIcon() {
        return resizedImage(String.format("images/flags (%d).jpg", new Random().nextInt(16) + 1), TILE_SIZE, TILE_SIZE);
    }

    /**
     * A method to get a random tile icon
     * @return icon
     */
    public ImageIcon getRandomTileIcon() {
        return resizedImage(String.format("images/tiles (%d).jpg", new Random().nextInt(16) + 1), TILE_SIZE, TILE_SIZE);
    }

    /**
     * A method to get a random dead enemy icon
     * @return icon
     */
    public ImageIcon getRandomDeadEnemyIcon() {
        return resizedImage(String.format("images/dead_enemy (%d).jpg", new Random().nextInt(4) + 1), TILE_SIZE, TILE_SIZE);
    }

    /**
     * A method to get a random enemy icon
     * @return icon
     */
    public ImageIcon getRandomEnemyIcon() {
        return resizedImage(String.format("images/enemy (%d).jpg", new Random().nextInt(4) + 1), TILE_SIZE, TILE_SIZE);
    }

    /**
     * A method to resize an icon gotten from the resources
     * @param name name of the file
     * @param width new width
     * @param height new height
     * @return resized icon
     */
    public ImageIcon resizedImage(String name, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(name));
        return new ImageIcon(icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    /**
     * A method to get a tile with a value on it
     * @param value the cell's {@link model.enums.Value}
     * @return icon
     */
    public ImageIcon getIconForValue(Value value) {
        switch(value.getValue()) {
            case -1:
                return getRandomEnemyIcon();
            case 0:
                return resizedImage("images/revealed.jpg", TILE_SIZE, TILE_SIZE);
            case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
                return resizedImage(String.format("images/%d.jpg", value.getValue()), TILE_SIZE, TILE_SIZE);
            default:
                //TODO no such value exception
        }
        return null;
    }
}
