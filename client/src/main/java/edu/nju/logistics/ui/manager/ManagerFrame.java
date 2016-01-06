package edu.nju.logistics.ui.manager;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.ui.functionButton.MyButton;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

/**
 * 总经理操作界面
 * 
 * @author 董轶波
 *
 */
@SuppressWarnings("serial")
public class ManagerFrame extends MainFrame implements ReconnectSuccessObserver, DisconnectInformer {

	public static void main(String[] args) {
		new ManagerFrame(new UserVO("1111111", "dongyibo", "总经理"));
	}
//
//	/**
//	 * 查询日志图标
//	 */
//	private static final ImageIcon LOG = new ImageIcon("Image/manager/log.png");
//	/**
//	 * 审批单据图标
//	 */
//	private static final ImageIcon SHEET = new ImageIcon("Image/manager/sheet.png");
//	/**
//	 * 制定常量图标
//	 */
//	private static final ImageIcon CONSTANT = new ImageIcon("Image/manager/constant.png");
//	/**
//	 * 制定薪水策略图标
//	 */
//	private static final ImageIcon SALARY = new ImageIcon("Image/manager/salary.png");
//	/**
//	 * 查看报表图标
//	 */
//	private static final ImageIcon TABLE = new ImageIcon("Image/manager/table.png");
//	/**
//	 * 人员机构管理图标
//	 */
//	private static final ImageIcon INSTITUTION = new ImageIcon("Image/manager/institution.png");
//	/**
//	 * 成本收益表图标
//	 */
//	private static final ImageIcon BALANCE_CHART = new ImageIcon("Image/manager/balanceChart.png");
//	/**
//	 * 经营情况表图标
//	 */
//	private static final ImageIcon SITUTATION_REPORT = new ImageIcon("Image/manager/situtationReport.png");
	
	public static boolean flag_ins_other = true;

	public static boolean flag_tableForIns = true;

	private LogCheckPanel logCheckPanel = null;

	private SheetApprovePanel sheetApprovePanel = null;

	private ConstantSetPanel constantSetPanel = null;

	private SalaryMakePanel salaryMakePanel = null;

	private SituationReportPanel situationReportPanel = null;

	private BalanceChartPanel balanceChartPanel = null;

	private InstitutionPanel insAndUserPanel = null;

	private JPanel[] panels = null;

	private MyButton lab_log = null, lab_sheet = null, lab_constant = null, lab_salary = null, lab_table = null,
			lab_institution = null, lab_BC = null, lab_SR = null;

	private MyButton[] labels = null;

	private Cursor cursor = null;

	private HashMap<MyButton, JPanel> map = null;

	private boolean flag_table_other = true;

	private boolean flag_table = true;
	/**
	 * 总经理经营管理控制器
	 */
	private OperationManagementBLService controller = null;
	/**
	 * 断线重连面板
	 */
	private ReconnectPanel reconnectPanel = null;
	
	private MyButton temp=null;

	public ManagerFrame(UserVO vo) {
		super(vo);
		// 创建断线重连面板
		this.reconnectPanel = new ReconnectPanel(this);
		this.reconnectPanel.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y, SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		this.reconnectPanel.setVisible(false);
		this.getMainPanel().add(this.reconnectPanel);
       //尝试初始化所有组件以及控制器
		this.initAllComponentsAndController();
	}
	/**
	 * 尝试初始化所有组件以及控制器
	 */
	private void initAllComponentsAndController(){
		try {
			// 创建总经理业务逻辑控制器
			this.controller = new OperationManagementController();
			// 创建所有按钮
			this.createButton();
			// 初始化所有的功能面板
			this.initPanel();
			
			this.repaint();
		} catch (RemoteException e) {
			System.out.println("网络断线");
			this.findDisconnect();
		}
	}
	/**
	 * 初始化所有的功能面板
	 */
	private void initPanel() {
		this.logCheckPanel = new LogCheckPanel(this.controller, this);
		this.sheetApprovePanel = new SheetApprovePanel(this.controller, this, this);
		this.salaryMakePanel = new SalaryMakePanel(this.controller, this);
		this.constantSetPanel = new ConstantSetPanel(this.controller, this);
		this.situationReportPanel = new SituationReportPanel(this.controller, this);
		this.balanceChartPanel = new BalanceChartPanel(this.controller, this);
		this.insAndUserPanel = new InstitutionPanel(this.controller, this, this.showPanel, this.labels, this);

		this.panels = new JPanel[] { this.logCheckPanel, this.sheetApprovePanel, this.salaryMakePanel,
				this.constantSetPanel, this.situationReportPanel, this.balanceChartPanel, this.insAndUserPanel };

		this.showPanel.setLayout(null);
		for (JPanel panel : panels) {
			this.showPanel.add(panel);
			panel.setVisible(false);
		}

		this.map = new HashMap<MyButton, JPanel>();
		this.map.put(this.lab_log, this.logCheckPanel);
		this.map.put(this.lab_sheet, this.sheetApprovePanel);
		this.map.put(this.lab_salary, this.salaryMakePanel);
		this.map.put(this.lab_constant, this.constantSetPanel);
		this.map.put(this.lab_table, this.situationReportPanel);
		this.map.put(this.lab_institution, this.insAndUserPanel);

		this.repaint();
	}

