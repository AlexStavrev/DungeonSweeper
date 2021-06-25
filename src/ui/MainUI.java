package ui;

import model.Difficulty;
import ui.custom.ChoiceButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class MainUI extends JFrame {

    // The default dark color used for e.g. on the form's background
    public Color defaultColor;
    // Default button bg color
    public Color buttonBackground;
    // Hover bg color for a button
    public Color buttonHoverBackground;
    // Hover fg color for a button
    public Color buttonHoverForeground;

    //============= UI Purpose Elements =============
    // The main panel
    JPanel contentPane;
    // Cards layout so the UI can switch between pages
    private final CardLayout pages;
    // The page where the game will be played
    private GameUI gamePanel;
    // true if the swing worker has done loading elements
    boolean isSwingWorkerDone;
    // true if the splash screen has done playing
    boolean isSplashScreenDone;

    /**
     * The main method for this frame
     * @param args default
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(laf.getName())) {
                        UIManager.setLookAndFeel(laf.getClassName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                MainUI mainFrame = new MainUI();
                mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mainFrame.setTitle("Dungeon Sweeper");
                URL url = mainFrame.getClass().getResource("images/icon.png");
                ImageIcon icon = new ImageIcon(url);
                mainFrame.setIconImage(icon.getImage());
                // Remove the system's scale factor on the UI elements
                System.setProperty("sun.java2d.uiScale", "1.0");
                System.setProperty("sun.java2d.d3d", "false");
                // Centres the dialog
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mainFrame.setUndecorated(true);
                mainFrame.showOnScreen(0);
                mainFrame.setVisible(true);
                Utils.getInstance().playMusic("music");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Standard constructor for this class
     */
    public MainUI() {
        //======== splash ========
        SplashPanel splash = new SplashPanel();
        splash.addFinishAction(() -> {
            isSplashScreenDone = true;
            selectPage("mainPanel");
        });
        //======== this ========
        contentPane = new JPanel();
        pages = new CardLayout(0, 0);
        contentPane.setLayout(pages);
        contentPane.add(splash, "splashPane");
        contentPane.add(new SettingsUI(this), "settingsPanel");
        setContentPane(contentPane);

        // Swing worker to load the rest in the background while the splash screen is playing
        SwingWorker<Object, Object> initComponentsInBackground = new SwingWorker<>() {
            @Override
            protected Object doInBackground() {
                initComponents();
                return null;
            }
            @Override
            protected void done() {
                isSwingWorkerDone = true;
                selectPage("mainPanel");
            }
        };
        initComponentsInBackground.execute();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        //======= this =========
        defaultColor = new Color(27, 27, 35);
        buttonBackground = new Color(82, 82, 82);
        buttonHoverBackground = new Color(60, 60, 60);
        buttonHoverForeground = new Color(200, 200, 200);

        //======== main page ========
        JPanel mainPanel = new JPanel();
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{60, 0, 0, 0, 0, 0, 60};
        gbl_contentPane.columnWeights = new double[]{0.7, 0.3, 0.7};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
        mainPanel.setLayout(gbl_contentPane);
        mainPanel.setBackground(defaultColor);
        contentPane.add(mainPanel, "mainPanel");

        //---- difficultyChoiceButton ----
        ChoiceButton<Difficulty> difficultyChoiceButton = new ChoiceButton<>();
        difficultyChoiceButton.addChoice(new Difficulty("Easy", 10, 9, 9,  5, 0));
        difficultyChoiceButton.addChoice(new Difficulty("Normal", 30, 15, 10, 7, 0));
        difficultyChoiceButton.addChoice(new Difficulty("Hard", 40, 21, 10, 10, 0));
        mainPanel.add(difficultyChoiceButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        difficultyChoiceButton.selectChoiceByIndex(0);
        difficultyChoiceButton.setOpaque(false);
        formatElement(difficultyChoiceButton.getChoiceLabel(), buttonBackground);
        formatElement(difficultyChoiceButton.getLeftArrowButton(), buttonBackground);
        formatElement(difficultyChoiceButton.getRightArrowButton(), buttonBackground);
        makeHoverable(difficultyChoiceButton.getLeftArrowButton(), buttonHoverBackground, buttonHoverForeground);
        makeHoverable(difficultyChoiceButton.getRightArrowButton(), buttonHoverBackground, buttonHoverForeground);

        //---- playButton ----
        JButton playButton = new JButton("PLAY");
        formatElement(playButton, buttonBackground);
        playButton.addActionListener(e -> {
            Utils.getInstance().playSound("click");
            if(gamePanel != null) {
                contentPane.remove(gamePanel);
            }
            gamePanel = new GameUI(this, difficultyChoiceButton.getSelectedChoice());
            contentPane.add(gamePanel, "gamePanel");
            selectPage("gamePanel");
        });
        mainPanel.add(playButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(40, 0, 20, 0), 0, 0));
        makeHoverable(playButton, buttonHoverBackground, buttonHoverForeground);

        //---- creditsButton ----
        JButton creditsButton = new JButton("CREDITS");
        formatElement(creditsButton, buttonBackground);
        creditsButton.addActionListener(e -> showOnScreen(1));
        mainPanel.add(creditsButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(creditsButton, buttonHoverBackground, buttonHoverForeground);

        //---- settingsButton ----
        JButton settingsButton = new JButton("SETTINGS");
        formatElement(settingsButton, buttonBackground);
        settingsButton.addActionListener(e -> selectPage("settingsPanel"));
        mainPanel.add(settingsButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(settingsButton, buttonHoverBackground, buttonHoverForeground);

        //---- exitButton ----
        JButton exitButton = new JButton("EXIT");
        formatElement(exitButton, new Color(183, 0, 0));
        exitButton.addActionListener(e -> this.dispose());
        mainPanel.add(exitButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(exitButton, new Color(140, 0, 0), buttonHoverForeground);
    }

    /**
     * A method to select a page when using the sidebar buttons
     * @param page name of the page
     */
    public void selectPage(String page) {
        switch (page) {
            case "mainPanel", "gamePanel", "settingsPanel" -> {
                if(isSplashScreenDone && isSwingWorkerDone) {
                    pages.show(contentPane, page);
                }
            }
            case "splashPanel" -> pages.show(contentPane, page);
            default -> throw new IllegalStateException("Unexpected page: " + page);
        }
    }

    /**
     * A method to format an element
     * @param element the component
     * @param color the bg color it should have
     */
    private void formatElement(JComponent element, Color color) {
        element.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        element.setOpaque(true);
        element.setFocusable(false);
        element.setForeground(Color.WHITE);
        element.setFont(new Font("Roboto", Font.BOLD, 50));
        element.setBackground(color);
    }

    /** TODO remake
     * A method to make a JButton hoverable
     * @param element the button
     * @param backgroundColor the hover bg color
     * @param foregroundColor the hover fg color
     */
    private void makeHoverable(JButton element, Color backgroundColor, Color foregroundColor) {
        element.setModel(new NoHighlightButtonModel());
        element.addMouseListener(new MouseAdapter() {
            private final Color backgroundDefault = element.getBackground();
            private final Color foregroundDefault = element.getForeground();
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                element.setBackground(backgroundColor);
                element.setForeground(foregroundColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseEntered(e);
                element.setBackground(backgroundDefault);
                element.setForeground(foregroundDefault);
            }
        });
    }

    /**
     * Used to remove highlight on button click
     */
    public static class NoHighlightButtonModel extends DefaultButtonModel {
        @Override
        public boolean isPressed() {return false;}
        @Override
        public boolean isRollover() {return false;}
        @Override
        public void setRollover(boolean b) {}
    }

    /**
     * A function to show the application on a specific screen
     * @param screen screen's id
     */
    public void showOnScreen(int screen) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        Rectangle screenBounds;
        if(screen > -1 && screen < gs.length) {
            screenBounds = gs[screen].getDefaultConfiguration().getBounds();
        }
        else if(gs.length > 0) {
            screenBounds = gs[0].getDefaultConfiguration().getBounds();
        }
        else {
            throw new RuntimeException( "No Screens Found" );
        }
        this.setLocation(screenBounds.getLocation());
        Utils.getInstance().setUIScale((int)(screenBounds.height/12.3));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}