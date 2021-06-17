package controller;

import java.util.ArrayList;
import java.util.List;

import model.*;
import model.enums.Value;

/**
 * A controller to manage the {@link model.Cell} class
 *
 * @author WrexBG
 */
public class CellController {

    /**
     * A method to toggle the flagged attribute of a cell
     * @param cell the cell object to toggle
     */
    public void toggleFlaggedCell(Cell cell) {
        cell.setFlagged(!cell.isFlagged());
    }

    /**
     * A method to set a cell to revealed
     * @param cell the cell object to be revealed
     */
    public void revealCell(Cell cell) {
        if(!cell.isRevealed()) {
            cell.setRevealed(true);
        }
    }

    /**
     * A method to increase the {@link model.enums.Value} of an array cells
     * @param cells the array of cells to be modified
     */
    public void increaseCellValue(Cell[] cells) {
        for(Cell cell : cells) {
            Value cellValue = cell.getValue();
            if (!cellValue.isMaxValue() && !cellValue.isABomb()) {
                cell.setValue(Value.fromInt(cellValue.getValue() + 1));
            }
        }
    }

    /** TODO rewrite
     * A method to find all neighboring cells based on given x, y, and a grid
     * @param cell the origin cell
     * @param grid the grid of the game
     * @return foundCells
     */
    public List<Cell> getAllNearbyCells(Cell cell, GameGrid grid) {
        List<Cell> foundCells = new ArrayList<>();
        List<Cell> initial = new ArrayList<>(List.of(cell));
        int x = cell.getX();
        int y = cell.getY();

        // Get cell left
        if(x != 0) {
            foundCells.add(grid.getCellAt(x - 1, y));
        }
        // Get cell right
        if(x != grid.getGridX()-1) {
            foundCells.add(grid.getCellAt(x + 1, y));
        }

        initial.addAll(foundCells);
        for(Cell initialCell : initial) {
            // Get left above
            if(y != 0) {
                foundCells.add(grid.getCellAt(initialCell.getX(), initialCell.getY() - 1));
            }
            // Get left below
            if(y != grid.getGridY()-1) {
                foundCells.add(grid.getCellAt(initialCell.getX(), initialCell.getY() + 1));
            }
        }
        return foundCells;
    }
}
