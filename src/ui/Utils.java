package ui;

import model.enums.Value;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

/**
 * A Singleton class to provide useful utilities
 *
 * @author WrexBG
 */
public class Utils {
    // The size of the tiles in pixels
    public static int TILE_SIZE = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/12.3);
    // The singleton instance
    private static final Utils instance = new Utils();

    //========== Images ==========
    // A map of all icons
    private final HashMap<String, ImageIcon> icons;
    // used to get random icons
    private final Random random;

    /**
     * private constructor to complete the Singleton pattern
     */
    private Utils() {
        icons = new HashMap<>();
        loadImages();
        random = new Random();
    }

    /**
     * A method to get the Singleton instance or create one if there isn't
     * @return instance
     */
    public static Utils getInstance() {
        return instance;
    }

    /**
     * A method to play a .wav file on a loop
     * @param name name of the file
     */
    public void playSoundOnLoop(String name) {
        SwingUtilities.invokeLater(() -> {
            try(InputStream is = getClass().getResourceAsStream(String.format("audio/%s.wav", name));
                AudioInputStream sound = AudioSystem.getAudioInputStream(is)
            ){
                DataLine.Info info = new DataLine.Info (Clip.class, sound.getFormat());
                final Clip clip = (Clip)AudioSystem.getLine(info);
                clip.open(sound);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
                System.err.println("ERROR: Playing sound has failed");
                e.printStackTrace();
            }
        });
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
     * A method to set the UI scale factor
     * @param scale the UI scale factor
     */
    public void setUIScale(int scale) {
        TILE_SIZE = scale;
        for (String iconName: icons.keySet()) {
            ImageIcon icon = icons.get(iconName);
            icons.replace(iconName, resizedImage(icon, TILE_SIZE, TILE_SIZE));
        }
    }

    /**
     * A method to get a random coins icon
     * @param name name of the image
     * @return icon
     */
    public ImageIcon getImage(String name) {
        return icons.get(name);
    }

    /**
     * A method to get a random coins icon
     * @return icon
     */
    public ImageIcon getRandomTreasureIcon() {
        return getImage(String.format("coins (%d)", random.nextInt(2) + 1));
    }

    /**
     * A method to get a random dead enemy icon
     * @return icon
     */
    public ImageIcon getRandomDeadEnemyIcon() {
        return getImage(String.format("dead_enemy (%d)", random.nextInt(4) + 1));
    }

    /**
     * A method to get a random enemy icon
     * @return icon
     */
    public ImageIcon getRandomEnemyIcon() {
        return getImage(String.format("enemy (%d)", random.nextInt(4) + 1));
    }

    /**
     * A method to resize an icon gotten from the resources
     * @param icon the icon
     * @param width new width
     * @param height new height
     * @return resized icon
     */
    public ImageIcon resizedImage(ImageIcon icon, int width, int height) {
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
                return getImage("revealed");
            case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
                return getImage(String.valueOf(value.getValue()));
            default:
                //TODO no such value exception
        }
        return null;
    }

    private void loadImages() {
        URL url = getClass().getResource("images/");
        if (url == null) {
            // TODO missing folder
        } else {
            try {
                File dir = new File(url.toURI());
                for (File nextFile : dir.listFiles()) {
                    ImageIcon icon = new ImageIcon(getClass().getResource(String.format("images/%s", nextFile.getName())));
                    icons.put(nextFile.getName().replaceFirst("[.][^.]+$", ""), resizedImage(icon, TILE_SIZE, TILE_SIZE));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
