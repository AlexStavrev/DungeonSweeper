package ui;

import ui.custom.ChoiceButton;
import ui.custom.Slider;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class SettingsUI extends JPanel {
    // The parent frame showing this panel
    private final MainUI parent;
    // The slider for the music volume
    private JSlider musicSlider;
    // The slider for the effects volume
    JSlider effectsSlider;
    // The music choice button
    ChoiceButton<String> musicChoiceButton;
    // The screen shoice button
    ChoiceButton<Integer> screenChoiceButton;
    // A list to keep track of changes and save only them
    HashMap<Component, Object> initialSettings;

    /**
     * Standard Constructor for the class
     * @param parent the main UI
     */
    public SettingsUI(MainUI parent) {
        this.parent = parent;
        initialSettings = new HashMap<>();
        initComponents();
    }

    /**
     * A method to initialize the gui components and other elements
     */
    private void initComponents() {
        //======== this ========
        GridBagLayout gbl_settingsUI = new GridBagLayout();
        gbl_settingsUI.columnWidths = new int[]{50, 0, 0, 50};
        gbl_settingsUI.rowHeights = new int[]{50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 50};
        gbl_settingsUI.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
        gbl_settingsUI.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0};
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
            initialSettings.put(musicSlider, musicSlider.getValue());

            //---- effectsSlider ----
            effectsSlider = new Slider();
            effectsSlider.setOpaque(false);
            ((Slider)effectsSlider).setFilledColor(new Color(183, 0, 0));
            ((Slider)effectsSlider).setEmptyColor(Color.LIGHT_GRAY);
            ((Slider)effectsSlider).setBorderColor(new Color(27,27,35));
            add(effectsSlider, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.EAST, GridBagConstraints.BOTH,
                    new Insets(0, 20, 20, 20), 0, 0));
            initialSettings.put(effectsSlider, effectsSlider.getValue());
        }

        //---- effectsLabel ----
        JLabel effectsLabel = new JLabel("Effects Volume");
        effectsLabel.setFont(new Font("Arial", Font.BOLD, 40));
        effectsLabel.setForeground(Color.WHITE);
        add(effectsLabel, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));

        //---- musicChoiceButton ----
        musicChoiceButton = new ChoiceButton<>();
        musicChoiceButton.addChoice("music");
        musicChoiceButton.addChoice("music2");
        add(musicChoiceButton, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));
        initialSettings.put(musicChoiceButton, musicChoiceButton.getSelectedChoice());
        musicChoiceButton.selectChoiceByIndex(0);
        musicChoiceButton.setOpaque(false);
        formatElement(musicChoiceButton.getChoiceLabel(), new Color(82, 82, 82));
        formatElement(musicChoiceButton.getLeftArrowButton(), new Color(82, 82, 82));
        formatElement(musicChoiceButton.getRightArrowButton(), new Color(82, 82, 82));

        //---- screenChoiceButton ----
        screenChoiceButton = new ChoiceButton<>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        for (int i = 0; i < gs.length; i++) {
            screenChoiceButton.addChoice(i);
        }
        add(screenChoiceButton, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 20, 5, 0), 0, 0));
        initialSettings.put(screenChoiceButton, screenChoiceButton.getSelectedChoice());
        screenChoiceButton.selectChoiceByIndex(0);
        screenChoiceButton.setOpaque(false);
        formatElement(screenChoiceButton.getChoiceLabel(), new Color(82, 82, 82));
        formatElement(screenChoiceButton.getLeftArrowButton(), new Color(82, 82, 82));
        formatElement(screenChoiceButton.getRightArrowButton(), new Color(82, 82, 82));

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
        add(buttonsPanel, new GridBagConstraints(1, 9, 2, 1, 0.0, 0.0,
                GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 80, 5, 40), 0, 0));
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
        if(!initialSettings.get(musicSlider).equals(musicSlider.getValue())) {
            initialSettings.put(musicSlider, musicSlider.getValue());
            Utils.getInstance().setMusicVolume(musicSlider.getValue());
        }
        if(!initialSettings.get(effectsSlider).equals(effectsSlider.getValue())) {
            initialSettings.put(effectsSlider, effectsSlider.getValue());
            Utils.getInstance().setEffectsVolume(effectsSlider.getValue());
        }
        if(!initialSettings.get(musicChoiceButton).equals(musicChoiceButton.getSelectedChoice())) {
            initialSettings.put(musicChoiceButton, musicChoiceButton.getSelectedChoice());
            Utils.getInstance().playMusic(musicChoiceButton.getSelectedChoice());
        }
        if(!initialSettings.get(screenChoiceButton).equals(screenChoiceButton.getSelectedChoice())) {
            initialSettings.put(screenChoiceButton, screenChoiceButton.getSelectedChoice());
            parent.showOnScreen(screenChoiceButton.getSelectedChoice());
        }
    }
}
