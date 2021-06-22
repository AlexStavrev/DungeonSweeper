package ui;

import controller.GameController;
import model.Cell;
import model.Difficulty;
import model.GameGrid;
import model.enums.GameState;
import model.enums.Value;
import ui.custom.JTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GameUI extends JPanel {

    private final MainUI parent;

    private Difficulty difficulty;

    private GameController gameController;

    private JLabel scoreLabel;
    private JTimer timer;
    private JButton imageButton;
    private JPanel gamePanel;

    /**
     * Standard Constructor for the class
     * @param difficulty the amount of mines
     */
    public GameUI(MainUI parent, Difficulty difficulty) {
        this.parent = parent;
        this.difficulty = difficulty;
        initComponents();
        initGameBoard();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        removeAll();
        //======== this ========
        GridBagLayout gbl_gameUI = new GridBagLayout();
        gbl_gameUI.columnWidths = new int[]{100, 0, 0, 0, 60};
        gbl_gameUI.rowHeights = new int[]{0, 0, 0};
        gbl_gameUI.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0};
        gbl_gameUI.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        setLayout(gbl_gameUI);
        setBackground(new Color(27, 27, 35));

        //---- time ----
        timer = new JTimer(difficulty.getMinutes(), difficulty.getSeconds());
        timer.addFinishAction(() -> {
            gameController.getGame().setState(GameState.LOST);
            looseGame();
        });
        timer.setFont(new Font("Arial", Font.BOLD, 20));
        timer.setForeground(Color.WHITE);
        timer.setHorizontalAlignment(SwingConstants.LEFT);
        add(timer, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(15, 20, 0, 0), 0, 0));
        timer.startTimer();

        //---- scoreLabel ----
        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setForeground(Color.WHITE);
        add(scoreLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));

        //---- imageButton ----
        imageButton = new JButton(Utils.getInstance().getImage("alive"));
        imageButton.setBorderPainted(false);
        imageButton.setBorder(BorderFactory.createEmptyBorder());
        imageButton.setContentAreaFilled(false);
        imageButton.setBackground(new Color(27, 27, 35));
        imageButton.setOpaque(true);
        imageButton.addActionListener(e -> {
            if(gameController.getGame().getState() == GameState.LOST || gameController.getGame().getState() == GameState.WON) {
                initComponents();
                initGameBoard();
            }
        });
        add(imageButton, new GridBagConstraints(0, 0, 5, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                new Insets(15, 0, 5, 0), 0, 0));

        //---- pauseButton ----
        JButton pauseButton = new JButton("▐ ▌");
        pauseButton.setFocusable(false);
        pauseButton.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        pauseButton.setBackground(new Color(80, 80, 80));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFont(new Font("Arial",Font.BOLD, 20));
        pauseButton.setOpaque(true);
        pauseButton.addActionListener(e -> {
            timer.pause();
            parent.selectPage("mainPanel");
        });
        add(pauseButton, new GridBagConstraints(4, 0, 1, 2, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
    }

    private void initGameBoard() {
        gameController = new GameController(difficulty) {
            @Override
            public int getTotalSeconds() {return timer.getTotalSeconds();}
        };

        gamePanel = new JPanel();
        gamePanel.setVisible(false);
        gamePanel.removeAll();
        gamePanel.setBackground(new Color(27, 27, 35));
        add(gamePanel, new GridBagConstraints(0, 2, 5, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        GridBagLayout gbl_gamePanel = new GridBagLayout();
        gbl_gamePanel.columnWidths = new int[difficulty.getColumns()];
        gbl_gamePanel.rowHeights = new int[difficulty.getRows()];
        gamePanel.setLayout(gbl_gamePanel);

        gamePanel.setPreferredSize(new Dimension(Utils.TILE_SIZE * difficulty.getColumns(), Utils.TILE_SIZE * difficulty.getRows()));
        gamePanel.setMaximumSize(new Dimension(Utils.TILE_SIZE * difficulty.getColumns(), Utils.TILE_SIZE * difficulty.getRows()));
        gameController.startGame();
        GameGrid grid = gameController.getGame().getGrid();

        SwingWorker loadBoard = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                for (int y = 0; y < difficulty.getRows(); y++) {
                    for (int x = 0; x < difficulty.getColumns(); x++) {
                        Cell cell = grid.getCellAt(x, y);
                        UICell button = new UICell(cell, Utils.getInstance().getIconForValue(cell.getValue()), gameController);
                        button.setBorder(BorderFactory.createEmptyBorder());
                        gamePanel.add(button, new GridBagConstraints(x, y, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        formatCellButtonAt(button);
                    }
                }
                return null;
            }
            @Override
            public void done() {
                gamePanel.setVisible(true);
            }
        };
        loadBoard.execute();
    }

    /**
     * A method to set the formatting of the {@link UICell} elements
     * @param cellButton the cellButton
     */
    private void formatCellButtonAt(UICell cellButton) {
        cellButton.addActionListener(e -> {
            Cell cell = cellButton.getCell();
            if (!cell.isRevealed() && !cell.isFlagged()) {
                Utils.getInstance().playSound("break");
            }
            cellButton.reveal();
            if (cell.getValue().isABomb() && !cell.isFlagged()) {
                looseGame();
            } else {
                if (gameController.getGame().getState() == GameState.WON) {
                    winGame();
                }
                if (cell.getValue() == Value.ZERO) {
                    updateBoard();
                }
            }
            scoreLabel.setText(Integer.valueOf(gameController.getScore()).toString());
        });

        cellButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    cellButton.flag();
                }
            }
        });
    }

    /**
     * A method to end the game
     */
    private void endGame() {
        timer.pause();
        for (Component cell : gamePanel.getComponents()) {
            if (cell instanceof UICell cellButton) {
                if (cellButton.getActionListeners().length > 0) {
                    cellButton.removeActionListener(cellButton.getActionListeners()[0]);
                }
                if (cellButton.getCell().getValue().isABomb()) {
                    cellButton.reveal();
                }
            }
        }
    }

    /**
     * A method to win the game
     */
    private void looseGame() {
        endGame();
        Utils.getInstance().playSound("loose");
        imageButton.setIcon(Utils.getInstance().getImage("dead"));
    }

    /**
     * A method to win the game
     */
    private void winGame() {
        endGame();
        Utils.getInstance().playSound("win");
        imageButton.setIcon(Utils.getInstance().getImage("won"));
    }

    /**
     * A method to update all cells on the board
     */
    private void updateBoard() {
        for (Component cell : gamePanel.getComponents()) {
            if (cell instanceof UICell cellButton) {
                if (cellButton.getCell().isRevealed()) {
                    cellButton.setIcon(cellButton.getCellIcon());
                }
            }
        }
    }
}
