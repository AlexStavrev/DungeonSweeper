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

    /**
     * A standard constructor for the class
     * @param amountOfMines the amount of mines
     * @param columns the amount of columns
     * @param rows the amount of rows
     */
    public Difficulty(String name, int amountOfMines, int columns, int rows) {
        setName(name);
        setAmountOfMines(amountOfMines);
        setColumns(columns);
        setRows(rows);
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
}
