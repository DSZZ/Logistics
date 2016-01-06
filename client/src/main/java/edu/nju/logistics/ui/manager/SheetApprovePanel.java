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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.SheetVO;

@SuppressWarnings("serial")
public class SheetApprovePanel extends JPanel implements MouseListener, ActionListener {
	/**
	 * 显示区宽度
	 */
	public static final int SHOWPANEL_WIDTH = 980;
	/**
	 * 显示区高度
	 */
	public static final int SHOWPANEL_HEIGHT = 560;

//	private static final ImageIcon CHANGE = new ImageIcon("Image/manager/switch.png");
//
//	private static final ImageIcon MASS = new ImageIcon("Image/manager/mass.png");
//
//	private static final ImageIcon APPROVAL = new ImageIcon("Image/manager/approval.png");
//
//	private static final ImageIcon NO_APPROVAL = new ImageIcon("Image/manager/noApproval.png");

	JPanel northPanel = null, centerPanel = null, southPanel = null;

	private JComboBox<String> box = null;

	private JLabel  main_lab = null, prompt_lab = null,  sheet_lab = null;

	private commonButton  noApproval_lab = null,change_lab = null,mass_lab = null,approval_lab = null;
	
	private Cursor cursor = null;

	private JScrollPane jsp = null;

	private JTable sheetTable = null;

	private SheetTable sheetModel = null;
	/**
	 * 总经理经营管理控制器
	 */
	private OperationManagementBLService controller = null;

	private String sheets[] = null;

	private boolean flag_isExist = false;

	private boolean flag_isChange = false;

	private ManagerFrame mainFrame = null;
	
	private DisconnectInformer disconnectInformer=null;

