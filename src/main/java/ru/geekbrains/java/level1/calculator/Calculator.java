package ru.geekbrains.java.level1.calculator;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Calculator extends JFrame {
    private JTextField textField;
    private static final String NOTDIGITTEXTBUTTONS = "+-*/=C.";
    private static final String LASTBUTTONSROW = "=0C./";
    private static final String SQRT = "√";
    private static final String POWER = "^";

    public Calculator(){
        initMandatoryComponents();
        setTitle("Calculator");
        setBounds(0,0,300,500);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        add(initTopTextField(), BorderLayout.NORTH);
        add(initButtonsPanel(), BorderLayout.CENTER);

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

    private JPanel initButtonsPanel() {
        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(5, 5));

        ActionListener buttonListener = new ButtonListener(textField);

        for (int i = 0; i < 4; i++) {
            JButton button;
            for (int j = 0; j < 4; j++) {
                if (i == 3) {
                    //рисуем последнюю строку
                    button = new JButton(
                            Character.toString(LASTBUTTONSROW.charAt(j)));
                } else {
                    if (j < 3) {
                        //рисуем цифры
                        int value = (i * 3) + j + 1;
                        button = new JButton(String.valueOf(value));
                    } else {
                        button = new JButton(
                                Character.toString(NOTDIGITTEXTBUTTONS.charAt(i))
                        );
                    }
                }
                button.addActionListener(buttonListener);
                digitPanel.add(button);
            }
        }
        JButton button = new JButton("/");
        button.addActionListener(buttonListener);
        digitPanel.add(button);
        JButton buttonSqrt = new JButton(SQRT);
        buttonSqrt.addActionListener(buttonListener);
        digitPanel.add(buttonSqrt);
        JButton buttonPower = new JButton(POWER);
        buttonPower.addActionListener(buttonListener);
        digitPanel.add(buttonPower);
        return digitPanel;
    }
}
