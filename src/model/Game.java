package model;

import model.enums.GameState;

/**
 * A class to represent the game object
 *
 * @author WrexBG
 */
public class Game implements GameModel {
    // the amount of mines in the game
    int amountOfMines;
    // weather or not the game has finished
    boolean hasFinished;
    // the grid of the game
    GameGrid grid;
    // the GameState of the game
    GameState state;

    /**
     * Constructor for the game object
     * @param amountOfMines The amount of mines
     * @param width The width of the grid
     * @param height The height of the grid
     */
    public Game(int amountOfMines, int width, int height) {
        setAmountOfMines(amountOfMines);
        setFinished(false);
        setState(GameState.IDLE);
        setGrid(new GameGrid(width, height));
    }

    // Methods inherited from the {@link GameModel} interface
    @Override
    public void setAmountOfMines(int amount) {
        this.amountOfMines = amount;
    }
    @Override
    public int getAmountOfMines() {
        return this.amountOfMines;
    }
    @Override
    public void setFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }
    @Override
    public boolean hasFinished() {
        return this.hasFinished;
    }
    @Override
    public void setState(GameState state) {
        this.state = state;
    }
    @Override
    public GameState getState() {
        return this.state;
    }
    @Override
    public void setGrid(GameGrid grid) {
        this.grid = grid;
    }
    @Override
    public GameGrid getGrid() {
        return this.grid;
    }
}
