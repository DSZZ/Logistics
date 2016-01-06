package edu.nju.logistics.ui.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.nju.logistics.bl.impl.login.LoginController;
import edu.nju.logistics.bl.service.login.LoginBLService;
import edu.nju.logistics.ui.accountant.main.UIController;
import edu.nju.logistics.ui.admin.AdminFrame;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.centerstaff.CenterStaffFrame;
import edu.nju.logistics.ui.checkorder.CheckOrderFrame;
import edu.nju.logistics.ui.courier.CourierFrame;
import edu.nju.logistics.ui.keeper.KeeperFrame;
import edu.nju.logistics.ui.manager.ManagerFrame;
import edu.nju.logistics.ui.util.LoginUtil;
import edu.nju.logistics.vo.UserVO;

public class LoginPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private static int VGap = 10;
	private static int TagW = 120;
	private static int MinW = 20;
	private static int MinH = 20;
	private static int MinX = (LoginFrame.frameWidth - 2 * MinW);
	private static int LabelH = 24;
	private static int LabelW = 30;
	private static int TextFieldW = 128;
	private static int LoginW = LabelW + TextFieldW;
	private static int LabelX = (LoginFrame.frameWidth - (LabelW + TextFieldW)) / 2;
	private static int LabelY1 = LoginFrame.frameHeight / 2 - 30;
	private static int LabelY2 = LabelY1 + LabelH + VGap;
	private static int TFX = LabelX + LabelW;
	private static int LoginX = (LoginFrame.frameWidth - LoginW) / 2;
	private static int LoginY = LabelY2 + LabelH + VGap;

	/**
	 * 用户登陆业务逻辑
	 */
	private LoginBLService loginBLService = null;

	BufferedImage bg1;
	// TF
	ImageIcon renderIcon;
	// Login
	ImageIcon renderIcon1;
	// min\exit
	ImageIcon renderIcon2;
	ImageIcon minIcon;
	ImageIcon exitIcon;
	JLabel usernameLB;
	JLabel passwordLB;
	JTextField usernameTF;
	JPasswordField passwordTF;
	JLabel loginLB,checkLB;
	JLabel tagLB;
	// 用户名
	JLabel renderLB1;
	// 密码
	JLabel renderLB2;
	// 登录按钮
	JLabel renderLB3,renderLB6;
	// 最小化、关闭
	JLabel renderLB4, renderLB5;
	// 最小化
	JLabel min;
	// 关闭
	JLabel exit;

	private JFrame frame;
	
	private  ConnectDialog connectDialog=null;
	
	private boolean flag=false;

	public LoginPanel(JFrame frame) {
		this.frame = frame;
		this.init();
		Thread myThread = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				while (true) {
					try {
						loginBLService = new LoginController();
						System.out.println("连接成功！");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(connectDialog!=null){
						connectDialog.getMyThread().stop();
						connectDialog.dispose();
						JOptionPane.showMessageDialog(null, "已连接上服务器！");
						if(flag){
							jumpToOrderCheck();
							flag=false;
						}
						}
						break;
					} catch (RemoteException | MalformedURLException | NotBoundException e1) {
						//e1.printStackTrace();
						//System.out.println("连接失败......");
					}

				}
			}
		};
		myThread.start();
	}

	public void init() {
		try {
			bg1 = ImageIO.read(new FileInputStream("Image/login/bg6.jpg"));
			renderIcon = new ImageIcon("Image/login/TFRender.png");
			renderIcon1 = new ImageIcon("Image/login/LBRender.png");
			renderIcon2 = new ImageIcon("Image/login/衬底.png");
			minIcon = new ImageIcon("Image/login/最小化.png");
			exitIcon = new ImageIcon("Image/login/关闭.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setLayout(null);

		usernameLB = new JLabel("账号");
		passwordLB = new JLabel("密码");
		loginLB = new JLabel("登录", JLabel.CENTER);
		checkLB  = new  JLabel("订单查询",JLabel.CENTER);
		tagLB = new JLabel("--NJU物流系统--");

		Font font = new Font("微软雅黑", 0, 14);

		usernameLB.setFont(font);
		passwordLB.setFont(font);
		loginLB.setFont(font);
		checkLB.setFont(font);
		tagLB.setFont(font);

		usernameLB.setForeground(Color.white);
		passwordLB.setForeground(Color.white);
		tagLB.setForeground(Color.white);
		loginLB.setForeground(Color.white);
		checkLB.setForeground(Color.white);

		loginLB.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				renderLB3.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				renderLB3.setVisible(false);

			}

			public void mouseClicked(MouseEvent e) {
				login();

			}
		});
		
		 checkLB.addMouseListener(new MouseAdapter(){
	     		
	    		public void mouseEntered(MouseEvent e) {	
	    			 renderLB6.setVisible(true);
	    		}

	    		public void mouseExited(MouseEvent e) {   			
	    		      renderLB6.setVisible(false);
	    			
	    		}
	    		public void mouseClicked(MouseEvent e){
	    			//切换面板
	    			try {
						jumpToOrderCheck();
					} catch (Exception e1) {
					waitConnect();
					flag=true;
					}
	    		}
	     	});

		usernameTF = new JTextField();
		// 读档
		String id = LoginUtil.load();
		this.usernameTF.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		this.usernameTF.setText(id);

		passwordTF = new JPasswordField();
		this.passwordTF.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		usernameTF.setFont(font);
		font = new Font("TimesRoman", 1, 9);
		passwordTF.setFont(font);

		// 将文本框设置为透明
		usernameTF.setOpaque(false);
		passwordTF.setOpaque(false);

		// 将文本框的边框也隐藏掉
		usernameTF.setBorder(BorderFactory.createEmptyBorder());
		passwordTF.setBorder(BorderFactory.createEmptyBorder());
		usernameTF.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				renderLB1.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				
				renderLB1.setVisible(false);
				// loginFrame.getPanel().repaint();
			}
		});

		passwordTF.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				
				renderLB2.setVisible(true);

			}

			public void mouseExited(MouseEvent e) {
				
				renderLB2.setVisible(false);

			}
		});

		// 光晕LB
		//光晕LB
     	renderLB1 = new JLabel(renderIcon);
     	renderLB2 = new JLabel(renderIcon);
     	renderLB3 = new JLabel(renderIcon1);
     	renderLB4 = new JLabel (renderIcon2);
     	renderLB5 = new JLabel (renderIcon2); 
     	renderLB6 = new JLabel(renderIcon1);
     	renderLB1.setVisible(false);
     	renderLB2.setVisible(false);
     	renderLB3.setVisible(false);
     	renderLB4.setVisible(false);
     	renderLB5.setVisible(false);
     	renderLB6.setVisible(false);
     	min = new JLabel(minIcon);
     	exit= new JLabel(exitIcon);
		min.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				
				renderLB4.setVisible(true);
				// loginFrame.getPanel().repaint();
			}

			public void mouseExited(MouseEvent e) {
				
				renderLB4.setVisible(false);
				// loginFrame.getPanel().repaint();
			}

			public void mouseClicked(MouseEvent e) {
				JLabel minLB = (JLabel) e.getSource();
				Container p = minLB.getParent();
				while (p != null && !(p instanceof LoginFrame)) {
					p = p.getParent();
				}
				LoginFrame frame = (LoginFrame) p;
				frame.setExtendedState(JFrame.ICONIFIED);
			}
		});

		exit.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				
				renderLB5.setVisible(true);
				// loginFrame.getPanel().repaint();
			}

			public void mouseExited(MouseEvent e) {
				
				renderLB5.setVisible(false);
				// loginFrame.getPanel().repaint();
			}

			public void mouseClicked(MouseEvent e) {
				// JLabel minLB = (JLabel) e.getSource();
				// loginFrame frame = (loginFrame)minLB.getParent().getParent();
				System.exit(0);
			}
		});
		// 设置所有组件的位置
		min.setBounds(MinX, 0, MinW, MinH);
		renderLB4.setBounds(MinX, 0, MinW, MinH);
		exit.setBounds(MinX + MinW, 0, MinW, MinH);
		renderLB5.setBounds(MinX + MinW, 0, MinW, MinH);
		usernameLB.setBounds(LabelX, LabelY1, LabelW, LabelH);
		passwordLB.setBounds(LabelX, LabelY2, LabelW, LabelH);
		usernameTF.setBounds(TFX, LabelY1, TextFieldW, LabelH);
		renderLB1.setBounds(TFX, LabelY1, TextFieldW, LabelH);
		passwordTF.setBounds(TFX, LabelY2, TextFieldW, LabelH);
		renderLB2.setBounds(TFX, LabelY2, TextFieldW, LabelH);
		loginLB.setBounds(LoginX, LoginY, LoginW, LabelH);
		renderLB3.setBounds(LoginX, LoginY, LoginW, LabelH);
		checkLB.setBounds(loginLB.getX(),loginLB.getY()+LabelH+10,LoginW,LabelH);
     	renderLB6.setBounds(loginLB.getX(),loginLB.getY()+LabelH+10,LoginW,LabelH);
		tagLB.setBounds(20, 20, TagW, 20);
		this.add(min);
		this.add(exit);
		this.add(usernameLB);
		this.add(passwordLB);
		this.add(usernameTF);
		this.add(passwordTF);

		this.add(renderLB1);
		this.add(renderLB2);
		this.add(renderLB3);
		this.add(renderLB4);
		this.add(renderLB5);
		this.add(renderLB6);
		this.add(loginLB);
		this.add(checkLB);
		this.add(tagLB);
		this.setVisible(true);

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(bg1, 0, 0, this.getWidth(), this.getHeight(), null);
		Graphics2D g2 = (Graphics2D) g;
		// 在文本框的位置画上背景
		Color color = new Color(255, 255, 255, 150);
		g2.setColor(color);
		// usernameTF
		g2.fillRect(TFX, LabelY1, TextFieldW, LabelH);
		// passwordTF
		g2.fillRect(TFX, LabelY2, TextFieldW, LabelH);
		// loginLB
		Color newcolor = new Color(75, 75, 75, 200);
		g2.setColor(newcolor);
		g2.fillRect(LoginX, LoginY, LoginW, LabelH);
		 g2.fillRect(checkLB.getX(), checkLB.getY(), LoginW, LabelH);

	}

	private  void jumpToOrderCheck() throws RemoteException{
		  new CheckOrderFrame();
		  this.frame.dispose();
	}
	
	
	/**
	 * 验证登录
	 */
	private void login() {
		try {
			String id = usernameTF.getText().trim();
			String password = new String(passwordTF.getPassword());
			UserVO vo = new UserVO(id, password);
			if (loginBLService.login(vo)) {
				//判断是否重复登录
				if(loginBLService.isRepeatLogin(id)){
					JOptionPane.showMessageDialog(this, "该账户已登录\n请勿重复登录！");
					return;
				}
				//记录当前用户
				loginBLService.record(vo);
				String role = loginBLService.getRole();
				vo.setRole(role);
				JOptionPane.showMessageDialog(this, "登录成功！\n您的职位是" + role);
				// 存档
				LoginUtil.save(id);
				// 关闭登录界面
				frame.dispose();
				// 界面跳转
				interfaceJump(vo);
				System.out.println("连接成功");
			} else {
				JOptionPane.showMessageDialog(null, "登录失败！\n请检查您的账号和密码是否匹配");
			}

		} catch (Exception e) {
			this.waitConnect();
		}
				
	}
	/**
	 * 等待连接
	 */
	private void waitConnect(){
		if(loginBLService!=null){
			JOptionPane.showMessageDialog(this, "与服务器断开连接！");
			if(this.connectDialog!=null){
			this.connectDialog.dispose();
			}
			this.frame.dispose();
			new LoginFrame();
			//System.exit(0);
			
		}
		else{
		connectDialog=new ConnectDialog(frame, "等待服务器连接",true);
		this.connectDialog.setVisible(true);
		System.out.println("连接失败");
		}
	
	}
	/**
	 * 界面跳转
	 * 
	 * @param vo
	 * @throws RemoteException 
	 */
	private void interfaceJump(UserVO vo) throws RemoteException{
		if (vo.getRole().equals("管理员")) {
			new AdminFrame(vo);
		} else if (vo.getRole().equals("总经理")) {
			new ManagerFrame(vo);
		} else if (vo.getRole().equals("财务人员(高)")) {
				UIController.initFinancialFrame(vo);
		} else if (vo.getRole().equals("财务人员(低)")) {
				UIController.initFinancialFrame(vo);
		} else if (vo.getRole().equals("中转中心业务员")) {
			new CenterStaffFrame(vo);
		} else if (vo.getRole().equals("中转中心库存管理员")) {
			new KeeperFrame(vo);
		} else if (vo.getRole().equals("营业厅业务员")) {
			new BranchFrame(vo);
		} else if (vo.getRole().equals("快递员")) {
			new CourierFrame(vo);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// if (e.getSource() == this.close_icon) {
		// this.dispose();
		// // System.exit(0);
		// } else if (e.getSource() == this.login_icon) {
		// this.login();
		// }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		

	}

	@Override
	public void mouseExited(MouseEvent e) {
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		

	}

}
