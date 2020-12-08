
/**
 * 功能：简单的四则运算计算器
 * @author 独孤猿1998
 *
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SimpleCalculator extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField textField;									//计算器文本框，用来显示数值
	private JButton[] buttons;										//计算器各按钮
	private String bufferNumber = "";								//计算器缓存的数值
	private String screenNumber = "";								//计算器文本框显示的计算结果数值
	private String operator;										//计算器运算中的运算符
	private boolean flag = false;									//计算器中标志运算过程

	public SimpleCalculator(String name) {
		setTitle(name);												//设置窗口标题
		setSize(400, 240);											//设置窗口宽度和高度
		setLayout(new BorderLayout(0, 4));							//设置窗口的布局管理器
		setDefaultCloseOperation(EXIT_ON_CLOSE);					//设置点击窗口的叉号结束程序
		setLocationRelativeTo(null);								//设置窗口在屏幕居中
		setResizable(false);										//设置窗口大小不可变
		/*定义单行文本框，用于显示运算*/
		textField = new JTextField("0.0");
		textField.setHorizontalAlignment(JTextField.RIGHT);			//设置文本框数值显示向右对齐
		textField.setFont(new Font("宋体", Font.BOLD, 20));			//设置文本框中字体
		add(textField, BorderLayout.NORTH);							//将文本框放入窗口中
		/*定义按钮，将按钮放于panel面板中*/
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 5, 4, 4));				//设置panel面板的布局管理器

		String[] buttonNames = {"1", "2", "3", "+", "C",			//定义各个按钮的名称
								"4", "5", "6", "-", "CE",
								"7", "8", "9", "*", "sqrt",
								".", "0", "=", "/", "^2"};

		buttons = new JButton[buttonNames.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonNames[i]);
			panel.add(buttons[i]);									//将各个按钮放入面板panel中
		}
		listener(buttons, textField);								//为计算机各组件增加监听器
		add(panel);													//将面板panel放入窗口中
		setVisible(true);											//设置窗口可见
	}

	public void listener(JButton[] buttons, JTextField textField) {

		/*为计算器各个按钮增加监听事件*/
		for (int i = 0; i < buttons.length; i++) {
			String tempString = buttons[i].getText();				//用来得到按钮名称
			if((i >= 0 && i <= 2) || (i >= 5 && i <= 7) || (i >= 10 && i <= 12) || (i >= 15 && i <= 16)) {
				buttons[i].addActionListener(new ActionListener() { //为数字按钮增加监听事件

					@Override
					public void actionPerformed(ActionEvent e) {
						if (flag == false) {
							flag = true;
							textField.setText("");
						}
						textField.setText(textField.getText() + tempString);
						repaint();
					}
				});
			}
			if (i == 3 || i == 8 || i == 13 || i == 18) {
				buttons[i].addActionListener(new ActionListener() {	//为运算符按钮增加监听事件

					@Override
					public void actionPerformed(ActionEvent e) {
						if (flag || !textField.getText().equals("")) {
							flag = false;
							operator = tempString;
							bufferNumber = textField.getText();
							repaint();
						}
					}
				});
			}
			if (i == 4 || i == 9) {									//为消除按钮增加监听事件
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (tempString.equals("C")) {
							textField.setText("");
							repaint();
						}
						if (tempString.equals("CE")) {
							if(textField.getText().length() != 0) {
								textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
							}
							repaint();
						}
					}
				});
			}
			if (i == 14 || i == 19) {								//为平方和平方根增加监听事件
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (tempString.equals("sqrt")) {
								BigDecimal result = new BigDecimal(textField.getText());
								screenNumber = "" + Math.sqrt(result.doubleValue());
							}
							if (tempString.equals("^2")) {
								BigDecimal result = new BigDecimal(textField.getText());
								screenNumber = result.pow(2).toString();
							}
							textField.setText(screenNumber);
							repaint();
						} catch (Exception e2) {
							textField.setText("ERROR");
							repaint();
						}
					}
				});
			}
			if (i == 17) {											//为等于号按钮增加监听事件
				buttons[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (!bufferNumber.equals("") && !operator.equals("") && !textField.getText().equals("")) {
							if (flag) {
								screenNumber = textField.getText();
							}
							try {
								BigDecimal bufferBigDecimal = new BigDecimal(bufferNumber);
								BigDecimal screenBigDecimal = new BigDecimal(screenNumber);
								switch (operator) {

								case "+":
									screenNumber = bufferBigDecimal.add(screenBigDecimal).toString();
									break;
								case "-":
									screenNumber = bufferBigDecimal.subtract(screenBigDecimal).toString();
									break;
								case "*":
									screenNumber = bufferBigDecimal.multiply(screenBigDecimal).toString();
									break;
								case "/":
									screenNumber = bufferBigDecimal.divide(screenBigDecimal, 30, BigDecimal.ROUND_HALF_UP).toString();
									break;
								default:
									break;
								}
								flag = false;
								textField.setText(screenNumber);
								bufferNumber = textField.getText();
								screenNumber = screenBigDecimal.toString();
								repaint();
							} catch (Exception e2) {
								textField.setText("ERROR");
								repaint();
							}
						}
					}
				});
			}
		}

		/*为计算器的文本框显示增加监听事件*/
		textField.addKeyListener(new KeyAdapter() {					//设置文本框的键盘监听事件
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();										//设置取消从键盘录入数据，只能从计算器的按钮输入
			}
		});
	}

	public static void main(String[] args) {
		new SimpleCalculator("简单的四则运算计算器");
	}
}