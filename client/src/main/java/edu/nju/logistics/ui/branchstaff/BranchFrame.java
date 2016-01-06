package edu.nju.logistics.ui.branchstaff;

import javax.swing.JPanel;

import edu.nju.logistics.bl.impl.branchmanagement.BranchManagementImpl;
import edu.nju.logistics.bl.impl.ordermanagement.OrderManagementImpl;
import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.CommonShowPanel;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car.CarService;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver.DriverService;
import edu.nju.logistics.ui.branchstaff.distribute.ShowPanel_Distribute;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.branchstaff.receive.ShowPanel_Receive;
import edu.nju.logistics.ui.branchstaff.recordPayment.ShowPanel_RecordPayment;
import edu.nju.logistics.ui.main.MainFrame;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.reconnection.ReconnectPanel;
import edu.nju.logistics.ui.util.reconnection.ReconnectSuccessObserver;
import edu.nju.logistics.vo.UserVO;

public class BranchFrame extends MainFrame implements ReconnectSuccessObserver,DisconnectInformer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -295621759401155445L;

	private OrderManagementService orderManagement;
	private BranchManagementService branchManagement;
	
	JPanel SHOWPANEL_PAYMENT;	
	JPanel SHOWPANEL_CAR;
	JPanel SHOWPANEL_DISTRIBUTE;
	JPanel SHOWPANEL_DRIVER;
	JPanel SHOWPANEL_RECEIVE;
	JPanel SHOWPANEL_LOAD;
	ReconnectPanel SHOWPANEL_RECONNECT;
	
	JPanel leftPanel;
	
	private void initLeftPanel() {
		leftPanel = new BranchLeftPanel(this);
		leftPanel.setBounds(10, SHOWPANEL_INIT_Y, SHOWPANEL_INIT_X, SHOWPANEL_HEIGHT);
		this.add(leftPanel);
	}
	
	protected JPanel initShowPanel() {
		initLeftPanel();
		
		
		SHOWPANEL_RECONNECT = new ReconnectPanel(this);
		SHOWPANEL_RECONNECT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		try {
			orderManagement = new OrderManagementImpl(userVO);
			branchManagement = new BranchManagementImpl(userVO);
		} catch (Exception e) {
			findDisconnect();
			return null;
		}
		
		
		SHOWPANEL_PAYMENT = new ShowPanel_RecordPayment(orderManagement,this);
		SHOWPANEL_PAYMENT.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_LOAD = new ShowPanel_Load(orderManagement,branchManagement,userVO,this);
		SHOWPANEL_LOAD.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_CAR = new CommonShowPanel(new CarService(branchManagement,this));
		SHOWPANEL_CAR.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_DRIVER = new CommonShowPanel(new DriverService(branchManagement,this));
		SHOWPANEL_DRIVER.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_RECEIVE = new ShowPanel_Receive(branchManagement,userVO.getId(),this);
		SHOWPANEL_RECEIVE.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		SHOWPANEL_DISTRIBUTE = new ShowPanel_Distribute(orderManagement,branchManagement,this);
		SHOWPANEL_DISTRIBUTE.setBounds(SHOWPANEL_INIT_X, SHOWPANEL_INIT_Y,
				SHOWPANEL_WIDTH, SHOWPANEL_HEIGHT);
		
		this.showPanel = SHOWPANEL_DISTRIBUTE;
		return showPanel;
	}
	
	public BranchFrame(UserVO vo) {
		super(vo);
	}
	
	public void changeShowPanelTo(JPanel showPanel){
		if(showPanel == null) return;
		
		if(this.showPanel != null)
			mainPanel.remove(this.showPanel);
		this.showPanel = showPanel;
		mainPanel.add(showPanel);
		showPanel.repaint();
	}
	
	@Override
	public void findDisconnect() {
		SHOWPANEL_PAYMENT = null;
		
		SHOWPANEL_LOAD = null;
		
		SHOWPANEL_CAR = null;
		
		SHOWPANEL_DRIVER = null;
		
		SHOWPANEL_RECEIVE = null;
		
		SHOWPANEL_DISTRIBUTE = null;
		
		changeShowPanelTo(SHOWPANEL_RECONNECT);
		SHOWPANEL_RECONNECT.startReconnection();
		
	}

	@Override
	public void informReconnectSuccess() {
		mainPanel.remove(SHOWPANEL_RECONNECT);
		initShowPanel();
		changeShowPanelTo(SHOWPANEL_DISTRIBUTE);
		
	}
}
