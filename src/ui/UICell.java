package ui;

import controller.GameController;
import model.Cell;
import model.enums.GameState;
import model.enums.Value;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * A Class to represent the cell in the UI
 *
 * @author WrexBG
 */
public class UICell extends JButton {
    // The index of the icon. Used to find the same flagged icon
    private int imageNumber;
    // The chance of finding a treasure in ZERO values cell
    private final float TREASURE_CHANCE = 0.30F;
    // The cell in the grid representing this button
    private Cell cell;
    // The game controller responsible for this cell's game
    private final GameController gameController;
    // The icon shown when this cell is revealed
    private ImageIcon icon;
    // A random object
    private final Random random;

    /**
     * Standard constructor for the class
     * @param cell the cell this holds
     * @param icon the icon shown when this cell is revealed
     * @param gameController the game controller responsible for this cell's game
     */
    public UICell(Cell cell, ImageIcon icon, GameController gameController) {
        super();
        random = new Random();
        //===== this =====
        setCell(cell);
        setCellIcon(icon);
        this.gameController = gameController;
        //===== super =====
        setImageNumber(random.nextInt(16) + 1);
        setIcon(Utils.getInstance().resizedImage(String.format("images/tiles (%d).jpg", imageNumber), Utils.TILE_SIZE, Utils.TILE_SIZE));
        setBorderPainted(false);
        setOpaque(true);
        setPreferredSize(new Dimension(Utils.TILE_SIZE, Utils.TILE_SIZE));
    }

    /**
     * A method to reveal this cell
     */
    public void reveal() {
        if (gameController.getGame().getState() != GameState.IN_GAME && cell.isFlagged()) {
            setCellIcon(Utils.getInstance().getRandomDeadEnemyIcon());
            this.setIcon(icon);
            gameController.addScore(100); // Add points for flagged bomb
        } else if(!cell.isRevealed() && !cell.isFlagged()) {
            gameController.revealCellAt(cell.getX(), cell.getY());
            if((cell.getValue() == Value.ZERO) && random.nextFloat() < TREASURE_CHANCE) {
                this.setCellIcon(Utils.getInstance().getRandomTreasureIcon());
                gameController.addScore(500); //Add points for treasure
            }
            this.setIcon(icon);
        }
    }

    /**
     * A method to flag this cell
     */
    public void flag() {
        if(cell.isRevealed() || gameController.getGame().getState() != GameState.IN_GAME) return;
        gameController.toggleFlaggedCellAt(cell.getX(), cell.getY());
        if(cell.isFlagged()) {
            this.setIcon(Utils.getInstance().resizedImage(String.format("images/flags (%d).jpg", imageNumber), Utils.TILE_SIZE, Utils.TILE_SIZE));
        } else this.setIcon(Utils.getInstance().resizedImage(String.format("images/tiles (%d).jpg", imageNumber), Utils.TILE_SIZE, Utils.TILE_SIZE));
    }

    /**
     * A setter method for the cell
     * @param cell this element's corresponding cell in the grid
     */
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /**
     * A getter method for this element's corresponding cell in the grid
     * @return cell
     */
    public Cell getCell() {
        return this.cell;
    }

    /**
     * A setter method for the icon
     * @param icon the one that's show when this element is revealed
     */
    public void setCellIcon(ImageIcon icon) {
        this.icon = icon;
    }

    /**
     * A getter method for this element's icon
     * @return icon
     */
    public ImageIcon getCellIcon() {
        return this.icon;
    }

    /**
     * A getter method for the image number
     * @return the index of the randomly picked image
     */
    public int getImageNumber() {
        return imageNumber;
    }

    /**
     * A setter method for the image number
     * @param imageNumber this element's index of the randomly picked image
     */
    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }
}
