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
import java.net.URL;

/**
 * A UI Class for the application's main game window
 * @author WrexBG
 */
public class GameForm extends JFrame {
    // The difficulty of the game
    Difficulty difficulty;
    GameController gameController;
    // The button for the image in the top center
    JButton imageButton;
    // The panel where the cells are shown
    JPanel gamePanel;
    // The label showing the score
    JLabel scoreLabel;
    // The game timer
    JTimer timer;

    /**
     * The main method for this frame
     * @param args default
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame mainFrame = new GameForm(new Difficulty("Easy",10, 9, 9));
                mainFrame.setResizable(false);
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setTitle("Dungeon Sweeper");
                URL url = mainFrame.getClass().getResource("images/icon.png");
                ImageIcon icon = new ImageIcon(url);
                mainFrame.setIconImage(icon.getImage());
                // Remove the system's scale factor on the UI elements
                System.setProperty("sun.java2d.uiScale", "1.0");
                // Centres the dialog
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Standard constructor for this class
     * @param difficulty the amount of mines
     */
    public GameForm(Difficulty difficulty) {
        this.difficulty = difficulty;
        initComponents();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        gameController = new GameController(difficulty) {
            @Override
            public int getTotalSeconds() {
                return timer.getTotalSeconds();
            }
        };
        //======== this ========
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 30, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0};
        gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        contentPane.setBackground(new Color(27, 27, 35));

        //---- timeLabel ----
        timer = new JTimer(5, 0) {
            @Override
            public void done() {
                endGame();
            }
        };
        timer.setFont(new Font("Arial", Font.BOLD, 16));
        timer.setForeground(new Color(170, 0, 30));
        timer.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(timer, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 20, 10, 20), 0, 0));
        timer.startTimer();

        //---- imageButton ----
        imageButton = new JButton(Utils.getInstance().resizedImage("images/alive.jpg", 60, 60));
        imageButton.setBorderPainted(false);
        imageButton.setBorder(BorderFactory.createEmptyBorder());
        imageButton.setContentAreaFilled(false);
        imageButton.setOpaque(true);
        imageButton.addActionListener(e -> {
            if(gameController.getGame().getState() == GameState.LOST || gameController.getGame().getState() == GameState.WON) {
                initComponents();
            }
        });
        contentPane.add(imageButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));

        //---- scoreLabel ----
        scoreLabel = new JLabel("0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(170, 0, 30));
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(scoreLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(10, 20, 10, 20), 0, 0));

        //---- gamePanel ---- TODO This is temp so change it
        gamePanel = new JPanel();
        contentPane.add(gamePanel, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));

        FlowLayout fl_gamePanel = new FlowLayout();
        fl_gamePanel.setHgap(0);
        fl_gamePanel.setVgap(0);
        gamePanel.setLayout(fl_gamePanel);
        gamePanel.setPreferredSize(new Dimension(Utils.TILE_SIZE * difficulty.getColumns(), Utils.TILE_SIZE * difficulty.getRows()));
        gameController.startGame();
        GameGrid grid = gameController.getGame().getGrid();
        for (int y = 0; y < difficulty.getRows(); y++) {
            for (int x = 0; x < difficulty.getColumns(); x++) {
                Cell cell = grid.getCellAt(x, y);
                UICell button = new UICell(cell, Utils.getInstance().getIconForValue(cell.getValue()), gameController);
                formatCellButtonAt(button);
                gamePanel.add(button);
            }
        }
        pack();
        setLocationRelativeTo(getOwner());
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
                endGame();
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
        if(gameController.getGame().getState() == GameState.LOST) {
            Utils.getInstance().playSound("loose");
        }
        imageButton.setIcon(Utils.getInstance().resizedImage("images/dead.jpg", 60, 60));
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
    private void winGame() {
        endGame();
        Utils.getInstance().playSound("win");
        imageButton.setIcon(Utils.getInstance().resizedImage("images/won.jpg", 60, 60));
    }

    /**
     * A method to update all cells on the board
     */
    private void updateBoard() {
        EventQueue.invokeLater(() -> {
            for (Component cell : gamePanel.getComponents()) {
                if (cell instanceof UICell cellButton) {
                    if (cellButton.getCell().isRevealed()) {
                        cellButton.setIcon(cellButton.getCellIcon());
                    }
                }
            }
        });
    }
}
