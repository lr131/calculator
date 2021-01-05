package ru.geekbrains.java.level1.calculator;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ButtonListener implements ActionListener {
    private final JTextField textField;
    private static final String DIGITS = "0123456789";
    private static final String SQRT = "√";
    private static final String POWER = "^";

    public ButtonListener(JTextField textField) {
        this.textField = textField;
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String currentButtonValue = button.getText();
        StringBuilder stringBuilder = new StringBuilder(textField.getText());
        String lastChar;

        if (DIGITS.contains(currentButtonValue)) {
            //если ввели цифру
            stringBuilder.append(button.getText());
            textField.setText(stringBuilder.toString());
        } else if (currentButtonValue.equals("C")) {
            stringBuilder.setLength(0); //очищаем stringBuilder
            textField.setText(stringBuilder.toString());
        } else if (currentButtonValue.equals("=")) {
            String result = calculate(textField.getText());
            textField.setText(result);
        } else {
//            если это оператор (не корень) или точка и строка ввода была пустая
            if (stringBuilder.length() == 0) {
                if (!currentButtonValue.equals(SQRT)) {
                    stringBuilder.append("0" + currentButtonValue);
                } else {
                    stringBuilder.append(currentButtonValue);
                }
                textField.setText(stringBuilder.toString());
            } else {
                // сохраняем последний символ строки
                lastChar = Character.toString(
                        stringBuilder.charAt(stringBuilder.length() - 1)
                );
                //если ввели точку
                if (currentButtonValue.equals(".")) {
                    //если точка, то ввести можно только один раз за число
                    //предыдущим значением может быть только цифра
                    if (DIGITS.contains(lastChar) &&
                            (!(hasDotLastNumber(textField.getText())))) {
                        stringBuilder.append(button.getText());
                    }
                }

                //после точки может идти только цифра
                if (currentButtonValue.equals("-")) {
                    if ("-+.√".contains(lastChar)) {
                        //минус заменяет значения: -, +, ., sqrt
                        stringBuilder.setLength(stringBuilder.length() - 1);
                    }
                    stringBuilder.append(button.getText());
                }
                if (currentButtonValue.equals(SQRT)){
                    //не будет ввода, если не в начале строки, т.к. нельзя
                    // учесть приоритет вычисления корня
                }
                if (currentButtonValue.contains(POWER)) {
                    //выражение трикстерное, т.к. тоже не учитывается приоритет.
                    //это значит, что 2^3 = 8 и 1+1^3 тоже будет 8
                    //И это значит, что перед знаком степени обязательно должна быть цифра
                    //в противном случае ввод не совершается
                    if (DIGITS.contains(lastChar)) {
                        stringBuilder.append(button.getText());
                    }
                }
                if (("+*/").contains(currentButtonValue)) {
                    if (!DIGITS.contains(lastChar)) {
                        //минус может стоять перед степенью. Значит, если заменяется
                        // ТАКОЙ: "8^-" "минус", то заменяться должен и знак степени
                        String preLastChar = Character.toString(
                                stringBuilder.charAt(stringBuilder.length() - 2)
                        );
                        if (preLastChar.equals(POWER)) {
                            stringBuilder.setLength(stringBuilder.length() - 2);
                        } else {
                            stringBuilder.setLength(stringBuilder.length() - 1);
                        }
                    }
                    stringBuilder.append(button.getText());
                }
            }
            textField.setText(stringBuilder.toString());
        }
    }

    private String calculate(String expression){

        //разбиение
        Pattern pattern = Pattern.compile("(-*\\d+\\.*\\d*)");
        Matcher matcher = pattern.matcher(expression);
        List<String> numbers = new ArrayList<>();
        List<Integer> startIndexes = new ArrayList<>();
        while(matcher.find()) {
            numbers.add(matcher.group());
            startIndexes.add(matcher.start());
        }

        //вычисление без учета приоритета
        double res = isDouble(numbers.get(0)) ?
                Double.parseDouble(numbers.get(0)) : Integer.parseInt(numbers.get(0));
        //проверяем, есть ли в начале квадратный корень
        if (expression.charAt(0) == SQRT.charAt(0)) {
            res = Math.sqrt(res);
        }
        if (numbers.size() > 1) {
            for (int i = 1; i < numbers.size(); i++) {
                double currentNumber = isDouble(numbers.get(i)) ?
                        Double.parseDouble(numbers.get(i)) : Integer.parseInt(numbers.get(i));
                try {
                    res = compute(res, currentNumber, expression, startIndexes.get(i)-1);
                } catch (ArithmeticException e) {
                    return "Division by zero!";
                }
            }
        }

        return String.valueOf(res);
    }

    private boolean hasDotLastNumber(String expression){
        //в этом методе не важно, какой у числа знак:
        String[] strArr = expression.split("[\\+\\-*/]");
        return strArr[strArr.length - 1].contains(".");
    }

    private boolean isDouble(String value) {
        return value.contains(".");
    }

    private double compute(double a, double b, String expression, int indexOperator) {
        char currentOperation = expression.charAt(indexOperator);
        if (currentOperation == '+') {
            return a + b;
        } else if (currentOperation == '*') {
            return a * b;
        } else if (currentOperation == '/') {
            return a / b;
        } else if (currentOperation == POWER.charAt(0)) {
            return Math.pow(a,b);
        }

        //в этот блок попадаем, если стоит отрицание, т.е. выражение вида
        // 55-44
        //распарсится оно как а = 55 и и = -44 (отрицательное число), поэтому
        //просто складываем числа и возвращаем результат
        return a + b;
    }
}
