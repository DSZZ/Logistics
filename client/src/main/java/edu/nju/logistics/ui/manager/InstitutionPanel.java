package edu.nju.logistics.ui.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.accountant.main.MyTableHandler;
import edu.nju.logistics.ui.commonButton.commonButton;
import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.InstitutionVO;

@SuppressWarnings("serial")
public class InstitutionPanel extends JPanel implements MouseListener {
//
//	private static final ImageIcon ADD = new ImageIcon("Image/manager/add.png");
//
//	private static final ImageIcon DEL = new ImageIcon("Image/manager/delete.png");
//
//	private static final ImageIcon MODIFY = new ImageIcon("Image/manager/modify.png");
//
//	private static final ImageIcon CHECK = new ImageIcon("Image/manager/check.png");
//
//	private static final ImageIcon USER = new ImageIcon("Image/manager/user.png");
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

	private JLabel  info = null;

	private commonButton add_lab = null,back_lab = null, check_lab = null, del_lab = null, modify_lab = null,
			user_lab = null;
	
	private Cursor cursor = null;

	private JScrollPane jsp = null;

	private JTable insTable = null;

	private InstitutionTable insModel = null;

	private UserPanel userPanel = null;
	/**
	 * 总经理经营管理控制器
	 */
	private OperationManagementBLService controller = null;

	private JPanel showPanel = null;

	private MyButton[] labels = null;

	private AddInsDialog addInsDialog = null;

	private ModifyInsDialog modifyInsDialog = null;

	private MainFrame owner=null;
	
	private DisconnectInformer disconnectInformer=null;
	
	public InstitutionPanel(OperationManagementBLService controller,MainFrame owner, JPanel showPanel, MyButton[] labels,DisconnectInformer disconnectInformer) {
		try{
		this.owner=owner;
		this.showPanel = showPanel;
		this.labels = labels;

		this.setSize(SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.setLayout(null);
		this.setOpaque(false);
		this.cursor = new Cursor(Cursor.HAND_CURSOR);
		this.disconnectInformer=disconnectInformer;
		this.controller = controller;

		// 初始化北部面板
		this.initNorthPanel();
		// 初始化中心面板
		this.initCenterPanel();
		// 初始化南部面板
		this.initSouthPanel();
		}catch(RemoteException  e){
			e.printStackTrace();
		}
	}
	/**
	 * 初始化北部面板
	 */
	private void initNorthPanel() {
		this.northPanel = new JPanel();
		this.northPanel.setLayout(null);
		this.northPanel.setOpaque(false);
		//this.northPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.northPanel.setBounds(5, 5, 970, 40);

		this.jtf = new JTextField();
		this.jtf.setBounds(40, 7, 160, 25);
		this.northPanel.add(this.jtf);

		this.info = new JLabel("(请输入所查询机构的ID)");
		this.info.setBounds(210, 10, 150, 20);
		this.info.setForeground(Color.RED);
		this.northPanel.add(this.info);

		this.check_lab = new commonButton("查看");
		this.check_lab.setBounds(400, 0,100, 40);
		this.check_lab.setCursor(this.cursor);
		this.check_lab.addMouseListener(this);
		this.northPanel.add(this.check_lab);

		this.add(this.northPanel);

		this.repaint();
	}

	/**
	 * 初始化中心面板
	 * @throws RemoteException 
	 */
	private void initCenterPanel() throws RemoteException {
		this.centerPanel = new JPanel(new BorderLayout());
		this.centerPanel.setOpaque(false);
		this.centerPanel.setBounds(5, 50, 970, 460);

		this.insModel = new InstitutionTable(this.controller);
		this.insModel.init();
		this.insTable = new JTable(this.insModel);
//		this.insTable.setOpaque(false);
		//渲染器
//		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//		//render单元格的属性
//		renderer.setOpaque(false);
//		//遍历表格中所有列，将其渲染器设置为renderer
//		for(int i = 0 ; i < this.insTable.getColumnCount() ; i ++)
//		{
//			this.insTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
//		}

		this.insTable.getTableHeader().setFont(new Font("黑体", Font.BOLD, 14));
		this.insTable.setFont(new Font("楷体", Font.PLAIN, 14));
		//this.insTable.setShowHorizontalLines(false);
		//this.insTable.setShowVerticalLines(false);
		// 不可整列移动
		this.insTable.getTableHeader().setReorderingAllowed(false);
		// 不可拉动表格
		this.insTable.getTableHeader().setResizingAllowed(false);
		this.jsp = new JScrollPane(this.insTable);
		//this.jsp.setOpaque(false);
		//this.jsp.getViewport().setOpaque(false);
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
				g.drawString("记录条数为 " + insModel.getRowCount(), 5, 25);
			}
		};
		this.southPanel.setLayout(null);
		this.southPanel.setOpaque(false);
		this.southPanel.setBounds(5, 515, 970, 40);

