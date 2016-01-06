package edu.nju.logistics.ui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import edu.nju.logistics.bl.impl.login.LoginController;
import edu.nju.logistics.bl.service.login.LoginBLService;
import edu.nju.logistics.ui.login.LoginFrame;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.vo.UserVO;
/**
 * 主界面的父类，大家可以得到mainPanel和限制好的showPanel，
 * 在mainPanel的左边添加自己的按钮，在showPanel里显示内容,
 * showPanel设置成了透明,但加了边框
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements MouseListener{
	
	private static final Image ICON=new ImageIcon("Image/main/01.jpg").getImage();
	/**
	 * 背景图片Image/financial/main/mainbg1.jpg
	 */
	private static final Image BACKGROUND=new ImageIcon("Image/financial/main/Framebg4.jpg").getImage();
	/**
	 * 关闭按钮图片
	 */
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");
	/**
	 * 最小化图标按钮
	 */
	private static final ImageIcon MIN=new ImageIcon("Image/login/最小化.png");
	/**
	 * 注销按钮图片
	 */
	private static final ImageIcon LOG_OFF1=new ImageIcon("Image/main/user.png");
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;
	/**
	 * 显示区起始横坐标
	 */
	public static final int SHOWPANEL_INIT_X = 210;
	/**
	 * 显示区其起始纵坐标
	 */
	public static final int SHOWPANEL_INIT_Y = 80;
	/**
	 * 主面板宽度
	 */
	public static final int mainPanelW = 1200;
	/**
	 * 主面板高度
	 */
	public static final int mainPanelH = 650;
	/**
	 * 按钮宽度
	 */
	protected static final int BUTTON_WIDTH=170;
	/**
	 * 按钮高度
	 */
	protected static final int BUTTON_HEIGHT=50;
	/**
	 * 按钮间距
	 */
	protected static final int GAP=60;
	/**
	 * 按钮起始横坐标
	 */
	protected static final int BUTTON_X=25;
	/**
	 * 按钮起始纵坐标
	 */
	protected static final int BUTTON_Y=80;
	
	protected JPanel mainPanel=null;
	
	protected JPanel showPanel=null;
	
	protected JLabel close=null;
	
	private JLabel min=null;
	
	protected JLabel userInfo=null;
	
	protected JLabel logoff1=null;
	
	protected JLabel logoff2=null;
	
	private Cursor myCursor=null;
	
	private boolean flag=false;

	private QuitDialog quitDialog=null;
	
	private Point origin=null;
	
	protected UserVO userVO = null;

	public MainFrame(UserVO vo) {
		this.setIconImage(ICON);
		try{
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	     }catch(Exception e){}
		this.userVO = vo;
		initMainPanel();
		//初始化显示区，并设置panel为透明
		GlobalHintInserter.setFrame(this);
		initShowPanel();
		this.mainPanel.add(this.showPanel);
		//初始化组件
		this.initComponent(vo);
		//创建关闭提示窗口
		this.quitDialog=new QuitDialog(this, "退出提示", true);
		this.quitDialog.setVisible(false);
		
		this.origin=new Point();
		
		this.add(this.mainPanel);
		this.setSize(mainPanelW,mainPanelH);
		//去边框
		this.setUndecorated(true);
		//窗口出现线程
		this.moveLocation();
		//设置可拖拽
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				// 当鼠标按下的时候获得窗口当前的位置
				origin.x = e.getX();
				origin.y = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
				// 当鼠标拖动时获取窗口当前位置
				Point p = getLocation();
				// 设置窗口的位置
				// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
			}
		
		});
		
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
	}
	/*
	 * 窗口出现线程
	 */
	private void moveLocation() {
		Toolkit toolkit=Toolkit.getDefaultToolkit();
    	Dimension screen=toolkit.getScreenSize();
    	int x=screen.width-this.getWidth()>>1;
    	int y=screen.height-this.getHeight()>>1;
	
		Thread thread=new Thread(){
			public void run(){
				for(int i=-1200;i<x;i+=10){
					setLocation(i,y);
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		
	}
	/**
	 * 初始化主面板
	 */
	protected void initMainPanel(){
		this.mainPanel=new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(),this.getHeight(),null);	
			}
		};
		this.mainPanel.setLayout(null);
	}
	/**
	 * 初始阿显示区面板
	 * @return
	 */
	protected JPanel initShowPanel(){
	    showPanel = new JPanel();
		//showPanel.setBorder(BorderFactory.createEtchedBorder());
		showPanel.setOpaque(false);
		showPanel.setBounds(SHOWPANEL_INIT_X,SHOWPANEL_INIT_Y
				,SHOWPANEL_WIDTH,SHOWPANEL_HEIGHT);
		return showPanel;
	}
	/**
	 * 初始化组件
	 */
	private void initComponent(UserVO vo) {
		this.myCursor = new Cursor(Cursor.HAND_CURSOR);
		
		this.close=new JLabel(CLOSE);
		this.close.setCursor(this.myCursor);
		this.close.setBounds(1170,0,32,32);
		this.close.addMouseListener(this);
		this.mainPanel.add(this.close);
		
		this.min=new JLabel(MIN);
		this.min.setCursor(this.myCursor);
		this.min.setBounds(1138,0,32,32);
		this.min.addMouseListener(this);
		this.mainPanel.add(this.min);
		
		this.userInfo=new JLabel();
		this.userInfo.addMouseListener(this);
		this.userInfo.setText("ID："+vo.getId()+"  职位： "+vo.getRole());
		this.userInfo.setFont(new Font("楷体",Font.BOLD ,14));
		this.userInfo.setBounds(820,13,310,20);
		this.mainPanel.add(this.userInfo);
		
		this.logoff1=new JLabel("注销",LOG_OFF1,0);
		this.logoff1.setFont(new Font("宋体", Font.BOLD, 14));
		this.logoff1.setBorder(BorderFactory.createRaisedBevelBorder());
		this.logoff1.addMouseListener(this);
		this.logoff1.setBounds(900,30,75,40);
		this.logoff1.setVisible(false);
		this.mainPanel.add(this.logoff1);
		
		this.logoff2=new JLabel("注销",LOG_OFF1,0);
		this.logoff2.setFont(new Font("宋体", Font.BOLD, 14));
		this.logoff2.setBorder(BorderFactory.createEtchedBorder());
		this.logoff2.addMouseListener(this);
		this.logoff2.setBounds(900,30,75,40);
		this.logoff2.setVisible(false);
		this.mainPanel.add(this.logoff2);
		
	}
	
	public static void main(String[] args) {
		new MainFrame(new UserVO("1234567","123456","中转中心库存管理员"));
	}	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==this.close){
			//this.removeCurrentUser();
			//System.exit(0);
			this.quitDialog.setVisible(true);
			//this.dispose();
		}
		else if(e.getSource()==this.min){
			this.setExtendedState(JFrame.ICONIFIED);
		}
		else if(e.getSource()==this.userInfo){
			this.userInfo.setForeground(Color.RED);
			if(this.flag){
				this.logoff1.setVisible(false);
				this.flag=false;
			}
			else{
			this.logoff1.setVisible(true);
			this.flag=true;
			}
		}
		else if(e.getSource()==this.logoff2){
			this.removeCurrentUser();
			this.dispose();
			new LoginFrame();
		}
		
	}

	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==this.userInfo){
			this.userInfo.setForeground(Color.RED);
		}
		else if(e.getSource()==this.logoff1){
			this.logoff1.setVisible(false);
			this.logoff2.setVisible(true);
		}
		
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource()==this.userInfo){
			this.userInfo.setForeground(Color.BLACK);
		}
		else if(e.getSource()==this.logoff2){
			this.logoff2.setVisible(false);
			this.logoff1.setVisible(true);
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {

	}
	
	public JPanel getMainPanel(){
		return this.mainPanel;
	}
	
	public JPanel getShowPanel(){
		return this.showPanel;
	}
	/**
	 * 删除用户登录信息
	 */
	public void removeCurrentUser(){
		try {
			LoginBLService controller=new LoginController();
			controller.removeCurrentUser(this.userVO.getId());
		} catch (Exception e) {
			System.out.println("非正常退出程序");
//			ReconnectPanel reconnectPanel = new ReconnectPanel(
//					new ReconnectSuccessObserver() {
//				
//				@Override
//				public void informReconnectSuccess() {
//					// TODO Auto-generated method stub
//					
//				}
//			
//			},
//				new DisconnectInformer(){
//
//				@Override
//				public void findDisconnect() {
//					// TODO Auto-generated method stub
//					
//				}
//				
//			});
//			reconnectPanel.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y, SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
//			reconnectPanel.setVisible(false);
//			this.mainPanel.add(reconnectPanel);
		} 
		
	}
}
