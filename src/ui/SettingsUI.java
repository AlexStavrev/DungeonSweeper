package ui;

import ui.custom.Slider;

import javax.swing.*;
import java.awt.*;

public class SettingsUI extends JPanel {
    // The parent frame showing this panel
    private final MainUI parent;
    // The slider for the music volume
    private JSlider musicSlider;
    // The slider for the effects volume
    JSlider effectsSlider;

    /**
     * Standard Constructor for the class
     * @param parent the main UI
     */
    public SettingsUI(MainUI parent) {
        this.parent = parent;
        initComponents();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        //======== this ========
        GridBagLayout gbl_settingsUI = new GridBagLayout();
        gbl_settingsUI.columnWidths = new int[]{50, 0, 0, 50};
        gbl_settingsUI.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 50};
        gbl_settingsUI.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
        gbl_settingsUI.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
        setLayout(gbl_settingsUI);
        setBackground(new Color(27, 27, 35));

        //---- settingsLabel ----
        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 50));
        settingsLabel.setForeground(Color.WHITE);
        add(settingsLabel, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 20, 0), 0, 0));

        //---- musicLabel ----
        JLabel musicLabel = new JLabel("Music Volume");
        musicLabel.setFont(new Font("Arial", Font.BOLD, 40));
        musicLabel.setForeground(Color.WHITE);
        add(musicLabel, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));
        {
            //---- musicSlider ----
            musicSlider = new Slider();
            musicSlider.setOpaque(false);
            ((Slider)musicSlider).setFilledColor(new Color(183, 0, 0));
            ((Slider)musicSlider).setEmptyColor(Color.LIGHT_GRAY);
            ((Slider)musicSlider).setBorderColor(new Color(27,27,35));
            add(musicSlider, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.BOTH,
                    new Insets(0, 20, 20, 20), 0, 0));

            //---- effectsSlider ----
            effectsSlider = new Slider();
            effectsSlider.setOpaque(false);
            ((Slider)effectsSlider).setFilledColor(new Color(183, 0, 0));
            ((Slider)effectsSlider).setEmptyColor(Color.LIGHT_GRAY);
            ((Slider)effectsSlider).setBorderColor(new Color(27,27,35));
            add(effectsSlider, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.BOTH,
                    new Insets(0, 20, 20, 20), 0, 0));
        }

        //---- effectsLabel ----
        JLabel effectsLabel = new JLabel("Effects Volume");
        effectsLabel.setFont(new Font("Arial", Font.BOLD, 40));
        effectsLabel.setForeground(Color.WHITE);
        add(effectsLabel, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));

        //======== buttonsPanel ========
        JPanel buttonsPanel = new JPanel();
        GridBagLayout gbl_buttonsPanel = new GridBagLayout();
        gbl_buttonsPanel.columnWidths = new int[]{0, 0};
        gbl_buttonsPanel.rowHeights = new int[]{0};
        gbl_buttonsPanel.columnWeights = new double[]{1.0, 1.0};
        gbl_buttonsPanel.rowWeights = new double[]{1.0};
        buttonsPanel.setLayout(gbl_buttonsPanel);
        buttonsPanel.setBackground(new Color(27, 27, 35));
        {
            //---- saveButton ----
            JButton saveButton = new JButton("SAVE");
            formatElement(saveButton, new Color(0, 183, 0));
            saveButton.addActionListener(e -> saveSettings());
            buttonsPanel.add(saveButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 40, 5, 80), 0, 0));

            //---- backButton ----
            JButton backButton = new JButton("BACK");
            formatElement(backButton, new Color(183, 0, 0));
            backButton.addActionListener(e -> parent.selectPage("mainPanel"));
            buttonsPanel.add(backButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 20, 5, 0), 0, 0));
        }
        add(buttonsPanel, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 00, 5, 40), 0, 0));
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

    private void saveSettings() {
        Utils.getInstance().setMusicVolume(musicSlider.getValue());
        Utils.getInstance().setEffectsVolume(effectsSlider.getValue());
    }
}
