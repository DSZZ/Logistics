package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.nju.logistics.ui.courier.inputOrder.EmptyException.EmptyException;
import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;

public class OrderInputPanel extends JPanel{

	private static final long serialVersionUID = 55156093512126118L;
	
	JLabel orderID;
	JTextField inputfield;

	public OrderInputPanel(){
		this.setLayout(null);
		
		orderID = new JLabel("订单编号：");
		orderID.setBounds(0,2,100,20);
		orderID.setFont(new java.awt.Font("Dialog", 1, 17));
		this.add(orderID);
		
		inputfield = new JTextField();
		
		/* 用于限制订单编号：1.只能输入数字 2.将长度限制为10 */
		inputfield.setDocument(new NumberLengthLimitedDmt(10));
		
		inputfield.setBounds(100, 2, 100, 25);
		inputfield.setFont(new java.awt.Font("Dialog", 1, 17));
		inputfield.grabFocus();
		this.add(inputfield);
	}

	public String getOrderID() throws EmptyException{
		String text = inputfield.getText();
		if(text.length() < 10) throw new EmptyException(ExceptionKind.OrderIDLength);
		return text;
	}

	public void clear() {
		inputfield.setText("");
		
	}

}
