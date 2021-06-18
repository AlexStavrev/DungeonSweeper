package model;

import model.enums.GameState;

/**
 * A class to represent the game object
 *
 * @author WrexBG
 */
public class Game implements GameModel {
    // the difficulty of the game
    Difficulty difficulty;
    // weather or not the game has finished
    boolean hasFinished;
    // the grid of the game
    GameGrid grid;
    // the GameState of the game
    GameState state;

    /**
     * Constructor for the game object
     * @param difficulty The difficulty of the game
     */
    public Game(Difficulty difficulty) {
        setDifficulty(difficulty);
        setFinished(false);
        setState(GameState.IDLE);
        setGrid(new GameGrid(difficulty.getColumns(), difficulty.getRows()));
    }

    // Methods inherited from the {@link GameModel} interface
    @Override
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    @Override
    public Difficulty getDifficulty() {
        return this.difficulty;
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
