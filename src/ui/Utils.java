package ui;

import model.enums.Value;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

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

    //========== Settings ==========
    // A value between 0-100 for the music volume
    private int musicVolume;
    // The clip the music is played on
    private Clip musicClip;
    // A value between 0-100 for the effects volume
    private int effectsVolume;

    /**
     * private constructor to complete the Singleton pattern
     */
    private Utils() {
        icons = new HashMap<>();
        loadImages();
        musicVolume = 50;
        effectsVolume = 50;
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
    public void playMusic(String name) {
        if (musicVolume <= 0) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try(InputStream audioSrc = getClass().getResourceAsStream(String.format("audio/%s.wav", name));
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream sound = AudioSystem.getAudioInputStream(bufferedIn)
            ){
                if (musicClip != null) {
                    musicClip.stop();
                }
                DataLine.Info info = new DataLine.Info (Clip.class, sound.getFormat());
                musicClip = (Clip)AudioSystem.getLine(info);
                musicClip.open(sound);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
                setMusicVolume(musicVolume);
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
        if (effectsVolume <= 0) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try(InputStream audioSrc = getClass().getResourceAsStream(String.format("audio/%s.wav", name));
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream sound = AudioSystem.getAudioInputStream(bufferedIn)
            ){
                DataLine.Info info = new DataLine.Info (Clip.class, sound.getFormat());
                final Clip clip = (Clip)AudioSystem.getLine(info);
                clip.open(sound);
                FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float)Math.log10(effectsVolume/100f));
                clip.setFramePosition(0);
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
        loadImages();
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
        return switch (value.getValue()) {
            case -1 -> getRandomEnemyIcon();
            case 0 -> getImage("revealed");
            case 1, 2, 3, 4, 5, 6, 7, 8 -> getImage(String.valueOf(value.getValue()));
            default -> throw new IllegalArgumentException("Couldn't find a case for " + value.getValue());
        };
    }

    /*private void loadImages() {
        icons.clear();
        try(final InputStream input = getClass().getResourceAsStream("/ui/images/");
            final InputStreamReader inputReader = new InputStreamReader(input);
            final BufferedReader bufferedReader = new BufferedReader(inputReader)) {
            bufferedReader.lines().forEach(System.out::println);
            bufferedReader.lines().forEach(nextFile -> {
                ImageIcon icon = new ImageIcon(getClass().getResource(String.format("images/%s", nextFile)));
                icons.put(nextFile.replaceFirst("[.][^.]+$", ""), resizedImage(icon, TILE_SIZE, TILE_SIZE));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Stupid way of loading the images because the one above only works in compiler
     * Please don't do anything like this!
     */
    private void loadImages() {
        icons.clear();
        try {
            String[] iconNames = {"1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg",
                    "alive.jpg", "dead.jpg", "won.jpg", "icon.png", "revealed.jpg",
                    "coins (1).jpg", "coins (2).jpg",
                    "dead_enemy (1).jpg", "dead_enemy (2).jpg", "dead_enemy (3).jpg", "dead_enemy (4).jpg",
                    "enemy (1).jpg", "enemy (2).jpg", "enemy (3).jpg", "enemy (4).jpg",
                    "flags (1).jpg", "flags (10).jpg", "flags (11).jpg", "flags (12).jpg", "flags (13).jpg", "flags (14).jpg", "flags (15).jpg",
                    "flags (16).jpg", "flags (2).jpg", "flags (3).jpg", "flags (4).jpg", "flags (5).jpg", "flags (6).jpg", "flags (7).jpg",
                    "flags (8).jpg", "flags (9).jpg",
                    "tiles (1).jpg", "tiles (10).jpg", "tiles (11).jpg", "tiles (12).jpg", "tiles (13).jpg", "tiles (14).jpg", "tiles (15).jpg",
                    "tiles (16).jpg", "tiles (2).jpg", "tiles (3).jpg", "tiles (4).jpg", "tiles (5).jpg", "tiles (6).jpg", "tiles (7).jpg",
                    "tiles (8).jpg", "tiles (9).jpg"};
            Arrays.stream(iconNames).forEach(nextFile -> {
                ImageIcon icon = new ImageIcon(getClass().getResource(String.format("images/%s", nextFile)));
                icons.put(nextFile.replaceFirst("[.][^.]+$", ""), resizedImage(icon, TILE_SIZE, TILE_SIZE));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMusicVolume(int volume) {
        if (volume < 0 || volume > 100)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        this.musicVolume = volume;
        FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float)Math.log10(musicVolume/100f));
    }

    public void setEffectsVolume(int volume) {
        if (volume < 0 || volume > 100)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        this.effectsVolume = volume;
    }
}