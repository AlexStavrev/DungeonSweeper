package controller;

import model.Cell;
import model.Game;
import model.GameGrid;
import model.enums.GameState;
import model.enums.Value;

import java.util.List;
import java.util.Random;

/**
 * A controller to manage the {@link model.Game} and {@link model.GameGrid} classes
 *
 * @author WrexBG
 */
public class GameController {
    // the amount of tiles left to discover before winning
    int amountOfTilesLeft;
    // the score of the game
    int score;
    // the game this controller works with
    Game game;
    // a cell controller used to handle cells
    CellController cellController;

    /**
     * Standard constructor for the class
     * @param game the game where this controller will work
     */
    public GameController(Game game) {
        setGame(game);
        amountOfTilesLeft = (game.getGrid().getGridX() * game.getGrid().getGridY()) - game.getAmountOfMines();
        cellController = new CellController();
    }

    /**
     * Constructor that will create the {@link model.Game} param itself
     * @param amountOfMines the amount of mines in the game
     * @param width the width of the game's grid
     * @param height the height of the game's grid
     */
    public GameController(int amountOfMines, int width, int height) {
        this(new Game(amountOfMines, width, height));
    }

    /**
     * A method to reveal a cell
     * @param x the x-coord of the cell
     * @param y the y-coord of the cell
     */
    public void revealCellAt(int x, int y) {
        if (game.getGrid().getCellAt(x, y).getValue() == Value.BOMB) {
            game.setState(GameState.LOST);
        } else {
            addScore(Math.abs(100-amountOfTilesLeft)+Math.round(getTotalSeconds()/2));
            cellController.revealCell(game.getGrid().getCellAt(x, y));
            if(--amountOfTilesLeft == 0) {
                game.setState(GameState.WON);
            }
            if(game.getGrid().getCellAt(x, y).getValue() == Value.ZERO) {
                for(Cell cell : cellController.getAllNearbyCells(game.getGrid().getCellAt(x, y), game.getGrid())) {
                    if(!cell.isRevealed()) {
                        revealCellAt(cell.getX(), cell.getY());
                    }
                }
            }
        }
    }

    /**
     * A method to add to the score
     * @param amount amount
     */
    public void addScore(int amount) {
        score += amount;
    }

    /**
     * A method to Override in UI so it can pass the time left
     */
    public int getTotalSeconds() {
        return 0;
    }
    /**
     * A method to toggle the flagged attribute of a cell
     * @param x x-coord of the cell
     * @param y y-coord of the cell
     */
    public void toggleFlaggedCellAt(int x, int y) {
        cellController.toggleFlaggedCell(game.getGrid().getCellAt(x, y));
    }

    /**
     * A method to increase the values of cells neighboring an origin cell
     * @param x the x-coord of the origin cell
     * @param y the y-coord of the origin cell
     */
    public void increaseNearbyCellValues(int x, int y) {
        List<Cell> cells = cellController.getAllNearbyCells(game.getGrid().getCellAt(x, y), game.getGrid());
        cellController.increaseCellValue(cells.toArray(new Cell[0]));
    }

    /**
     * A method to start a game
     */
    public void startGame() {
        game.setState(GameState.STARTING);

        int xMax = game.getGrid().getGridX();
        int yMax = game.getGrid().getGridY();
        GameGrid grid = game.getGrid();

        for (int x = 0; x < xMax; x++) {
            for (int y = 0; y < yMax; y++) {
                grid.addCell(new Cell(x, y, Value.ZERO));
            }
        }
        placeBombs();
        game.setState(GameState.IN_GAME);
    }

    /**
     * A method to place bombs on the map
     */
    public void placeBombs() {
        int xMax = game.getGrid().getGridX();
        int yMax = game.getGrid().getGridY();
        int amountOfMines = game.getAmountOfMines();

        Random random = new Random();
        for(int index = 0; index < amountOfMines; index++) {
            int bombX, bombY;
            do {
                bombX = random.nextInt(xMax);
                bombY = random.nextInt(yMax);
            } while(game.getGrid().getCellAt(bombX, bombY).getValue().isABomb() || isACorner(bombX, bombY));
            game.getGrid().getCellAt(bombX, bombY).setValue(Value.BOMB);
            increaseNearbyCellValues(bombX, bombY);
        }
    }

    /**
     * A condition to check if the providex x and y coords are in a corner of the grid
     * @param x x-coord
     * @param y y-coord
     * @return true if the given coordinates are in a corner
     */
    public boolean isACorner(int x, int y) {
        if(x == y && y == 0)                           return true; // 0, 0
        if(x == 0 && y == game.getGrid().getGridY()-1) return true; // 0, max
        if(x == game.getGrid().getGridX()-1 && y == 0) return true; // max, 0
        return x == game.getGrid().getGridX() - 1 && y == game.getGrid().getGridY() - 1; // max, max
    }

    /**
     * A getter method for the score of the game
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * A setter method for the current game of this controller
     * @param game the game this controller holds
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * A getter method for the current game of this controller
     * @return the game this controller holds
     */
    public Game getGame() {
        return this.game;
    }
}