		this.user_lab = new commonButton("人员管理");
		this.user_lab.setBounds(515, 0, 100, 40);
		this.user_lab.setCursor(this.cursor);
		this.user_lab.addMouseListener(this);
		this.southPanel.add(this.user_lab);

		this.add_lab = new commonButton("添加");
		this.add_lab.setBounds(635, 0, 100, 40);
		this.add_lab.setCursor(this.cursor);
		this.add_lab.addMouseListener(this);
		this.southPanel.add(this.add_lab);

		this.del_lab = new commonButton("删除");
		this.del_lab.setBounds(755, 0, 100, 40);
		this.del_lab.setCursor(this.cursor);
		this.del_lab.addMouseListener(this);
		this.southPanel.add(this.del_lab);

		this.modify_lab = new commonButton("修改");
		this.modify_lab.setBounds(875, 0, 100, 40);
		this.modify_lab.setCursor(this.cursor);
		this.modify_lab.addMouseListener(this);
		this.southPanel.add(this.modify_lab);

		this.back_lab = new commonButton("返回");
		this.back_lab.setBounds(395,0, 100, 40);
		this.back_lab.setCursor(this.cursor);
		this.back_lab.addMouseListener(this);
		this.back_lab.setVisible(false);
		this.southPanel.add(this.back_lab);

