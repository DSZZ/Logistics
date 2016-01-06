package edu.nju.logistics.ui.main;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.FrameUtil;

@SuppressWarnings("serial")
public class QuitDialog extends JDialog implements MouseListener {
	
	private static final Image BACKGROUND = new ImageIcon("Image/main/quit.png").getImage();
	
//	private static final ImageIcon CONFIRM = new ImageIcon("Image/main/confirm.png");
//
//	private static final ImageIcon CANCEL = new ImageIcon("Image/main/cancel.png");
	
	private static final int WIDTH=300;
	
	private static final int HEIGHT=200;

	private JLabel string = null;

	private commonButton confirm = null, cancel = null;
	
	private MainFrame owner = null;

	private Cursor cursor = null;
	
	private JPanel panel=null;

	public QuitDialog(MainFrame owner, String title, boolean modal) {
		super(owner, title, modal);

		this.owner = owner;
		//设置大小
		this.setSize(WIDTH,HEIGHT );
		//去边框
		this.setUndecorated(true);
		//居中
		FrameUtil.setFrameCenter(this);
		// 初始化组件
		this.initComponent();
	}

	/**
	 * 初始化组件
	 */
	private void initComponent() {
		this.panel=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(BACKGROUND, 0, 0,this.getWidth(),this.getHeight(), null);
			}
		};
		this.add(this.panel);
		this.panel.setLayout(null);
		//this.panel.setBorder(BorderFactory.createTitledBorder(null, "退出提示",0,0, new Font("楷体",Font.BOLD,14), Color.GREEN));

		
		this.cursor = new Cursor(Cursor.HAND_CURSOR);

		this.string = new JLabel("确定退出系统？");
		this.string.setFont(new Font("楷体", Font.BOLD, 20));
		this.string.setBounds(75, 55, 150, 30);
		this.panel.add(this.string);

		this.confirm = new commonButton("确认");
		this.confirm.setCursor(this.cursor);
		this.confirm.setBounds(30, 130, 100, 40);
		this.confirm.addMouseListener(this);
		this.panel.add(this.confirm);

		this.cancel = new commonButton("取消");
		this.cancel.setCursor(this.cursor);
		this.cancel.setBounds(175, 130, 100, 40);
		this.cancel.addMouseListener(this);
		this.panel.add(this.cancel);

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.confirm) {
			// 删除用户登录信息
			this.owner.removeCurrentUser();
			System.exit(0);
		} else if (e.getSource() == this.cancel) {
			this.dispose();
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}
}
