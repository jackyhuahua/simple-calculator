package com.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Created by jacky on 2016/11/23.
 */

public class bc extends JFrame implements ActionListener {
    private boolean isFirst = true;
    private double number = 0.0;
    private String operator = "=";
    private final String[] str = {"7", "8", "9", "/", "4", "5", "6", "*", "1",
            "2", "3", "-", ".", "0", "=", "+"};
    private JButton[] buttons = new JButton[str.length];
    private JTextField result = new JTextField(15);
    private JButton backSpace, reset;

    bc(String title) {
        setTitle(title);
        init();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    void init() {
        JPanel panel = new JPanel(new GridLayout(4, 4));
        for (int i = 0; i < str.length; i++) {
            buttons[i] = new JButton(str[i]);
            buttons[i].setBackground(Color.magenta);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }
        backSpace = new JButton("backSpace");
        backSpace.addActionListener(this);
        reset = new JButton("reset");
        reset.addActionListener(this);
        JPanel panel1 = new JPanel();
        panel1.add(result);
        panel1.add(backSpace);
        panel1.add(reset);
        add(panel, new BorderLayout().CENTER);
        add(panel1, new BorderLayout().NORTH);
    }

    public void handleReset() {
        result.setText("0");
        operator = "=";
        isFirst = true;
    }

    public void handlerBackSpace() {
        String str = result.getText();
        int i = str.length();
        if (i > 0) {
            // 退格，将文本最后一个字符去掉
            str = str.substring(0, i - 1);
            if (str.length() == 0) {
                // 如果文本没有了内容，则初始化计算器的各种值
                result.setText("0");
                isFirst = true;
                operator = "=";
            } else {
                // 显示新的文本
                result.setText(str);
            }
        }
    }

    public void handleNumber(String label) {
        if (isFirst)
            result.setText(label);
        else if ((label.equals(".")) && (result.getText().indexOf(".") < 0)) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            result.setText(result.getText() + ".");
        } else if (!label.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            result.setText(result.getText() + label);
        }
        isFirst = false;
    }
    public void handleOperator(String label){
        if (operator.equals("+"))
            number += Double.valueOf(result.getText());
        else if (operator.equals("-"))
            number -= Double.valueOf(result.getText());
        else if (operator.equals("*"))
            number *= Double.valueOf(result.getText());
        else if (operator.equals("/"))
            number /= Double.parseDouble(result.getText());
        else if (operator.equals("="))
            number = Double.valueOf(result.getText());
        result.setText(String.valueOf(number));
        operator = label;
        isFirst = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        String label = e.getActionCommand();
        if (target == reset)
            handleReset();
        else if (target == backSpace)
            handlerBackSpace();
        else if ("0123456789.".indexOf(label) > 0)
            handleNumber(label);
        else
            handleOperator(label);
    }
}
