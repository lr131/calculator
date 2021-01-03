package ru.geekbrains.java.level1.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private JTextField textField;
    public Calculator(){
        initMandatoryComponents();
        setTitle("Calculator");
        setBounds(0,0,300,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(initTopTextField(), BorderLayout.NORTH);
        add(initBottom(), BorderLayout.CENTER);

//        setLayout(new GridBagLayout());
//        add(initTopTextField());
//        add(initBottom());

        setVisible(true);
    }

    private void initMandatoryComponents(){
        textField = new JTextField();
    }

    private JPanel initTopTextField(){
        JPanel topTextField = new JPanel();
        topTextField.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);
        topTextField.add(textField, BorderLayout.CENTER);

        return topTextField;
    }

    private JPanel initBottom(){
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(5, 4));

        ActionListener digitButtonListener = new DigitalButtonListener(textField);
        ActionListener operatorsButtonListener =
                new OperatorsButtonListener(textField);

        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.addActionListener(digitButtonListener);
            bottom.add(button);
        }

        JButton submit = new JButton("=");
        JButton zero = new JButton("0");
        JButton clear = new JButton("C");
        JButton plus = new JButton("+");
        JButton minus = new JButton("-");
        JButton multiply = new JButton("*");

        zero.addActionListener(digitButtonListener);
        plus.addActionListener(operatorsButtonListener);
        minus.addActionListener(operatorsButtonListener);
        multiply.addActionListener(operatorsButtonListener);
//        clear.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                textField.setText("");
//            }
//        }); // аналогичная короткая запись:
        clear.addActionListener(e -> textField.setText(""));

        submit.addActionListener(new CalculationListener(textField));

        bottom.add(submit);
        bottom.add(zero);
        bottom.add(clear);
        bottom.add(plus);
        bottom.add(minus);
        bottom.add(multiply);

        return bottom;
    }

}
