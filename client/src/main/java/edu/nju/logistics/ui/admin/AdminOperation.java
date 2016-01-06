package edu.nju.logistics.ui.admin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.FrameUtil;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.UserVO;

/**
 * 管理员账户管理,核心类
 * 
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class AdminOperation extends JDialog implements ActionListener,MouseListener {

	private static final Image BACKGROUND = new ImageIcon("Image/admin/admin.png").getImage();
	
	private static final ImageIcon CLOSE=new ImageIcon("Image/login/关闭.png");

	private static final Font FONT = new Font("楷体", Font.BOLD, 13);

	private JPanel mainPanel = null;

	private JPanel btnPanenl = null;

	private JPanel showPanel = null;

	private JPanel tablePanel = null;

	private JPanel checkPanel = null;

	private commonButton add = null, delete = null, modify = null, check = null, back = null, confirm = null;
	
	private JTextField jtfCheck = null, jtfID = null, jtfPassword = null, jtfRole = null;

	private JRadioButton ID = null, role = null;

	private ButtonGroup group = null;

	private JComboBox<String> roleBox = null;

	private JComboBox<String> IDBox = null;

	private JTable table = null;

	private AdminOperaTable model = null;

	private JLabel close=null,jlID = null, jlPassword = null, jlRole = null;
	
	private JScrollPane jsp=null;

	private int rowNum = 0;
	/**
	 * 返回按钮标记
	 */
	private boolean flag1 = false;
	/**
	 * 确认按钮标记
	 */
	private boolean flag2 = false;

	private IdentityManagementBLService controller = null;

	private AdminFrame adminMain = null;
	
	private DisconnectInformer disconnectInformer=null;

	private Point origin=null;

	public AdminOperation(AdminFrame adminMain, String title, boolean modal, IdentityManagementBLService controller,DisconnectInformer disconnectInformer) {
		super(adminMain, title, modal);
		try {
			this.setSize(900, 500);
			
			this.setUndecorated(true);
			
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
			
			FrameUtil.setFrameCenter(this);
			//断线重连主面板
			this.disconnectInformer=disconnectInformer;
			// 身份管理业务逻辑控制器
			this.controller = controller;
			// 身份列表model
			this.model = new AdminOperaTable(this.controller);
			// 列表初始化
			this.model.init();

			this.adminMain = adminMain;

			this.mainPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), null);
				}
			};
			
			this.mainPanel.setLayout(null);
			this.add(this.mainPanel);

			this.close=new JLabel(CLOSE);
			this.close.setBounds(880,0,20,20);
			this.close.addMouseListener(this);
			this.mainPanel.add(this.close);
			
			this.initButtonPanel();

			this.initShowPanel();
			this.setResizable(false);
			this.setVisible(true);
			// this.addWindowListener(new WindowAdapter() {
			// public void windowClosing(WindowEvent e) {
			//// adminMain.setFlag();
			//// adminMain.labelGetter().setEnabled(true);
			// dispose();
			// }
			// });
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化显示区
	 */
	private void initShowPanel() {
		this.showPanel = new JPanel();
		this.showPanel.setLayout(null);
		this.showPanel.setBounds(170, 15, 718, 475);
		this.showPanel.setOpaque(false);
		//this.showPanel.setBorder(BorderFactory.createEtchedBorder());
		// 初始化列表面板
		this.initTablePanel();
		// 初始化查询面板
		this.initCheckPanel();

		this.mainPanel.add(this.showPanel);

	}

	/**
	 * 初始化列表面板
	 */
	private void initTablePanel() {
		this.tablePanel = new JPanel(new BorderLayout());
		this.tablePanel.setBounds(5, 5, 708, 410);

		this.table = new JTable(this.model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		// 不可整列移动
		this.table.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.table.getTableHeader().setResizingAllowed(false);
		
		this.jsp=new JScrollPane(this.table);
		
		this.decorateTable();
		
		this.tablePanel.add(this.jsp, "Center");

		this.showPanel.add(this.tablePanel);
	}

	/**
	 * 初始化查询面板
	 */
	private void initCheckPanel() {
		this.checkPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setFont(new Font("宋体", Font.BOLD, 14));
				g.drawString("当前用户总数为 " + model.getRowCount(), 5, 20);
			}
		};
		this.checkPanel.setLayout(null);
		this.checkPanel.setOpaque(false);
		this.jtfCheck = new JTextField();
		this.jtfCheck.setBounds(150, 5, 150, 25);
		this.checkPanel.add(this.jtfCheck);

		this.ID = new JRadioButton("ID");
		this.ID.addActionListener(this);
		this.role = new JRadioButton("职位");
		this.role.addActionListener(this);
		this.group = new ButtonGroup();
		this.group.add(this.ID);
		this.group.add(this.role);
		this.ID.setBounds(305, 8, 50, 20);
		this.role.setBounds(360, 8, 70, 20);
		this.ID.setFont(new Font("楷体", Font.BOLD, 14));
		this.role.setFont(new Font("楷体", Font.BOLD, 14));
		this.ID.setOpaque(false);
		this.role.setOpaque(false);
		this.checkPanel.add(this.ID);
		this.checkPanel.add(this.role);

		this.check = new commonButton("查看");
		this.check.addActionListener(this);
		//this.check.setFont(FONT);
		this.check.setBounds(480, 0, 100, 40);
		this.checkPanel.add(this.check);

		String[] roles = { "总经理", "管理员", "营业厅业务员", "中转中心业务员", "中转中心库存管理员", "快递员", "财务人员(低)", "财务人员(高)" };
		this.roleBox = new JComboBox<String>(roles);
		this.roleBox.setBounds(150, 5, 150, 25);
		this.roleBox.setVisible(false);
		this.checkPanel.add(this.roleBox);

		this.checkPanel.setBounds(5, 430, 708, 35);
		this.showPanel.add(this.checkPanel);
	}

	/**
	 * 初始化按钮框
	 */
	private void initButtonPanel() {
		this.btnPanenl = new JPanel();
		this.btnPanenl.setOpaque(false);
		this.btnPanenl.setBorder(BorderFactory.createEtchedBorder());
		this.btnPanenl.setLayout(null);
		this.btnPanenl.setBounds(5, 15, 150, 475);

		this.add = new commonButton("添加");
		this.add.addActionListener(this);
		//this.add.setFont(FONT);
		this.add.setBounds(25, 5, 100, 40);
		this.btnPanenl.add(this.add);

		this.delete = new commonButton("删除");
		this.delete.addActionListener(this);
		//this.delete.setFont(FONT);
		this.delete.setBounds(25, 65, 100, 40);
		this.btnPanenl.add(this.delete);

		this.modify = new commonButton("修改");
		this.modify.addActionListener(this);
		//this.modify.setFont(FONT);
		this.modify.setBounds(25, 125, 100, 40);
		this.btnPanenl.add(this.modify);

		this.back = new commonButton("返回");
		this.back.addActionListener(this);
		//this.back.setFont(FONT);
		this.back.setBounds(25, 410, 100, 40);
		this.btnPanenl.add(this.back);

		this.mainPanel.add(this.btnPanenl);
	}

	// /**
	// * 窗口居中
	// */
	// private void setFrameCenter() {
	// Toolkit toolkit = Toolkit.getDefaultToolkit();
	// Dimension screen = toolkit.getScreenSize();
	// int x = screen.width - this.getWidth() >> 1;
	// int y = screen.height - this.getHeight() >> 1;
	// this.setLocation(x, y);
	//
	// }

	/**
	 * 初始化增加用户组件
	 * 
	 * @throws RemoteException
	 */
	private void initAddUserComponent() throws RemoteException {
		String[] IDs;
		ArrayList<UserVO> vo = this.controller.showAddWindow();
		int num = vo.size();
		if (num == 0) {
			IDs = new String[] { "暂无数据" };
			this.jtfPassword.setEditable(false);
			this.confirm.setEnabled(false);
		} else {
			IDs = new String[num];

			for (int i = 0; i < IDs.length; i++) {
				IDs[i] = vo.get(i).getId();
			}
		}
		this.IDBox = new JComboBox<String>(IDs);
		this.IDBox.setBounds(5, 30, 140, 25);
		this.btnPanenl.add(this.IDBox);
		// 职位随ID确定并不可更改
		for (int i = 0; i < vo.size(); i++) {
			if (vo.get(i).getId().equals((String) IDBox.getSelectedItem())) {
				jtfRole.setText(vo.get(i).getRole());
			}
		}
		// 点选ID,职位随ID确定并不可更改
		this.IDBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < vo.size(); i++) {
					if (vo.get(i).getId().equals((String) IDBox.getSelectedItem())) {
						jtfRole.setText(vo.get(i).getRole());
					}
				}
			}
		});
	}
	/**
	 * 装饰表格
	 */
	private void decorateTable(){
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
	}
	/**
	 * 刷新账户管理界面
	 * 
	 * @throws RemoteException
	 */
	private void updateTable() throws RemoteException {
		this.model = new AdminOperaTable(this.controller);
		this.model.init();
		// 刷新数据表
		this.table.setModel(this.model);
		//表格装饰
		this.decorateTable();
		// 数字刷新
		this.checkPanel.repaint();
	}

	/**
	 * 增加账户
	 * 
	 * @throws RemoteException
	 */
	private void addUser() throws RemoteException {
		// 返回按钮为修改账户服务
		this.flag1 = true;
		// 创建add和modify框架
		this.createUpdateArea();
		// 确认按钮为增加账户服务
		this.flag2 = true;
		// 初始化增加用户组件
		this.initAddUserComponent();
	}

	/**
	 * 删除账户
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void deleteUser() throws RemoteException {
		this.rowNum = this.table.getSelectedRow();
		if (this.rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		String ID = (String) this.model.getValueAt(this.rowNum, 0);
		if (!this.controller.deleteUser(ID)) {
			JOptionPane.showMessageDialog(this, "删除失败！");
			return;
		}
		// 刷新账户管理界面
		this.updateTable();
		// 刷新人员情况变更列表
		AdminInitTable adminInitTable = new AdminInitTable(this.controller);
		this.adminMain.tableGetter().setModel(adminInitTable);
		// 刷新人员情况变更条数
		this.adminMain.countGetter().setText("记录条数为 " + adminInitTable.getRowCount());

		JOptionPane.showMessageDialog(this, "删除成功！");
	}

	/**
	 * 修改账户信息
	 */
	private void modifyUser() {
		// 确认按钮为修改账户服务
		this.flag2 = false;

		this.rowNum = this.table.getSelectedRow();
		if (this.rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		// 返回按钮为修改账户服务
		this.flag1 = true;
		// 创建add和modify框架
		this.createUpdateArea();

		this.jtfID = new JTextField();
		this.jtfID.setEditable(false);
		this.jtfID.setBounds(5, 30, 140, 25);
		this.btnPanenl.add(this.jtfID);

		String ID = (String) this.model.getValueAt(this.rowNum, 0);
		String password = (String) this.model.getValueAt(this.rowNum, 1);
		String role = (String) this.model.getValueAt(this.rowNum, 2);
		this.jtfID.setText(ID);
		this.jtfPassword.setText(password);
		this.jtfRole.setText(role);
	}

	/**
	 * 根据ID查询用户
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void checkByID() throws RemoteException {
		String id = this.jtfCheck.getText();
		this.model = new AdminOperaTable(this.controller);
		if (!this.model.checkByID(id)) {
			JOptionPane.showMessageDialog(this, "无此用户！");
			return;
		}
		this.table.setModel(this.model);
		this.decorateTable();
		this.checkPanel.repaint();
	}

	/**
	 * 根据职位查询用户
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void checkByRole() throws RemoteException {
		String role = (String) this.roleBox.getSelectedItem();
		this.model = new AdminOperaTable(this.controller);
		if (!this.model.checkByRole(role)) {
			JOptionPane.showMessageDialog(this, "该职业暂无用户！");
			return;
		}
		this.table.setModel(this.model);
		this.decorateTable();
		this.checkPanel.repaint();
	}

	/**
	 * 创建add和modify框架
	 */
	private void createUpdateArea() {
		this.add.setVisible(false);
		this.delete.setVisible(false);
		this.modify.setVisible(false);

		this.jlID = new JLabel("ID:");
		this.jlID.setFont(new Font("宋体", Font.BOLD, 14));
		this.jlID.setBounds(5, 5, 100, 20);
		this.btnPanenl.add(this.jlID);

		this.jlPassword = new JLabel("密码:");
		this.jlPassword.setFont(new Font("宋体", Font.BOLD, 14));
		this.jlPassword.setBounds(5, 70, 100, 20);
		this.btnPanenl.add(this.jlPassword);

		this.jtfPassword = new JTextField();
		this.jtfPassword.setBounds(5, 95, 140, 25);
		this.btnPanenl.add(this.jtfPassword);

		this.jlRole = new JLabel("职位:");
		this.jlRole.setFont(new Font("宋体", Font.BOLD, 14));
		this.jlRole.setBounds(5, 135, 100, 20);
		this.btnPanenl.add(this.jlRole);

		this.jtfRole = new JTextField();
		this.jtfRole.setBounds(5, 160, 140, 25);
		this.jtfRole.setEditable(false);
		this.btnPanenl.add(this.jtfRole);

		this.confirm = new commonButton("确认");
		this.confirm.addActionListener(this);
		this.confirm.setBounds(25, 350, 100, 40);
		this.confirm.setFont(FONT);
		this.btnPanenl.add(this.confirm);

		this.repaint();
	}

	/**
	 * 返回上层界面
	 */
	private void backTo() {
		this.jlID.setVisible(false);
		if (this.jtfID != null) {
			this.jtfID.setVisible(false);
		}
		if (this.IDBox != null) {
			this.IDBox.setVisible(false);
		}
		this.jlPassword.setVisible(false);
		this.jtfPassword.setVisible(false);
		this.jlRole.setVisible(false);
		this.jtfRole.setVisible(false);

		this.confirm.setVisible(false);

		this.add.setVisible(true);
		this.delete.setVisible(true);
		this.modify.setVisible(true);

		this.flag1 = false;
	}

	/**
	 * 增加用户确认之后的系统执行
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void confirmForAdd() throws RemoteException {
		// 增加账户
		String password = this.jtfPassword.getText();
		this.jtfPassword.setText("");
		// 若密码为空
		if (password.equals("")) {
			JOptionPane.showMessageDialog(this, "密码不能为空");
			return;
		}
		// 若密码大于20位
		if (password.length() > 20) {
			JOptionPane.showMessageDialog(this, "密码不能超过20位");
			return;
		}
		UserVO userVO = new UserVO((String) this.IDBox.getSelectedItem(), password, this.jtfRole.getText());
		if (!this.controller.addUser(userVO)) {
			JOptionPane.showMessageDialog(this, "添加失败！");
			return;
		}
		// 刷新账户管理界面
		this.updateTable();
		// 刷新人员情况变更列表
		AdminInitTable adminInitTable = new AdminInitTable(this.controller);
		this.adminMain.tableGetter().setModel(adminInitTable);
		// 刷新人员情况变更条数
		this.adminMain.countGetter().setText("记录条数为 " + adminInitTable.getRowCount());
		// 再次初始化增加用户组件
		this.IDBox.setVisible(false);
		this.initAddUserComponent();

		JOptionPane.showMessageDialog(this, "添加成功！");
	}

	/**
	 * 修改账户信息确认之后的系统执行
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void confirmForModify() throws RemoteException {
		// 修改账户信息
		String password = this.jtfPassword.getText();
		// 若未做修改
		if (((String) this.model.getValueAt(this.rowNum, 1)).equals(password)) {
			JOptionPane.showMessageDialog(this, "请做修改！");
			return;
		}
		// 若密码为空
		if (password.equals("")) {
			JOptionPane.showMessageDialog(this, "密码不能为空");
			return;
		}
		// 若密码大于20位
		if (password.length() > 20) {
			JOptionPane.showMessageDialog(this, "密码不能超过20位");
			return;
		}
		UserVO userVO = new UserVO(this.jtfID.getText(), password, this.jtfRole.getText());
		if (!this.controller.updateUser(userVO)) {
			JOptionPane.showMessageDialog(this, "修改信息失败！");
			return;
		}
		// 刷新账户管理界面
		this.updateTable();
		// 返回上层界面
		this.backTo();

		JOptionPane.showMessageDialog(this, "修改信息成功！");
	}

	public void actionPerformed(ActionEvent e) {
		try {
			// 增加账户
			if (e.getSource() == this.add) {
				this.addUser();
			}
			// 删除账户
			else if (e.getSource() == this.delete) {
				this.deleteUser();
			}
			// 修改账户信息
			else if (e.getSource() == this.modify) {
				this.modifyUser();
			}
			// 查询账户
			else if (e.getSource() == this.check) {
				if (this.ID.isSelected()) {
					this.checkByID();
				} else if (this.role.isSelected()) {
					this.checkByRole();
				} else {
					JOptionPane.showMessageDialog(this, "请选择一项进行查询！");
				}
			}
			// 返回
			else if (e.getSource() == this.back) {
				// 从增加或修改界面返回上层界面
				if (this.flag1) {
					this.backTo();
				}
				// 从账户管理界面返回到管理员主界面
				else {
					// this.adminMain.setFlag();
					// this.adminMain.labelGetter().setEnabled(true);
					this.dispose();
				}
			}
			// 选择了根据职位查询账户
			else if (e.getSource() == this.role) {
				this.jtfCheck.setVisible(false);
				this.roleBox.setVisible(true);
			}
			// 选择了根据ID查询账户
			else if (e.getSource() == this.ID) {
				this.roleBox.setVisible(false);
				this.jtfCheck.setVisible(true);
			}
			// 确认按钮
			else if (e.getSource() == this.confirm) {
				// 确认按钮为添加账户服务
				if (this.flag2) {
					this.confirmForAdd();
				}
				// 确认按钮为修改账户服务
				else {
					this.confirmForModify();
				}
			}
		} catch (RemoteException e1) {
			this.dispose();
			this.disconnectInformer.findDisconnect();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==this.close){
			this.dispose();
		}
		
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