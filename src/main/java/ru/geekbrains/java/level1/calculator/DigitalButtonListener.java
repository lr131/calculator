package ru.geekbrains.java.level1.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DigitalButtonListener implements ActionListener {
    private final JTextField textField;

    public DigitalButtonListener(JTextField textField) {
        this.textField = textField;
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        button.getText();

        StringBuilder stringBuilder = new StringBuilder(textField.getText());
        stringBuilder.append(button.getText());
        textField.setText(stringBuilder.toString());
    }
}
