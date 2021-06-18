package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class ExperimentalUI extends JFrame {

    // The default dark color used for e.g. on the form's background
    private final Color defaultColor;
    // Default button bg color
    private final Color buttonBackground;
    // Hover bg color for a button
    private final Color buttonHoverBackground;
    // Hover fg color for a button
    private final Color buttonHoverForeground;

    /**
     * The main method for this frame
     * @param args default
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame mainFrame = new ExperimentalUI();
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
     */
    public ExperimentalUI() {
        defaultColor = new Color(27, 27, 35);
        buttonBackground = new Color(82, 82, 82);
        buttonHoverBackground = new Color(60, 60, 60);
        buttonHoverForeground = new Color(200, 200, 200);
        initComponents();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        //======== this ========
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0,0,0, 0};
        gbl_contentPane.rowHeights = new int[]{60, 0, 0, 0, 0, 60};
        gbl_contentPane.columnWeights = new double[]{0.7, 0.0,0.3,0.0, 0.7};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
        contentPane.setLayout(gbl_contentPane);
        contentPane.setBackground(defaultColor);

        //---- playButton ----
        JButton playButton = new JButton("PLAY");
        formatElement(playButton, buttonBackground);
        playButton.addActionListener(e -> GameForm.main(null));
        contentPane.add(playButton, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(40, 0, 20, 0), 0, 0));
        makeHoverable(playButton, buttonHoverBackground, buttonHoverForeground);

        //---- leftArrow ----
        JButton leftArrow = new JButton("◀");
        formatElement(leftArrow, buttonBackground);
        contentPane.add(leftArrow, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(leftArrow, buttonHoverBackground, buttonHoverForeground);

        //---- difficultyLabel ----
        JLabel difficultyLabel = new JLabel("EASY");
        formatElement(difficultyLabel, buttonBackground);
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(difficultyLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));

        //---- rightArrow ----
        JButton rightArrow = new JButton("▶");
        formatElement(rightArrow, buttonBackground);
        contentPane.add(rightArrow, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(rightArrow, buttonHoverBackground, buttonHoverForeground);

        //---- creditsButton ----
        JButton creditsButton = new JButton("CREDITS");
        formatElement(creditsButton, buttonBackground);
        creditsButton.addActionListener(e -> System.out.println("credits"));
        contentPane.add(creditsButton, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(creditsButton, buttonHoverBackground, buttonHoverForeground);

        //---- exitButton ----
        JButton exitButton = new JButton("EXIT");
        formatElement(exitButton, new Color(183, 0, 0));
        exitButton.addActionListener(e -> dispose());
        contentPane.add(exitButton, new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
        makeHoverable(exitButton, new Color(140, 0, 0), buttonHoverForeground);

        setLocationRelativeTo(getOwner());
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

    /**
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
    public class NoHighlightButtonModel extends DefaultButtonModel    {
        @Override
        public boolean isPressed() {return false;}
        @Override
        public boolean isRollover() {return false;}
        @Override
        public void setRollover(boolean b) {}
    }
}