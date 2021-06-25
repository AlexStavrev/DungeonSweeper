package ui.custom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the custom choice button UI element
 * Shows an option as a string using #toString() and it can
 * be switched to next/previous using the right/left arrows
 * @param <T> the type of the held choices
 */
public class ChoiceButton<T> extends JPanel {
    // A list of all the choices
    private List<T> choiceList;
    // An index to keep track of the selected choice
    int selectedChoiceIndex;
    // The JLabel showing the selected choice as string
    JLabel choiceLabel;
    // The left arrow button
    JButton leftArrow;
    // The right arrow button
    JButton rightArrow;

    /**
     * A standard constructor
     */
    public ChoiceButton() {
        choiceList = new ArrayList<>();
        init();
        selectChoiceByIndex(0);
    }

    /**
     * A method to initialise the components in the JPanel
     */
    private void init() {
        //======== main page ========
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 261, 0};
        gbl_contentPane.rowHeights = new int[]{0};
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0};
        gbl_contentPane.rowWeights = new double[]{1.0};
        this.setLayout(gbl_contentPane);

        //---- leftArrow ----
        leftArrow = new JButton("◀");
        formatElement(leftArrow);
        leftArrow.addActionListener(e -> selectPrevious());
        add(leftArrow, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));

        //---- choiceLabel ----
        choiceLabel = new JLabel();
        formatElement(choiceLabel);
        choiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(choiceLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));

        //---- rightArrow ----
        rightArrow = new JButton("▶");
        formatElement(rightArrow);
        rightArrow.addActionListener(e -> selectNext());
        add(rightArrow, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 0, 20, 0), 0, 0));
    }

    /**
     * A method to select the next choice
     */
    public void selectNext() {
        setSelectedChoiceIndex((++selectedChoiceIndex < choiceList.size()) ? selectedChoiceIndex : 0);
        choiceLabel.setText(choiceList.get(selectedChoiceIndex).toString());
    }

    /**
     * A method to select the previous choice
     */
    public void selectPrevious() {
        setSelectedChoiceIndex((--selectedChoiceIndex < 0) ? choiceList.size()-1 : selectedChoiceIndex);
        choiceLabel.setText(choiceList.get(selectedChoiceIndex).toString());
    }

    /**
     * A method to directly select a choice
     * @param choice choice to select
     */
    public void selectChoice(T choice) {
        if(choiceList.contains(choice)) {
            setSelectedChoiceIndex(choiceList.indexOf(choice));
            choiceLabel.setText(choice.toString());
        } else {
            throw new IllegalArgumentException("Choice is not contained in the list!");
        }
    }

    /**
     * A method to select choice by index
     * @param index index of the choice
     */
    public void selectChoiceByIndex(int index) {
        if(choiceList.size() > index && index >= 0) {
            setSelectedChoiceIndex(index);
            choiceLabel.setText(choiceList.get(index).toString());
        }
    }

    /**
     * A method to format an element
     * @param element the component
     */
    private void formatElement(JComponent element) {
        element.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
        element.setOpaque(true);
        element.setFocusable(false);
        element.setForeground(getForeground());
        element.setFont(getFont());
        element.setBackground(getBackground());
    }

    /**
     * A setter method for the choice list
     * @param choices list of choices
     */
    public void setChoiceList(List<T> choices) {
        this.choiceList = choices;
    }

    /**
     * A method to add a new choice
     * @param choice new choice
     */
    public void addChoice(T choice) {
        this.choiceList.add(choice);
    }

    /**
     * A gether method for the choices
     * @return choice list
     */
    public List<T> getChoices() {
        return this.choiceList;
    }

    public T getSelectedChoice() {
        return choiceList.get(selectedChoiceIndex);
    }

    /**
     * An internal setter for the selected index
     * @param index
     */
    private void setSelectedChoiceIndex(int index) {
        this.selectedChoiceIndex = index;
    }

    /**
     * A method to get the index of the selected choice
     * @return index
     */
    public int getSelectedChoiceIndex() {
        return this.selectedChoiceIndex;
    }

    /**
     * A getter method for the left arrow button
     * @return left arrow button
     */
    public JButton getLeftArrowButton() {
        return leftArrow;
    }

    /**
     * A getter method for the right arrow button
     * @return right arrow button
     */
    public JButton getRightArrowButton() {
        return rightArrow;
    }

    public JLabel getChoiceLabel() {
        return choiceLabel;
    }
}
