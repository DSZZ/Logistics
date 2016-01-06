package edu.nju.logistics.ui.centerstaff;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.util.FrameUtil;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowResultDialog extends JDialog implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 宽度
	 */
	private static final int WIDTH = 600;
	
	/**
	 * 高度
	 */
	private static final int HEIGHT = 600;
	
	/**
	 * 中转单对象
	 */
	private ArrayList<OrderVO> list = null;
	
	/**
	 * 显示面板
	 */
	private JPanel panel = null;
	
	/**
	 * 显示信息
	 */
	private JLabel label = null;
	
	/**
	 * 确认按钮
	 */
	private JButton confirm_button = null;
	
	/**
	 * 发货或收货
	 */
	private ResultType rt = null;
	
	public ShowResultDialog(JFrame owner,String title, boolean modal, ArrayList<OrderVO> list, ResultType rt) {
		super(owner, title, modal);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.list = list;
		this.rt = rt;
		FrameUtil.setFrameCenter(this);
		this.initComponent();
	}
	
	public void initComponent() {
		this.panel = new TablePanel(list);
		this.add(this.panel);
		this.panel.setBounds(20, 100, 560, 400);
		//初始化按钮
		this.confirm_button = new JButton("确定");
		this.confirm_button.setBounds(260, 520, 120, 50);
		this.confirm_button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.confirm_button.addMouseListener(this);
		this.add(this.confirm_button);
		//初始化信息提示
		this.initLabel(this.rt);
	}
	
	private void initLabel(ResultType rt) {
		this.label = new JLabel();
		this.label.setBounds(20, 40, 400, 30);
		if(rt == ResultType.RECEIVE) {
			label.setText("接受成功，该次中转的快件清单如下如下：");
		} else {
			label.setText("发送成功，发送的货物清单如下：");
		}
		this.label.setVisible(true);
		this.add(this.label);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == this.confirm_button) {
			this.setVisible(false);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
