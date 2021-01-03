package ru.geekbrains.java.level1.calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculationListener implements ActionListener {
    private final JTextField textField;

    public CalculationListener(JTextField textField) {
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String expression = textField.getText();
        int result = 0;
        String[] operators;
        //TODO с отрицательными числами проработать
        if (expression.contains("+")) {
            operators = expression.split("\\+");
            result = Integer.parseInt(operators[0]) +
                    Integer.parseInt(operators[1]);
        } else if (expression.contains("-")) {
            operators = expression.split("-");
            result = Integer.parseInt(operators[0]) -
                    Integer.parseInt(operators[1]);
        } else if (expression.contains("*")) {
            operators = expression.split("\\*");
            result = Integer.parseInt(operators[0]) *
                    Integer.parseInt(operators[1]);
        }
        textField.setText(String.valueOf(result));
    }
}
