package model;

import model.enums.Value;

/**
 * This class will represent the Cell objects in the game
 * A cell is 1 unit of the grid
 *
 * @author WrexBG
 */
public class Cell {
    // x-coord of this cell in the grid
    int x;
    // y-coord of this cell in the grid
    int y;
    // weather or not the cell has been flagged
    boolean isFlagged;
    // weather or not the cell has been revealed
    boolean isRevealed;
    // the Value of the cell [0-8] or BOMB
    Value value;

    /**
     * Constructor for the cell object accepting {@link Value}
     * @param value The value of the cell
     */
    public Cell(int x, int y, Value value) {
        setX(x);
        setY(y);
        setValue(value);
        setFlagged(false);
        setRevealed(false);
    }

    /**
     * A getter method for the x location of the cell
     * @return x
     */
    public int getX() {
        return this.x;
    }

    /**
     * A setter method for the x location of the cell
     * @param x x-coord in a grid
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * A getter method for the y location of the cell
     * @return y-coord in a grid
     */
    public int getY() {
        return this.y;
    }

    /**
     * A setter method for the y location of the cell
     * @param y y-coord in a grid
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * A setter method for the value of the cell
     * @return value
     */
    public Value getValue() {
        return value;
    }
    /**
     * A getter method for the value of the cell
     * @param value The {@link Value} of the cell
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * A getter method for the isFlagged condition
     * @return boolean True if the cell is flagged
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * A setter method to check if the cell is flagged
     * @param isFlagged True to flag a cell
     */
    public void setFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    /**
     * A getter method for the isRevealed condition
     * @return boolean True if the cell is already revealed
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * A setter method to check if the cell is flagged
     * @param isRevealed True to reveal a cell
     */
    public void setRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
    }
}
