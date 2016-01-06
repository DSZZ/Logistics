package edu.nju.logistics.ui.courier;

import java.awt.Cursor;
import javax.swing.JPanel;

import edu.nju.logistics.ui.functionButton.MyButton;

public class CourierLeftPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6097168479956743932L;
	
	private CourierFrame showPanelChanger;
	
	/**
	 * 初始位置的x坐标
	 */
	private static final int STARTX = 11;
	
	/**
	 * 初始位置的y坐标
	 */
	private static final int STARTY = 10;
	
	/**
	 * 图片宽度
	 */
	private static final int IMG_W = 170;
	
	/**
	 * 图片高度
	 */
	private static final int IMG_H = 50;
	
	/**
	 * 功能按钮之间的间隔
	 */
	private static final int INTERVAL = 35 + IMG_H;
	
	public CourierLeftPanel(CourierFrame showPanelChanger) {
		this.showPanelChanger = showPanelChanger;
		setOpaque(false);
		setLayout(null);
		initButtons();
	}
	private void initButtons() {
		MyButton button = new MyButton("派送");
		button.setBounds(STARTX, STARTY, IMG_W, IMG_H);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.dispatch,button));
		add(button);
		
		MyButton inputButton = new MyButton("订单输入");
		inputButton.setBounds(STARTX, STARTY + INTERVAL, IMG_W, IMG_H);
		inputButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		inputButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.input,inputButton));
		add(inputButton);
	}
	
}
