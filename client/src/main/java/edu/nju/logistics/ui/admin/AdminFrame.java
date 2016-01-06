package edu.nju.logistics.ui.admin;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import edu.nju.logistics.bl.impl.identitymanagement.IdentityManagementController;
import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

/**
 * 管理员操作界面
 * 
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class AdminFrame extends MainFrame implements MouseListener,ReconnectSuccessObserver, DisconnectInformer {

	public static void main(String[] args) {
		new AdminFrame(new UserVO("111111", "222222", "管理员"));
	}
//
//	private static final ImageIcon LABEL = new ImageIcon("Image/admin/label.png");

	private MyButton label = null;

	private JLabel title = null;

	private JLabel count = null;

	private JTable table = null;

	private AdminInitTable model = null;

	private JScrollPane jsp = null;

	private AdminOperation operation = null;

	// private boolean flag = true;

	private Cursor cursor = null;

	private IdentityManagementBLService controller = null;
	/**
	 * 断线重连面板
	 */
	private ReconnectPanel reconnectPanel = null;
	
	public AdminFrame(UserVO vo) {
		super(vo);
		//断线重连面板
		this.reconnectPanel = new ReconnectPanel(this);
		this.reconnectPanel.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y, SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.reconnectPanel.setVisible(false);
		this.getMainPanel().add(this.reconnectPanel);
		//初始化所有组件和控制器
		this.initAllComponentsAndController();
	}
	/**
	 * 初始化所有组件以及控制器
	 */
	private void initAllComponentsAndController(){
		try {
			this.controller = new IdentityManagementController();
			this.model = new AdminInitTable(this.controller);
			this.createButton();
			this.initPanel();
		} catch (RemoteException e) {
			System.out.println("网络断线");
			this.findDisconnect();
		}
	}
	/**
	 * 显示区域的内容
	 */
	private void initPanel() {
		// 处理北部
		this.showPanel.setLayout(null);
		this.title = new JLabel("人员情况变更");
		this.title.setFont(new Font("黑体", Font.BOLD, 16));
		this.title.setBounds(10, 5, 200, 30);
		this.showPanel.add(this.title);
		// 处理南部
		this.count = new JLabel("记录条数为 " + this.model.getRowCount());
		this.count.setFont(new Font("黑体", Font.BOLD, 16));
		this.count.setBounds(5, 535, 200, 20);
		this.showPanel.add(this.count);
		// 处理中部
		this.table = new JTable(model);
		this.table.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		// 不可整列移动
		this.table.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.table.getTableHeader().setResizingAllowed(false);
		this.table.setFont(new Font("楷体", Font.PLAIN, 14));
		this.jsp = new JScrollPane(this.table);
		this.jsp.setBounds(10, 40, 960, 495);
		//装饰表格
		this.decorateTable();
		this.showPanel.add(this.jsp);

		this.repaint();
	}

	/**
	 * 左边按钮区域
	 */
	private void createButton() {
		this.cursor = new Cursor(Cursor.HAND_CURSOR);

		this.label = new MyButton("账户管理");
		this.label.setBounds(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.label.addMouseListener(this);
		this.label.setCursor(this.cursor);
		this.mainPanel.add(this.label);

	}
	/**
	 * 装饰表格
	 */
	private void decorateTable(){
		MyTableHandler.initTable(this.table);
		MyTableHandler.initSpane(this.jsp, this.table);
	}
	/**
	 * 重写
	 */
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		if (e.getSource() == this.close) {
			if (this.operation != null) {
				this.operation.dispose();
			}
		} else if (e.getSource() == this.logoff2) {
			if (this.operation != null) {
				this.operation.dispose();
			}
		} else if (e.getSource() == this.label) {
			// if (this.flag) {
			// this.label.setEnabled(false);
			// this.flag = false;
			this.operation = new AdminOperation(this, "账户管理", true, this.controller,this);
			// }
		}
	}

	public JTable tableGetter() {
		return this.table;
	}

	public JLabel countGetter() {
		return this.count;
	}

	@Override
	public void findDisconnect() {
		// 显示面板隐藏
		this.showPanel.setVisible(false);
		// 重连面板显示
		this.reconnectPanel.setVisible(true);
		// 开启断线重连线程
		this.reconnectPanel.startReconnection();
	}

	@Override
	public void informReconnectSuccess() {
		// 隐藏重连面板
		this.reconnectPanel.setVisible(false);
		// 移除组件
		this.removeAllComponents();
		// 显示面板显示
		this.showPanel.setVisible(true);
		// 再次初始化所有组件以及控制器
		this.initAllComponentsAndController();
		
	}
	/**
	 * 移除组件
	 */
	private void removeAllComponents() {
		this.showPanel.removeAll();
		if(this.label!=null){
		this.getMainPanel().remove(this.label);
		}
	}

	// public JLabel labelGetter() {
	// return this.label;
	// }

	// public void setFlag() {
	// this.flag = !this.flag;
	// }

}
