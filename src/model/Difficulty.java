package model;

/**
 * A class to represent the difficulty of a {@link Game}
 */
public class Difficulty implements DifficultyModel {

    // The amount of mines
    private int amountOfMines;
    // The amount of columns
    private int columns;
    // The amount of rows
    private int rows;
    // The name of the difficulty
    private String name;
    // The amount of minutes
    private int minutes;
    // The amount of seconds
    private int seconds;
    // The score multiplier
    private float scoreMultiplier;

    /**
     * A standard constructor for the class
     * @param amountOfMines the amount of mines
     * @param columns the amount of columns
     * @param rows the amount of rows
     * @param minutes minutes in the game
     * @param seconds seconds in the game, excluding minutes (0-60)
     * @param scoreMultiplier the score multiplier
     */
    public Difficulty(String name, int amountOfMines, int columns, int rows, int minutes, int seconds, float scoreMultiplier) {
        setName(name);
        setAmountOfMines(amountOfMines);
        setColumns(columns);
        setRows(rows);
        setMinutes(minutes);
        setSeconds(seconds);
        setScoreMultiplier(scoreMultiplier);
    }

    //Interface methods
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setAmountOfMines(int amount) {
        this.amountOfMines = amount;
    }

    @Override
    public int getAmountOfMines() {
        return amountOfMines;
    }

    @Override
    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int getSeconds() {
        return seconds;
    }

    @Override
    public float getScoreMultiplier() {
        return scoreMultiplier;
    }

    @Override
    public void setScoreMultiplier(float scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    @Override
    public String toString() {
        return getName();
    }
}
