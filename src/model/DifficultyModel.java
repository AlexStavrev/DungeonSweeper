package model;

/**
 * An interface for the {@link Difficulty} class
 */
public interface DifficultyModel {
    // Get or set the name
    void setName(String name);
    String getName();

    // Get or set the amount of mines in a game
    void setAmountOfMines(int amount);
    int getAmountOfMines();

    // Get or set the amount of columns
    void setColumns(int columns);
    int getColumns();

    // Get or set the amount of rows
    void setRows(int rows);
    int getRows();

    // Get or set the amount of rows
    void setMinutes(int minutes);
    int getMinutes();

    // Get or set the amount of rows
    void setSeconds(int seconds);
    int getSeconds();

    // Get or set the score multiplier
    void setScoreMultiplier(float soreMultiplier);
    float getScoreMultiplier();
}
