package edu.nju.logistics.ui.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
import edu.nju.logistics.ui.admin.AdminFrame;
import edu.nju.logistics.ui.centerstaff.CenterStaffFrame;
import edu.nju.logistics.ui.keeper.KeeperFrame;
import edu.nju.logistics.ui.manager.ManagerFrame;
import edu.nju.logistics.ui.util.FrameUtil;
import edu.nju.logistics.ui.util.LoginUtil;
import edu.nju.logistics.vo.UserVO;

/**
 * 用户登录
 * 
 * @author 董轶波
 *
 */
public class Login extends JFrame implements MouseListener {
	/**
	 * 背景图片
	 */
	private static final Image BACKGROUND = new ImageIcon("Image/login/background2.png").getImage();
	/**
	 * 登陆按钮图片
	 */
	private static final ImageIcon LOGIN = new ImageIcon("Image/login/login.png");
	/**
	 * 关闭按钮图片
	 */
	private static final ImageIcon CLOSE = new ImageIcon("Image/login/close1.png");
	/**
	 * 搜索图片
	 */
	private static final ImageIcon SEARCH = new ImageIcon("Image/login/search.png");
	/**
	 * 字体
	 */
	private static final Font FONT = new Font("黑体", Font.BOLD, 15);
	/**
	 * 用户登陆业务逻辑
	 */
	private LoginBLService loginBLService = null;
	/**
	 * 主面板
	 */
	private JPanel mainPanel = null;
	/**
	 * 登陆，关闭，搜索，职位，账号，密码标签
	 */
	private JLabel login_icon = null, close_icon = null, search_icon = null, JlSearch = null, JlID = null,
			JlPassword = null;
	/**
	 * 账号输入框
	 */
	private JTextField ID = null;
	/**
	 * 物流查询框
	 */
	private JTextField search = null;
	/**
	 * 密码输入框
	 */
	private JPasswordField password = null;
	/**
	 * 鼠标手势
	 */
	private Cursor myCursor = null;
	/**
	 * 初始位置
	 */
	private Point origin = null;

	@SuppressWarnings("serial")
	public Login() {
		this.mainPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		this.mainPanel.setLayout(null);
		// 初始化组件
		this.initComponent();
		// this.mainPanel.requestFocus();
		this.mainPanel.setFocusable(true);
		// 设置回车快捷键
		this.mainPanel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		this.add(this.mainPanel);
		this.setSize(496, 270);
		// 去除windows窗口
		this.setUndecorated(true);
		// 窗口居中
		FrameUtil.setFrameCenter(this);
		// 可拖拽
		this.origin = new Point();
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
		this.setVisible(true);
		this.setDefaultCloseOperation(3);

	}

	/**
	 * 初始化所有组件
	 */
	private void initComponent() {
		this.myCursor = new Cursor(Cursor.HAND_CURSOR);

		this.login_icon = new JLabel(LOGIN);
		this.login_icon.addMouseListener(this);
		this.login_icon.setCursor(this.myCursor);
		this.login_icon.setBounds(200, 210, 100, 52);
		this.mainPanel.add(this.login_icon);

		this.close_icon = new JLabel(CLOSE);
		this.close_icon.addMouseListener(this);
		this.close_icon.setCursor(myCursor);
		this.close_icon.setBounds(472, 2, 22, 22);
		this.mainPanel.add(this.close_icon);

		this.JlSearch = new JLabel("物流查询");
		this.JlSearch.setFont(FONT);
		this.JlSearch.setForeground(Color.WHITE);
		this.JlSearch.setBounds(120, 70, 120, 20);
		this.mainPanel.add(this.JlSearch);

		this.search = new JTextField();
		this.search.setBorder(BorderFactory.createLoweredBevelBorder());
		this.search.setBounds(200, 70, 150, 25);
		this.mainPanel.add(this.search);

		this.search_icon = new JLabel(SEARCH);
		this.search_icon.addMouseListener(this);
		this.search_icon.setCursor(myCursor);
		this.search_icon.setBounds(360, 70, 25, 25);
		this.mainPanel.add(this.search_icon);

		this.JlID = new JLabel("账号");
		this.JlID.setFont(FONT);
		this.JlID.setForeground(Color.WHITE);
		this.JlID.setBounds(150, 120, 60, 20);
		this.mainPanel.add(this.JlID);

		this.ID = new JTextField();
		// 读档
		String id = LoginUtil.load();
		this.ID.setText(id);
		this.ID.setBorder(BorderFactory.createLoweredBevelBorder());
		this.ID.setBounds(200, 120, 150, 25);
		this.ID.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		this.mainPanel.add(this.ID);

		this.JlPassword = new JLabel("密码");
		this.JlPassword.setFont(FONT);
		this.JlPassword.setForeground(Color.WHITE);
		this.JlPassword.setBounds(150, 170, 60, 20);
		this.mainPanel.add(this.JlPassword);

		this.password = new JPasswordField();
		this.password.setBorder(BorderFactory.createLoweredBevelBorder());
		this.password.setBounds(200, 170, 150, 25);
		this.password.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}
		});
		this.mainPanel.add(this.password);

	}

	/**
	 * 验证登录
	 */
	private void login() {
		try {
			try {
				this.loginBLService = new LoginController();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String id = this.ID.getText().trim();
			String password = new String(this.password.getPassword());
			UserVO vo = new UserVO(id, password);
			if (this.loginBLService.login(vo)) {
				String role = this.loginBLService.getRole();
				vo.setRole(role);
				JOptionPane.showMessageDialog(this, "登陆成功！您的职位是" + role);
				// 存档
				LoginUtil.save(id);
				// 关闭登录界面
				this.dispose();
				// 界面跳转
				this.interfaceJump(vo);
			} else {
				JOptionPane.showMessageDialog(this, "登陆失败！");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 界面跳转
	 * 
	 * @param vo
	 */
	private void interfaceJump(UserVO vo) {
		if (vo.getRole().equals("管理员")) {
			new AdminFrame(vo);
		} else if (vo.getRole().equals("总经理")) {
			new ManagerFrame(vo);
		} else if (vo.getRole().equals("财务人员(高)")) {

		} else if (vo.getRole().equals("财务人员(低)")) {

		} else if (vo.getRole().equals("中转中心业务员")) {
			new CenterStaffFrame(vo);
		} else if (vo.getRole().equals("中转中心库存管理员")) {
			new KeeperFrame(vo);
		} else if (vo.getRole().equals("营业厅业务员")) {

		} else if (vo.getRole().equals("快递员")) {

		}

	}

	public static void main(String[] e) {
		new Login();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.close_icon) {
			this.dispose();
			// System.exit(0);
		} else if (e.getSource() == this.login_icon) {
			this.login();
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