	/**
	 * 创建所有按钮
	 */
	private void createButton() {
		this.cursor = new Cursor(Cursor.HAND_CURSOR);

		this.lab_log = new MyButton("查询日志");
		this.lab_sheet =new MyButton("审批单据");
		this.lab_salary = new MyButton("薪水策略");
		this.lab_constant = new MyButton("制定常量");
		this.lab_table = new MyButton("查看报表");
		this.lab_institution = new MyButton("人员机构");

		this.labels = new MyButton[] { this.lab_log, this.lab_sheet, this.lab_salary, this.lab_constant, this.lab_table,
				this.lab_institution };

		for (int i = 0; i < labels.length; i++) {
			this.labels[i].setBounds(BUTTON_X, BUTTON_Y + GAP * i, BUTTON_WIDTH, BUTTON_HEIGHT);
			this.labels[i].addMouseListener(this);
			this.labels[i].setCursor(this.cursor);
			this.mainPanel.add(this.labels[i]);
		}

		this.lab_BC = new MyButton("成本收益表");
		this.lab_SR = new MyButton("经营情况表");
		this.lab_BC.setBounds(BUTTON_X, 530, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.lab_SR.setBounds(BUTTON_X, 530+GAP, BUTTON_WIDTH, BUTTON_HEIGHT);
		this.lab_SR.setCursor(this.cursor);
		this.lab_BC.setCursor(this.cursor);
		this.lab_BC.setVisible(false);
		this.lab_SR.setVisible(false);
		this.lab_BC.addMouseListener(this);
		this.lab_SR.addMouseListener(this);
		this.mainPanel.add(this.lab_BC);
		this.mainPanel.add(this.lab_SR);
	}

	/**
	 * 处理选中的面板可见，其他不可见
	 * 
	 * @param panel
	 */
	private void handlePanel(JPanel panel) {
		for (JPanel p : this.panels) {
			p.setVisible(false);
		}
		panel.setVisible(true);
	}

	/**
	 * 点选查看报表按钮
	 */
	private void selectMainButton(MouseEvent e) {
		// 遍历按钮
		Set<Entry<MyButton, JPanel>> entryset = this.map.entrySet();
		for (Entry<MyButton, JPanel> arg : entryset) {
			// 如果是功能按钮
			if (e.getSource() == arg.getKey()) {
				// 如果是查看报表按钮
				if (e.getSource() == this.lab_table) {
					if (flag_tableForIns) {
						//this.temp=this.lab_table;
						if (this.flag_table) {
							this.lab_BC.setVisible(true);
							this.lab_SR.setVisible(true);
							this.flag_table = false;
							for (MyButton label : this.labels) {
								if (label != this.lab_table) {
									label.setEnabled(false);
									label.setEnableFalseIcon();
								}
							}
							this.flag_table_other = false;
						} else {
							this.lab_BC.setVisible(false);
							this.lab_SR.setVisible(false);
							this.flag_table = true;
							for (MyButton label : this.labels) {
								if (label != this.lab_table) {
									label.setEnabled(true);
									label.setEnableTrueIcon();
								}
							}
							this.flag_table_other = true;
						}
					}
				}
				// 其他功能按钮
				else {
					if (this.flag_table_other) {
						// 其他功能按钮
						if (!flag_ins_other) {
							return;
						}
						this.lab_BC.setVisible(false);
						this.lab_SR.setVisible(false);
						if(this.temp!=null){
						//按钮还原状态
						this.temp.restore();
						}
						this.temp=(MyButton) e.getSource();
						//按钮设置为被点击状态
						this.temp.clicked();
						this.handlePanel(this.map.get(e.getSource()));
					}
				}
			}

		}
		this.repaint();

	}

	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		this.selectMainButton(e);
		//成本收益表
		if (e.getSource() == this.lab_BC) {
//			this.temp.restore();
//			this.temp=(MyButton) e.getSource();
//			this.temp.clicked();
			this.handlePanel(this.balanceChartPanel);
			//开启线程
			this.balanceChartPanel.startThread();
		}
		//经营情况表
		else if (e.getSource() == this.lab_SR) {
//			this.temp.restore();
//			this.temp=(MyButton) e.getSource();
//			this.temp.clicked();
			this.handlePanel(this.situationReportPanel);
		}
	}
	/**
	 * 服务器断线并等待重连
	 */
	@Override
	public void findDisconnect() {
		//显示面板隐藏
		this.showPanel.setVisible(false);
		//重连面板显示
		this.reconnectPanel.setVisible(true);
		//开启断线重连线程
		this.reconnectPanel.startReconnection();
	}
	/**
	 * 服务器已连接，再次初始化所有组件及控制器
	 */
	@Override
	public void informReconnectSuccess() {
		//隐藏重连面板
		this.reconnectPanel.setVisible(false);
		//移除组件
		this.removeAllComponents();
		//显示面板显示
		this.showPanel.setVisible(true);
		//再次初始化所有组件以及控制器
		this.initAllComponentsAndController();
		//初始化所有flag标志
		flag_tableForIns =true;
		flag_ins_other=true;
		this.flag_table_other=true;
		this.flag_table=true;
	}
	/**
	 * 移除所有组件
	 */
	private void removeAllComponents(){
		//移除显示面板的组件
		this.showPanel.removeAll();
		//移除按钮组件
		if (this.labels != null) {
			for (int i = 0; i < this.labels.length; i++) {
				this.getMainPanel().remove(this.labels[i]);
			}
		}
		if(this.lab_BC!=null){
		this.getMainPanel().remove(this.lab_BC);
		}
		if(this.lab_SR!=null){
		this.getMainPanel().remove(this.lab_SR);
		}
	}
}
