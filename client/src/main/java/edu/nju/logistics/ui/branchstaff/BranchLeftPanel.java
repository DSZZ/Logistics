package edu.nju.logistics.ui.branchstaff;

import java.awt.Cursor;

import javax.swing.JPanel;

import edu.nju.logistics.ui.functionButton.MyButton;

public class BranchLeftPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6097168479956743932L;
	
	private BranchFrame showPanelChanger;
	
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
	
	
	public BranchLeftPanel(BranchFrame showPanelChanger) {
		this.showPanelChanger = showPanelChanger;
		setOpaque(false);
		setLayout(null);
		initButtons();
	}
	private void initButtons() {
		MyButton paymentButton = new MyButton("收款");
		paymentButton.setBounds(STARTX, STARTY, IMG_W, IMG_H);
		paymentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		paymentButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.pay,paymentButton));
		add(paymentButton);
		
		MyButton distributeButton = new MyButton("分配派送");
		distributeButton.setBounds(STARTX, STARTY + INTERVAL, IMG_W, IMG_H);
		distributeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		distributeButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.distribute,distributeButton));
		add(distributeButton);
		
		MyButton loadButton = new MyButton("装车");
		loadButton.setBounds(STARTX, STARTY + INTERVAL*2, IMG_W, IMG_H);
		loadButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loadButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.load,loadButton));
		add(loadButton);
		
		MyButton receiveButton = new MyButton("接收中转");
		receiveButton.setBounds(STARTX, STARTY + INTERVAL*3, IMG_W, IMG_H);
		receiveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		receiveButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.receive,receiveButton));
		add(receiveButton);
		
		MyButton carButton = new MyButton("车辆管理");
		carButton.setBounds(STARTX, STARTY + INTERVAL*4, IMG_W, IMG_H);
		carButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		carButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.car,carButton));
		add(carButton);
		
		MyButton driverButton = new MyButton("司机管理");
		driverButton.setBounds(STARTX, STARTY + INTERVAL*5, IMG_W, IMG_H);
		driverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		driverButton.addMouseListener(new MouseDelegate(showPanelChanger,ShowPanelKind.driver,driverButton));
		add(driverButton);
		
	}
}
