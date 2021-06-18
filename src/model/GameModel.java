package model;

import model.enums.GameState;

/**
 * An interface to define a game model
 *
 * @author WrexBG
 */
public interface GameModel {
    // Get or set the difficulty
    void setDifficulty(Difficulty difficulty);
    Difficulty getDifficulty();

    // Get or set the has finished condition of a game
    void setFinished(boolean hasFinished);
    boolean hasFinished();

    // Get or set the game state of a game
    void setState(GameState state);
    GameState getState();

    // Get or set the grid of a game
    void setGrid(GameGrid grid);
    GameGrid getGrid();
}
