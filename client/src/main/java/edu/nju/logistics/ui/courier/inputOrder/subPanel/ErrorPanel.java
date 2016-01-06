package edu.nju.logistics.ui.courier.inputOrder.subPanel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.courier.inputOrder.EmptyException.ExceptionKind;

public class ErrorPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1677247454109990401L;
	
	JLabel hint;

	public ErrorPanel() {
		this.setLayout(null);
		
		hint = new JLabel();
		hint.setBounds(0,2,1000,23);
		hint.setFont(new java.awt.Font("Dialog", 1, 19));
		hint.setForeground(Color.red);
		this.add(hint);
	}
	
	public void clear(){
		hint.setText("");
		repaint();
	}

	public void showError(ExceptionKind kind) {
		switch(kind){
		case GoodsAmount:hint.setText("未填写【托运货物】-【原件数】！");
			break;
		case GoodsName:hint.setText("未填写【托运货物】-【内件品名】！");
			break;
		case GoodsRealWeight:hint.setText("未填写【托运货物】-【实际重量】！");
			break;
		case GoodsSize:hint.setText("未填写【托运货物】-【体积】！");
			break;
		case OrderIDLength:hint.setText("订单编号必须是10位！");
			break;
		case PackageFee:hint.setText("未填写【费用信息】-【包装费】！");
			break;
		case PersonAddress:hint.setText("未填写【寄件人/收件人信息】-【地址】！");
			break;
		case PersonCompany:hint.setText("未填写【寄件人/收件人信息】-【单位】！");
			break;
		case PersonName:hint.setText("未填写【寄件人/收件人信息】-【姓名】！");
			break;
		case PersonPhone:hint.setText("未填写【寄件人/收件人信息】-【手机】！");
			break;
		case PersonTel:hint.setText("未填写【寄件人/收件人信息】-【电话】！");
			break;
		default:hint.setText("未知错误！！！");
			break;
		
		}
		
	}
}
