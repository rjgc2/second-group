
/**
 * ���ܣ��򵥵��������������
 * @author ����Գ1998
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

	private JTextField textField;									//�������ı���������ʾ��ֵ
	private JButton[] buttons;										//����������ť
	private String bufferNumber = "";								//�������������ֵ
	private String screenNumber = "";								//�������ı�����ʾ�ļ�������ֵ
	private String operator;										//�����������е������
	private boolean flag = false;									//�������б�־�������

	public SimpleCalculator(String name) {
		setTitle(name);												//���ô��ڱ���
		setSize(400, 240);											//���ô��ڿ�Ⱥ͸߶�
		setLayout(new BorderLayout(0, 4));							//���ô��ڵĲ��ֹ�����
		setDefaultCloseOperation(EXIT_ON_CLOSE);					//���õ�����ڵĲ�Ž�������
		setLocationRelativeTo(null);								//���ô�������Ļ����
		setResizable(false);										//���ô��ڴ�С���ɱ�
		/*���嵥���ı���������ʾ����*/
		textField = new JTextField("0.0");
		textField.setHorizontalAlignment(JTextField.RIGHT);			//�����ı�����ֵ��ʾ���Ҷ���
		textField.setFont(new Font("����", Font.BOLD, 20));			//�����ı���������
		add(textField, BorderLayout.NORTH);							//���ı�����봰����
		/*���尴ť������ť����panel�����*/
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 5, 4, 4));				//����panel���Ĳ��ֹ�����

		String[] buttonNames = {"1", "2", "3", "+", "C",			//���������ť������
								"4", "5", "6", "-", "CE",
								"7", "8", "9", "*", "sqrt",
								".", "0", "=", "/", "^2"};

		buttons = new JButton[buttonNames.length];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(buttonNames[i]);
			panel.add(buttons[i]);									//��������ť�������panel��
		}
		listener(buttons, textField);								//Ϊ�������������Ӽ�����
		add(panel);													//�����panel���봰����
		setVisible(true);											//���ô��ڿɼ�
	}

	public void listener(JButton[] buttons, JTextField textField) {

		/*Ϊ������������ť���Ӽ����¼�*/
		for (int i = 0; i < buttons.length; i++) {
			String tempString = buttons[i].getText();				//�����õ���ť����
			if((i >= 0 && i <= 2) || (i >= 5 && i <= 7) || (i >= 10 && i <= 12) || (i >= 15 && i <= 16)) {
				buttons[i].addActionListener(new ActionListener() { //Ϊ���ְ�ť���Ӽ����¼�

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
				buttons[i].addActionListener(new ActionListener() {	//Ϊ�������ť���Ӽ����¼�

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
			if (i == 4 || i == 9) {									//Ϊ������ť���Ӽ����¼�
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
			if (i == 14 || i == 19) {								//Ϊƽ����ƽ�������Ӽ����¼�
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
			if (i == 17) {											//Ϊ���ںŰ�ť���Ӽ����¼�
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

		/*Ϊ���������ı�����ʾ���Ӽ����¼�*/
		textField.addKeyListener(new KeyAdapter() {					//�����ı���ļ��̼����¼�
			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();										//����ȡ���Ӽ���¼�����ݣ�ֻ�ܴӼ������İ�ť����
			}
		});
	}

	public static void main(String[] args) {
		new SimpleCalculator("�򵥵��������������");
	}
}