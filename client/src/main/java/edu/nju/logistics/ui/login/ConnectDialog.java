package edu.nju.logistics.ui.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.nju.logistics.ui.util.FrameUtil;

@SuppressWarnings("serial")
public class ConnectDialog extends JDialog implements MouseListener {

	private static final Image IMAGE = new ImageIcon("Image/login/bg.png").getImage();
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");

	private static final int WIDTH = 400;

	private static final int HEIGHT = 300;

	private JLabel close=null,time = null, prompt = null, fail = null;

	private JPanel panel = null;

	private Thread myThread = null;
	
	private Point origin=null;
	
	//public static ConnectDialog connectDialog=null;
	
	//private JFrame frame =null;

	public ConnectDialog(JFrame frame, String string, boolean modal) {
		super(frame, string, modal);
		//this.frame=frame;
		this.setSize(WIDTH, HEIGHT);
		this.setUndecorated(true);
		// this.setUndecorated(true);
		FrameUtil.setFrameCenter(this);
		// this.setLayout(null);
		//设置可拖拽
		this.origin=new Point();
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
		//初始化组件，开启线程
		this.init();
//		
//		this.addWindowListener(new WindowAdapter() {
//			@SuppressWarnings("deprecation")
//			public void windowClosing(WindowEvent e) {
//				myThread.stop();
//				dispose();
//			}
//		});
		// this.setVisible(true);
	}
//	/**
//	 * 获取单例
//	 */
//	public static  ConnectDialog getInstance(JFrame frame){
//		if(connectDialog==null){
//			connectDialog=new ConnectDialog(frame,"等待服务器连接", true);
//		}
//		return con
//	}
	/**
	 * 初始化组件，开启线程
	 */
	private void init() {
		this.panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(IMAGE, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		this.panel.setLayout(null);
		this.add(this.panel);
		
		this.close=new JLabel(CLOSE);
		this.close.setBorder(BorderFactory.createEtchedBorder());
		this.close.setBounds(380,0,20,20);
		this.close.addMouseListener(this);
		this.panel.add(this.close);

		this.prompt = new JLabel();
		this.prompt.setText("正在连接服务器......");
		this.prompt.setBounds(100, 30, 220, 30);
		this.prompt.setFont(new Font("楷体", Font.BOLD, 20));
		this.panel.add(this.prompt);

		this.time = new JLabel();
		this.time.setBounds(185, 135, 50, 50);
		this.time.setFont(new Font("黑体", Font.BOLD, 35));
		this.time.setForeground(Color.GREEN);
		this.panel.add(this.time);

		this.fail = new JLabel();
		this.fail.setText("连接失败!");
		this.fail.setBounds(145, 220, 200, 30);
		this.fail.setFont(new Font("楷体", Font.BOLD, 20));
		this.fail.setForeground(Color.RED);
		this.fail.setVisible(false);
		this.panel.add(this.fail);

		this.myThread = new Thread() {
			int index = 0;

			public void run() {
				while (true) {
					time.setText(index + "");
					fail.setVisible(false);
					if (index != 0 && index % 5 == 0) {
						// 尝试连接服务器
						index = 0;
						fail.setVisible(true);
						System.out.println("尝试连接服务器");
					}
					try {
						Thread.sleep(1000);
						index++;
					} catch (InterruptedException e) {
						e.printStackTrace();

					}
				}
			}
		};
		myThread.start();
		
	}

	public Thread getMyThread() {
		return this.myThread;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==this.close){
			myThread.stop();
			//this.frame.remove(this);
			dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
