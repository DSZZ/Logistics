package edu.nju.logistics.ui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.UserVO;

/**
 * 机构下的员工管理
 * 
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class UserPanel extends JPanel implements MouseListener, ActionListener {

//	private static final ImageIcon ADD = new ImageIcon("Image/manager/add.png");
//
//	private static final ImageIcon DEL = new ImageIcon("Image/manager/delete.png");
//
//	private static final ImageIcon MODIFY = new ImageIcon("Image/manager/modify.png");
//
//	private static final ImageIcon CHECK = new ImageIcon("Image/manager/check.png");
//
//	private static final ImageIcon BACK = new ImageIcon("Image/manager/back.png");
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;

	private JPanel northPanel = null, centerPanel = null, southPanel = null;

	private JTextField jtf = null;

	private JLabel userInfo = null,  prompt_lab = null;

	private commonButton check_lab = null,add_lab = null, del_lab = null,
			modify_lab = null, back_lab = null;
	
	private Cursor cursor = null;

	private JScrollPane jsp = null;

	private JTable userTable = null;

	private JRadioButton ID_radioButton = null, role_radioButton = null;

	private ButtonGroup group = null;

	private JComboBox<String> role_box = null;

	private UserTable userModel = null;

	private InstitutionPanel institutionPanel = null;

	private OperationManagementBLService controller = null;

	private InstitutionVO institutionVO = null;

	private MainFrame owner = null;

	private AddUserDialog addUserDialog = null;

	private ModifyUserDialog modifyUserDialog = null;

	private boolean flag_back = false;
	
	private DisconnectInformer disconnectInformer=null;

	public UserPanel(OperationManagementBLService controller, MainFrame owner, InstitutionPanel institutionPanel,
			InstitutionVO institutionVO,	DisconnectInformer disconnectInformer) {
		try {
			this.owner = owner;
			this.institutionPanel = institutionPanel;
			this.institutionVO = institutionVO;
			this.disconnectInformer=disconnectInformer;
			this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
			this.setLayout(null);
			this.setOpaque(false);
			this.cursor = new Cursor(Cursor.HAND_CURSOR);

			this.controller = controller;
			// 初始化北部面板
			this.initNorthPanel();
			// 初始化中心面板
			this.initCenterPanel();
			// 初始化南部面板
			this.initSouthPanel();
		} catch (RemoteException e) {
			this.disconnectInformer.findDisconnect();
		}
	}

	/**
	 * 初始化北部面板
	 */
	private void initNorthPanel() {
		this.northPanel = new JPanel();
		this.northPanel.setLayout(null);
		this.northPanel.setOpaque(false);
	//	this.northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.northPanel.setBounds(5, 5, 970, 40);

		this.jtf = new JTextField();
		this.jtf.setBounds(40, 7, 160, 25);
		this.northPanel.add(this.jtf);
		this.ID_radioButton = new JRadioButton("ID");
		this.role_radioButton = new JRadioButton("职位");
		this.group = new ButtonGroup();
		this.group.add(this.ID_radioButton);
		this.group.add(this.role_radioButton);
		this.ID_radioButton.setBounds(220, 7, 50, 25);
		this.role_radioButton.setBounds(275, 7, 70, 25);
		this.ID_radioButton.setFont(new Font("楷体", Font.BOLD, 14));
		this.role_radioButton.setFont(new Font("楷体", Font.BOLD, 14));
		this.ID_radioButton.setOpaque(false);
		this.role_radioButton.setOpaque(false);
		this.ID_radioButton.addActionListener(this);
		this.role_radioButton.addActionListener(this);
		this.northPanel.add(this.ID_radioButton);
		this.northPanel.add(this.role_radioButton);
		String[] roles = this.switchByInsType();
		// { "总经理", "管理员", "营业厅业务员", "中转中心业务员", "中转中心库存管理员", "快递员", "财务人员(低)",
		// "财务人员(高)" };
		this.role_box = new JComboBox<String>(roles);
		this.role_box.setBounds(40, 7, 160, 25);
		this.role_box.setVisible(false);
		this.northPanel.add(this.role_box);

		this.prompt_lab = new JLabel("(请选择ID或职位进行查询)");
		this.prompt_lab.setBounds(350, 10, 150, 20);
		this.prompt_lab.setForeground(Color.RED);
		this.northPanel.add(this.prompt_lab);

		this.check_lab = new commonButton("查看");
		this.check_lab.setBounds(550, 0, 100, 40);
		this.check_lab.setCursor(this.cursor);
		this.check_lab.addMouseListener(this);
		this.northPanel.add(this.check_lab);

		this.userInfo = new JLabel();
		String location = this.institutionVO.getLocation();
		String userID = this.institutionVO.getId();
		String type = this.institutionVO.getType();
		String result = location + " " + userID + " " + type;
		this.userInfo.setText(result);
		this.userInfo.setBounds(800, 5, 200, 30);
		this.userInfo.setFont(new Font("楷体", Font.BOLD, 14));
		this.northPanel.add(this.userInfo);

		this.add(this.northPanel);

		this.repaint();

	}

	/**
	 * 根据机构种类选择下拉框
	 * 
	 * @return
	 */
	private String[] switchByInsType() {
		if (this.institutionVO.getType().equals("营业厅")) {
			return new String[] { "总经理", "管理员", "营业厅业务员", "快递员", "财务人员(低)", "财务人员(高)" };
		} else {
			return new String[] { "总经理", "管理员", "中转中心业务员", "中转中心库存管理员", "财务人员(低)", "财务人员(高)" };
		}
	}

	/**
	 * 初始化中心面板
	 * 
	 * @throws RemoteException
	 */
	private void initCenterPanel() throws RemoteException {
		this.centerPanel = new JPanel(new BorderLayout());
		this.centerPanel.setOpaque(false);
		this.centerPanel.setBounds(5, 50, 970, 460);

		this.userModel = new UserTable(this.controller, this.institutionVO);
		this.userModel.init();
		this.userTable = new JTable(this.userModel);
		this.userTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.userTable.setFont(new Font("楷体", Font.PLAIN, 14));
		// 不可整列移动
		this.userTable.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.userTable.getTableHeader().setResizingAllowed(false);
		this.jsp = new JScrollPane(this.userTable);
		//装饰表格
		this.decorateTable();
		this.centerPanel.add(this.jsp, BorderLayout.CENTER);

		this.add(this.centerPanel);

	}

	/**
	 * 初始化南部面板
	 */
	private void initSouthPanel() {
		this.southPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setFont(new Font("宋体", Font.BOLD, 14));
				g.drawString("记录条数为 " + userModel.getRowCount(), 5, 25);
			}
		};
		this.southPanel.setLayout(null);
		this.southPanel.setOpaque(false);
		this.southPanel.setBounds(5, 515, 970, 40);

		this.back_lab = new commonButton("返回");
		this.back_lab.setBounds(515, 0, 100, 40);
		this.back_lab.setCursor(this.cursor);
		this.back_lab.addMouseListener(this);
		this.southPanel.add(this.back_lab);

		this.add_lab = new commonButton("添加");
		this.add_lab.setBounds(635, 0, 100, 40);
		this.add_lab.setCursor(this.cursor);
		this.add_lab.addMouseListener(this);
		this.southPanel.add(this.add_lab);

		this.del_lab = new commonButton("删除");
		this.del_lab.setBounds(755, 0, 100,40);
		this.del_lab.setCursor(this.cursor);
		this.del_lab.addMouseListener(this);
		this.southPanel.add(this.del_lab);

		this.modify_lab = new commonButton("修改");
		this.modify_lab.setBounds(875, 0,100,40);
		this.modify_lab.setCursor(this.cursor);
		this.modify_lab.addMouseListener(this);
		this.southPanel.add(this.modify_lab);

		this.add(this.southPanel);
		this.repaint();

	}
	/**
	 * 表格装饰
	 */
	private void decorateTable(){
		MyTableHandler.initTable(this.userTable);
		MyTableHandler.initSpane(this.jsp, this.userTable);
	}
	/**
	 * 刷新列表与面板
	 * 
	 * @throws RemoteException
	 */
	private void updatePanel() throws RemoteException {
		this.userModel = new UserTable(this.controller, this.institutionVO);
		this.userModel.init();
		this.userTable.setModel(this.userModel);
		this.decorateTable();
		this.southPanel.repaint();
	}

	/**
	 * 增加员工
	 * 
	 * @throws RemoteException
	 */
	private void addUser() throws RemoteException {
		// 启动增加员工窗口
		this.addUserDialog = new AddUserDialog(this.owner, "增加员工", true, this.institutionVO, this.controller,this.disconnectInformer);
		this.addUserDialog.setVisible(true);
		if (this.addUserDialog.getFlag()) {
			// 刷新面板
			this.updatePanel();
		}
	}

	/**
	 * 修改员工信息
	 * 
	 * @throws RemoteException
	 */
	private void modifyUser() throws RemoteException {
		int rowNum = this.userTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		String ID = (String) this.userTable.getValueAt(rowNum, 0);
		String name = (String) this.userTable.getValueAt(rowNum, 1);
		String role = (String) this.userTable.getValueAt(rowNum, 2);
		UserVO vo = new UserVO(ID, name, role, this.institutionVO);

		this.modifyUserDialog = new ModifyUserDialog(this.owner, "修改员工信息", true, this.controller, vo,this.disconnectInformer);
		this.modifyUserDialog.setVisible(true);
		// 如果修改成功，刷新面板
		if (this.modifyUserDialog.getFlag()) {
			this.updatePanel();
		}

	}

	/**
	 * 删除员工
	 * @throws RemoteException 
	 */
	private void deleteUser() throws RemoteException {
		JOptionPane.showMessageDialog(this, "确定删除该员工？");

		int rowNum = this.userTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		String ID = (String) this.userTable.getValueAt(rowNum, 0);
		if (!this.controller.deleteEmployee(ID)) {
			JOptionPane.showMessageDialog(this, "删除失败！");
			return;
		}
		// 刷新面板与数字
		this.updatePanel();
	}

	/**
	 * 根据ID查询
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void checkByRole() throws RemoteException {
		String role = (String) this.role_box.getSelectedItem();
		this.userModel = new UserTable(this.controller, this.institutionVO);
		if (!this.userModel.checkByRole(role)) {
			JOptionPane.showMessageDialog(this, "该职业暂无用户！");
			return;
		}
		// 刷新面板
		this.userTable.setModel(this.userModel);
		this.decorateTable();
		this.southPanel.repaint();
		// 设置返回标志
		this.flag_back = true;
	}

	/**
	 * 根据职位查询
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void checkByID() throws RemoteException {
		String ID = this.jtf.getText();
		this.userModel = new UserTable(this.controller, this.institutionVO);
		if (!this.userModel.checkByID(ID)) {
			JOptionPane.showMessageDialog(this, "无此用户！");
			return;
		}
		// 刷新面板
		this.userTable.setModel(this.userModel);
		this.decorateTable();
		this.southPanel.repaint();
		// 设置返回标志
		this.flag_back = true;
	}

	/**
	 * 返回操作，分情况
	 * @throws RemoteException 
	 */
	private void back() throws RemoteException {
		// 如果未进行查询，或查询未成功
		if (!this.flag_back) {
			this.setVisible(false);
			this.institutionPanel.setVisible(true);
			// 使得人员管理返回时，人员机构按钮可以使用
			this.institutionPanel.setButtonInUse();
		}
		// 查询成功，返回上层界面
		else {
			// 设置新的列表模型
			this.userModel = new UserTable(this.controller, this.institutionVO);
			this.userModel.init();
			this.userTable.setModel(this.userModel);
			this.decorateTable();
			// 刷新数字
			this.southPanel.repaint();
			// 标志更改
			this.flag_back = false;
		}

	}

	public void mouseClicked(MouseEvent e) {
		try{
		// 返回
		if (e.getSource() == this.back_lab) {
			this.back();
		}
		// 增加员工
		else if (e.getSource() == this.add_lab) {
			this.addUser();
		}
		// 删除员工
		else if (e.getSource() == this.del_lab) {
			this.deleteUser();
		}
		// 修改员工信息
		else if (e.getSource() == this.modify_lab) {
			this.modifyUser();
		}
		// 查询员工信息
		else if (e.getSource() == this.check_lab) {
			// 通过ID查询
			if (this.ID_radioButton.isSelected()) {
				this.checkByID();
			}
			// 通过职业查询
			else if (this.role_radioButton.isSelected()) {
				this.checkByRole();
			} else {
				JOptionPane.showMessageDialog(this, "请选择一项进行查询！");
			}
		}
		}catch(RemoteException e1){
			this.disconnectInformer.findDisconnect();
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

	public void actionPerformed(ActionEvent e) {
		// 选择通过ID进行查询
		if (e.getSource() == this.ID_radioButton) {
			this.role_box.setVisible(false);
			this.jtf.setVisible(true);
		}
		// 选择通过职业进行查询
		else if (e.getSource() == this.role_radioButton) {
			this.jtf.setVisible(false);
			this.role_box.setVisible(true);
		}

	}

}
