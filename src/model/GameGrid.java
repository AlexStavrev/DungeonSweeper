package model;

/**
 * This object represents a grid of cells in the {@link Game}
 *
 * @author WrexBG
 */
public class GameGrid {
    // the width of the grid
    int width;
    // the height of the grid
    int height;
    // the actual grid of cells
    Cell[][] gameGrid;

    /**
     * A standard constructor for the object
     * @param width the width of the grid
     * @param height the height of the grid
     */
    public GameGrid(int width, int height) {
        this.setGridX(width);
        this.setGridY(height);
        gameGrid = new Cell[width][height];
    }

    /**
     * A setter method for the grid's width
     * @param x the x-length (width) of the grid
     */
    public void setGridX(int x) {
        this.width = x;
    }

    /**
     * A getter method for the grid's width
     * @return width
     */
    public int getGridX() {
        return this.width;
    }

    /**
     * A setter method for the grid's height
     * @param y the y-length (height) of the grid
     */
    public void setGridY(int y) {
        this.height = y;
    }

    /**
     * A setter method for the grid's height
     * @return height
     */
    public int getGridY() {
        return this.height;
    }

    /**
     * A getter method for the whole array of {@link Cell cells} in the grid
     * @return gameGrid
     */
    public Cell[][] getGrid() {
        return this.gameGrid;
    }

    /**
     * A getter for all {@link Cell cells} in a grid's row
     * @param index The index of the roll
     * @return row
     */
    public Cell[] getRoll(int index) {
        return this.gameGrid[index];
    }

    /**
     * A getter for all {@link Cell cells} in a grid's column
     * @param index The index of the column
     * @return column
     */
    public Cell[] getColumn(int index) {
        height = this.getGridY();
        Cell[] column = new Cell[height];
        for(int i = 0; i < height; i++) {
            column[i] = gameGrid[i][index];
        }
        return column;
    }

    /**
     * A getter for a {@link Cell} in a grid
     * @param x the x-coord of the cell
     * @param y the y-coord of the cell
     * @return cell
     */
    public Cell getCellAt(int x, int y) {
        return this.gameGrid[x][y];
    }

    /**
     * A setter method for the grid array
     * @param newGrid an array of new cells which will replace the old grid
     */
    public void setGrid(Cell[][] newGrid) {
        this.gameGrid = newGrid;
    }

    /**
     * A setter method for a row with given index in the grid array
     * @param index the index of the roll
     * @param newRow the new {@link Cell} values
     */
    public void setRoll(int index, Cell[] newRow) {
        this.gameGrid[index] = newRow;
    }

    /**
     * A setter method for a column with given index in the grid array
     * @param index the index of the column
     * @param newColumn the new {@link Cell} values
     */
    public void setColumn(int index, Cell[] newColumn) {
        height = this.getGridY();
        for(int i = 0; i < height; i++) {
            gameGrid[i][index] = newColumn[i];
        }
    }

    /**
     * A setter method for a single {@link Cell} in the grid array
     * @param x the x-coord of the cell
     * @param y the y-coord of the cell
     * @param newCell the new {@link Cell}
     */
    public void setCellAt(int x, int y, Cell newCell) {
        this.gameGrid[x][y] = newCell;
    }

    public void addCell(Cell newCell) {
        this.gameGrid[newCell.getX()][newCell.getY()] = newCell;
    }
}
