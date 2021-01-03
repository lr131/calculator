package ru.geekbrains.java.level1.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperatorsButtonListener implements ActionListener {
    private final JTextField textField;

    public OperatorsButtonListener(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.getText();

        StringBuilder stringBuilder = new StringBuilder(textField.getText());
        stringBuilder.append(button.getText());
        //TODO если последний символ - операнд, то его надо заменять
        textField.setText(stringBuilder.toString());
    }
}