	public SheetApprovePanel(OperationManagementBLService controller, ManagerFrame mainFrame,DisconnectInformer disconnectInformer) {
		try {
			this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
			this.setLayout(null);
			this.setOpaque(false);
			this.cursor = new Cursor(Cursor.HAND_CURSOR);
			this.mainFrame = mainFrame;
			this.controller = controller;
			this.disconnectInformer=disconnectInformer;
			// 初始化北部面板
			this.initNorthPanel();
			// 初始化中心面板
			this.initCenterPanel();
			// 初始化南部面板
			this.initSouthPanel();
			//创建未审批提示
			this.createNoticeInfo();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建未审批的通知
	 * 
	 * @throws RemoteException
	 */
	private void createNoticeInfo() throws RemoteException {
		this.main_lab = new JLabel("存在未审批的单据！");
		this.main_lab.setForeground(Color.GREEN);
		this.main_lab.setFont(new Font("黑体", Font.BOLD, 16));
		this.main_lab.setVisible(false);
		// this.main_lab.setVisible(false);
		this.main_lab.setBounds(400, 20, 200, 30);
		this.mainFrame.getMainPanel().add(this.main_lab);

		// 通知有未审批单据
		Thread myThread=new Thread(){
		public void run(){
			while(true){
				try {
					notice();
					//System.out.println(111111);
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	myThread.start();
//		this.notice();
	}

	/**
	 * 初始化北部面板
	 */
	private void initNorthPanel() {
		this.northPanel = new JPanel();
		this.northPanel.setLayout(null);
		this.northPanel.setOpaque(false);
		this.northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.northPanel.setBounds(5, 5, 970, 40);

		this.sheet_lab = new JLabel("单据类型");
		this.sheet_lab.setFont(new Font("宋体", Font.BOLD, 14));
		this.sheet_lab.setBounds(10, 5, 70, 30);
		this.northPanel.add(this.sheet_lab);

		this.sheets = new String[] { "寄件单", "派件单", "入库单", "发货单", "收货单", "出库单", "付款单" };
		this.box = new JComboBox<>(sheets);
		this.box.addActionListener(this);
		this.box.setBounds(90, 7, 120, 25);
		this.northPanel.add(this.box);

		this.prompt_lab = new JLabel("显示: 全部单据");
		this.prompt_lab.setBounds(770, 5, 180, 30);
		this.prompt_lab.setFont(new Font("楷体", Font.BOLD, 16));
		this.prompt_lab.setForeground(Color.RED);
		this.northPanel.add(this.prompt_lab);

		this.add(this.northPanel);

		this.repaint();

	}

	/**
	 * 初始化中心面板
	 */
	private void initCenterPanel() {
		this.centerPanel = new JPanel(new BorderLayout());
		this.centerPanel.setOpaque(false);
		this.centerPanel.setBounds(5, 50, 970, 460);

		this.sheetModel = new SheetTable(this.controller);
		this.sheetModel.init();
		this.sheetTable = new JTable(this.sheetModel);
		this.sheetTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.sheetTable.setFont(new Font("楷体", Font.PLAIN, 14));
		// this.sheetTable.setHorizontalScrollBar(this.sheetTable.create);
//		this.sheetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// 不可整列移动
		this.sheetTable.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.sheetTable.getTableHeader().setResizingAllowed(false);
		this.jsp = new JScrollPane(this.sheetTable);
		// this.jsp.setViewportView(this.sheetTable);
//		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// 装饰表格
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
				g.drawString("记录条数为  " + sheetModel.getRowCount(), 5, 25);
			}
		};
		this.southPanel.setLayout(null);
		this.southPanel.setOpaque(false);
		this.southPanel.setBounds(5, 515, 970, 40);

		this.change_lab = new commonButton("全部");
		this.change_lab.setBounds(200, 0, 100, 40);
		this.change_lab.setCursor(this.cursor);
		this.change_lab.addMouseListener(this);
		this.change_lab.setEnabled(false);
		this.southPanel.add(this.change_lab);

		this.mass_lab = new commonButton("批量通过");
		this.mass_lab.setBounds(750, 0, 100, 40);
		this.mass_lab.setCursor(this.cursor);
		this.mass_lab.addMouseListener(this);
		this.mass_lab.setEnabled(false);
		this.southPanel.add(this.mass_lab);

		this.approval_lab = new commonButton("通过");
		this.approval_lab.setBounds(870, 0, 100, 40);
		this.approval_lab.setCursor(this.cursor);
		this.approval_lab.addMouseListener(this);
		this.approval_lab.setEnabled(false);
		this.southPanel.add(this.approval_lab);

		this.noApproval_lab = new commonButton("不通过");
		this.noApproval_lab.setBounds(630, 0, 100, 40);
		this.noApproval_lab.setCursor(this.cursor);
		this.noApproval_lab.addMouseListener(this);
		this.noApproval_lab.setEnabled(false);
		this.southPanel.add(this.noApproval_lab);

		this.add(this.southPanel);
		this.repaint();

	}

	/**
	 * 提示是否有单据未审批
	 * 
	 * @throws RemoteException
	 */
	private void notice() throws RemoteException {
		if (this.isNoApproval()) {
			this.main_lab.setVisible(true);
		} else {
			this.main_lab.setVisible(false);
		}
	}

	/**
	 * 提示是否有单据尚未审批
	 * 
	 * @return
	 * @throws RemoteException
	 */
	private boolean isNoApproval() throws RemoteException {

		// String type[] = new String[] { "寄件单", "派件单", "入库单", "发货单",
		// "收货单", "出库单", "付款单" };
		for (int i = 0; i < this.sheets.length; i++) {
			ArrayList<SheetVO> voList;
			voList = this.controller.sheetShow(this.sheets[i]);
			for (int j = 0; j < voList.size(); j++) {
				SheetVO vo = voList.get(j);
				if (vo.getState().equals("提交")) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 设置列宽
	 */
	private void decorateTable() {
		MyTableHandler.initTable(this.sheetTable);
		MyTableHandler.initSpane(this.jsp, this.sheetTable);
		// 设置列宽
		this.sheetTable.getColumnModel().getColumn(0).setPreferredWidth(220);
		this.sheetTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		this.sheetTable.getColumnModel().getColumn(2).setPreferredWidth(120);
		this.sheetTable.getColumnModel().getColumn(3).setPreferredWidth(120);
		this.sheetTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		this.sheetTable.getColumnModel().getColumn(5).setPreferredWidth(620);
		
		this.sheetTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	// 寄件单 装车单 营业厅到达单 收款单 派件单 中转中心到达单 入库单 中转单 出库单 付款单
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == this.box) {
				for (int i = 0; i < this.sheets.length; i++) {
					if (this.box.getSelectedItem().equals(this.sheets[i])) {
						this.sheetModel = new SheetTable(this.controller);
						if (!this.sheetModel.showByType(this.sheets[i])) {
							//JOptionPane.showMessageDialog(this, "该类型暂无单据！");
							GlobalHintInserter.insertGlobalHint("暂无该类型单据！");
							this.mass_lab.setEnabled(false);
							this.approval_lab.setEnabled(false);
							this.change_lab.setEnabled(false);
							this.noApproval_lab.setEnabled(false);
							this.flag_isExist = false;
						} else {
							this.noApproval_lab.setEnabled(true);
							this.mass_lab.setEnabled(true);
							this.approval_lab.setEnabled(true);
							this.change_lab.setEnabled(true);
							this.flag_isExist = true;
							this.flag_isChange = false;
						}
						this.prompt_lab.setText("显示: 全部单据");
						this.sheetTable.setModel(this.sheetModel);
						this.decorateTable();
						this.southPanel.repaint();
					}
				}
			}
		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (RemoteException e1) {
			this.disconnectInformer.findDisconnect();
		}

	}

	/**
	 * 审批某项单据
	 * 
	 * @throws RemoteException
	 */
	private void setApproval() throws RemoteException {
		int rowNum = this.sheetTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一项单据！");
			return;
		}
		String date = (String) this.sheetTable.getValueAt(rowNum, 0);
		String type = (String) this.sheetTable.getValueAt(rowNum, 1);
		String ID = (String) this.sheetTable.getValueAt(rowNum, 2);
		String employeeID = (String) this.sheetTable.getValueAt(rowNum, 3);
		String state = (String) this.sheetTable.getValueAt(rowNum, 4);
		String description = (String) this.sheetTable.getValueAt(rowNum, 5);
		if (!state.equals("提交")) {
			JOptionPane.showMessageDialog(this, "该单据已审批！");
			return;
		}
		SheetVO vo = new SheetVO(ID, employeeID, type, date, state, description);

		this.controller.setApproval(vo);

		this.prompt_lab.setText("显示: 全部单据");
		this.sheetModel = new SheetTable(this.controller);
		this.sheetModel.showByType(type);
		this.sheetTable.setModel(this.sheetModel);
		this.decorateTable();
		this.southPanel.repaint();
		this.flag_isChange = false;
		JOptionPane.showMessageDialog(this, "该单据生效！");

	}

	/**
	 * 批量通过
	 * 
	 * @throws RemoteException
	 */
	private void setMassApproval() throws RemoteException {
		String type = (String) this.box.getSelectedItem();
		ArrayList<SheetVO> list;
		list = this.controller.sheetShow(type);

		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			SheetVO vo = list.get(i);
			if (!vo.getState().equals("提交")) {
				count++;
			}
		}
		if (count == list.size()) {
			JOptionPane.showMessageDialog(this, "该类型单据已全审批！");
			return;
		}
		this.controller.setMassApproval(type);
		JOptionPane.showMessageDialog(this, "批量审批通过");
		this.sheetModel = new SheetTable(this.controller);
		this.sheetModel.showByType(type);
		this.sheetTable.setModel(this.sheetModel);
		this.prompt_lab.setText("显示: 全部单据");
		this.decorateTable();
		this.southPanel.repaint();
		this.flag_isChange = false;
		JOptionPane.showMessageDialog(this, "该类型单据全部生效！");

	}

	/**
	 * 设置某项为审批不通过
	 * 
	 * @throws RemoteException
	 */
	private void setNoApproval() throws RemoteException {
		int rowNum = this.sheetTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一项单据！");
			return;
		}
		// String date = (String) this.sheetTable.getValueAt(rowNum, 0);
		String type = (String) this.sheetTable.getValueAt(rowNum, 1);
		String ID = (String) this.sheetTable.getValueAt(rowNum, 2);
		// String employeeID = (String) this.sheetTable.getValueAt(rowNum,
		// 3);
		String state = (String) this.sheetTable.getValueAt(rowNum, 4);
		// String description = (String) this.sheetTable.getValueAt(rowNum,
		// 5);
		if (!state.equals("提交")) {
			JOptionPane.showMessageDialog(this, "该单据已审批！");
			return;
		}
		// SheetVO vo = new SheetVO(ID, employeeID, type, date, state,
		// description);

		this.controller.setNoApproval(ID);

		this.prompt_lab.setText("显示: 全部单据");
		this.sheetModel = new SheetTable(this.controller);
		this.sheetModel.showByType(type);
		this.sheetTable.setModel(this.sheetModel);
		this.decorateTable();
		this.southPanel.repaint();
		this.flag_isChange = false;
		JOptionPane.showMessageDialog(this, "该单据未通过审批\n请联系相关负责人！");

	}

	/**
	 * 全部 与 未审批 单据之间的切换
	 * 
	 * @throws RemoteException
	 * @throws HeadlessException
	 */
	private void switchInterface() throws RemoteException {
		if (!this.flag_isChange) {
			String type = (String) this.box.getSelectedItem();
			this.sheetModel = new SheetTable(this.controller);
			if (!this.sheetModel.showNoApproval(type)) {
				JOptionPane.showMessageDialog(this, "该类型单据已全通过审批！");
				this.sheetModel.showByType(type);
				return;
			}
			this.prompt_lab.setText("显示: 未审批单据");
			this.sheetTable.setModel(this.sheetModel);
			this.decorateTable();
			this.southPanel.repaint();
			this.flag_isChange = true;
		} else {
			this.prompt_lab.setText("显示: 全部单据");
			String type = (String) this.box.getSelectedItem();
			this.sheetModel = new SheetTable(this.controller);
			this.sheetModel.showByType(type);
			this.sheetTable.setModel(this.sheetModel);
			this.decorateTable();
			this.southPanel.repaint();
			this.flag_isChange = false;
		}

	}

	public void mouseClicked(MouseEvent e) {
		try{
		if (this.flag_isExist) {
			// 通过
			if (e.getSource() == this.approval_lab) {
				this.setApproval();
				this.notice();
			}
			// 批量通过
			else if (e.getSource() == this.mass_lab) {
				this.setMassApproval();
				this.notice();
			}
			// 不通过
			else if (e.getSource() == this.noApproval_lab) {
				this.setNoApproval();
				this.notice();
			}
			// 切换按钮
			else if (e.getSource() == this.change_lab) {
				this.switchInterface();
			}
		}
		}catch(RemoteException  e1){
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
}
