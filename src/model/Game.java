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
    // The chance of finding a treasure in ZERO values cell
    public float treasureChance;

    /**
     * Constructor for the game object
     * @param difficulty The difficulty of the game
     */
    public Game(Difficulty difficulty) {
        setTreasureChance(.3F);
        setDifficulty(difficulty);
        setFinished(false);
        setState(GameState.IDLE);
        setGrid(new GameGrid(difficulty.getColumns(), difficulty.getRows()));
    }

    /**
     * Constructor for the game object
     * @param difficulty The difficulty of the game
     * @param treasureChance The chance of finding a treasure
     */
    public Game(Difficulty difficulty, float treasureChance) {
        this(difficulty);
        this.treasureChance = treasureChance;
    }

    /**
     * A setter method for the chance of finding a treasure in a ZERO cell
     * @param treasureChance the chance in 0-1 value
     */
    public void setTreasureChance(float treasureChance) {
        this.treasureChance = treasureChance;
    }

    /**
     * A getter method for the chance of finding a treasure in a ZERO cell
     * @return the chance in 0-1 value
     */
    public float getTreasureChance() {
        return this.treasureChance;
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