		this.add(this.southPanel);
		this.repaint();
	}
	/**
	 * 表格装饰
	 */
	private void decorateTable(){
		MyTableHandler.initTable(this.insTable);
		MyTableHandler.initSpane(this.jsp, this.insTable);
	}
	/**
	 * 点击人员管理，进行选中机构下的员工管理
	 */
	private void userOperation() {
		int rowNum = this.insTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择机构进行员工管理!");
			return;
		}
		String location = (String) this.insModel.getValueAt(rowNum, 0);
		String institutionID = (String) this.insModel.getValueAt(rowNum, 1);
		String type = (String) this.insModel.getValueAt(rowNum, 2);
		InstitutionVO institutionVO = new InstitutionVO(location, institutionID, type);
		// 机构管理面板不显示，切换到相应的员工管理面板,人员机构按钮不可点击
		this.setVisible(false);
		for (MyButton label : labels) {
			label.setEnabled(false);
			label.setEnableFalseIcon();
		}
		ManagerFrame.flag_tableForIns = false;
		ManagerFrame.flag_ins_other = false;
		//启动人员管理窗口
		this.userPanel = new UserPanel(this.controller,this.owner,this, institutionVO,this.disconnectInformer);
		// 把userPanel添加到显示区
		this.showPanel.add(this.userPanel);

	}

	/**
	 * 使得人员管理返回时，人员机构按钮可以使用
	 */
	public void setButtonInUse() {
		for (MyButton label : labels) {
			label.setEnabled(true);
			label.setEnableTrueIcon();
		}
		ManagerFrame.flag_tableForIns = true;
		ManagerFrame.flag_ins_other = true;
	}
	/**
	 * 增加机构
	 * @throws RemoteException 
	 */
	private void addInstitution() throws RemoteException{
		//启动增加机构窗口
		this.addInsDialog = new AddInsDialog(this.owner, "添加机构", true, this.controller,this.disconnectInformer);
		this.addInsDialog.setVisible(true);
		// 如果添加成功，刷新面板
		if (this.addInsDialog.getFlag()) {
			this.updatePanel();
		}
	}
	/**
	 * 删除机构
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void deleteInstitution() throws  RemoteException{
		JOptionPane.showMessageDialog(this, "确定删除该机构？\n该机构下的员工也会被删除！");
		
		int rowNum =this.insTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		String ID=(String)this.insTable.getValueAt(rowNum, 1);
		if(!this.controller.deleteInstitution(ID)){
			JOptionPane.showMessageDialog(this, "该中转中心尚有营业厅，不能删除！");
			return;
		}
		//刷新面板
		this.updatePanel();
	
	}
	/**
	 * 查询机构信息
	 * @throws RemoteException 
	 * @throws HeadlessException 
	 */
	private void checkInstitution() throws RemoteException {
		String ID = this.jtf.getText();
		this.insModel = new InstitutionTable(this.controller);
		if (!this.insModel.checkInstitution(ID)) {
			JOptionPane.showMessageDialog(this, "无此机构！");
			this.insModel.init();
			return;
		}
		// 返回按钮出现
		this.back_lab.setVisible(true);
		// 刷新面板
		this.insTable.setModel(this.insModel);
		this.decorateTable();
		this.southPanel.repaint();
	}
	/**
	 * 修改机构信息
	 * @throws RemoteException 
	 */
	private void modifyInstitution() throws RemoteException{
		int rowNum =this.insTable.getSelectedRow();
		if (rowNum == -1) {
			JOptionPane.showMessageDialog(this, "请选择一行!");
			return;
		}
		String location=(String)this.insTable.getValueAt(rowNum, 0);
		String ID=(String)this.insTable.getValueAt(rowNum, 1);
		String type=(String)this.insTable.getValueAt(rowNum, 2);
		double rental=Double.parseDouble((String)this.insTable.getValueAt(rowNum, 3));
		InstitutionVO vo=new InstitutionVO(location,ID,type,rental);
		
		this.modifyInsDialog = new ModifyInsDialog(this.owner, "修改机构信息", 
				true, this.controller,vo,this.disconnectInformer);
		this.modifyInsDialog.setVisible(true);
		// 如果修改信息成功，刷新面板
		if (this.modifyInsDialog.getFlag()) {
			this.updatePanel();
		}
	}
	/**
	 * 返回到上层界面
	 * @throws RemoteException 
	 */
	private void backTO() throws RemoteException {
		// 设置新的列表模型
		this.insModel = new InstitutionTable(this.controller);
		// 刷新面板
		this.insModel.init();
		this.insTable.setModel(this.insModel);
		this.decorateTable();
		this.southPanel.repaint();
		// 返回按钮不显示
		this.back_lab.setVisible(false);
		// 刷新文本框
		this.jtf.setText("");
	}
	/**
	 * 刷新面板，更新数据
	 * @throws RemoteException 
	 */
	private void updatePanel() throws RemoteException{
		this.insModel = new InstitutionTable(this.controller);
		this.insModel.init();
		this.insTable.setModel(this.insModel);
		this.decorateTable();
		this.southPanel.repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
		try{
		// 点击人员管理
		if (e.getSource() == this.user_lab) {
			this.userOperation();
		}
		// 点击增加机构
		else if (e.getSource() == this.add_lab) {
			this.addInstitution();
		}
		// 点击删除机构
		else if (e.getSource() == this.del_lab) {
			this.deleteInstitution();
		}
		// 修改机构信息
		else if (e.getSource() == this.modify_lab) {
			this.modifyInstitution();
		}
		// 查询机构信息
		else if (e.getSource() == this.check_lab) {
			this.checkInstitution();
		}
		// 从查询机构返回到原界面
		else if (e.getSource() == this.back_lab) {
			this.backTO();
		}
		}catch(RemoteException e1){
//			System.out.println("catch exception");
//			ManagerFrame.flag_tableForIns = true;
//			ManagerFrame.flag_ins_other = true;
//			System.out.println(ManagerFrame.flag_tableForIns +"  "+ManagerFrame.flag_ins_other);
//			System.out.println("111111");
			this.disconnectInformer.findDisconnect();
		//	System.out.println("222222");
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
