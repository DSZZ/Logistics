package edu.nju.logistics.ui.branchstaff.load;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.load.check.CheckPanel;
import edu.nju.logistics.ui.branchstaff.load.chooseCar.ChooseCarPanel;
import edu.nju.logistics.ui.branchstaff.load.chooseDestination.ChooseDestinationPanel;
import edu.nju.logistics.ui.branchstaff.load.chooseDriver.ChooseDriverPanel;
import edu.nju.logistics.ui.branchstaff.load.chooseOrder.ChooseOrderPanel;
import edu.nju.logistics.ui.branchstaff.load.inputPersonInfo.InputPersonInfoPanel;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowPanel_Load extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1637613893054225505L;
	private OrderManagementService orderManagement;
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	
	private JPanel chooseOrderPanel;
	private JPanel choosedestinationPanel;
	private JPanel chooseCarPanel;
	private JPanel chooseDriverPanel;
	private JPanel inputPersonInfoPanel;
	
	private JPanel presentPanel;
	private DataContainer dataContainer;
	private UserVO uservo;

	public ShowPanel_Load(OrderManagementService orderManagement
			,BranchManagementService branchManagement,UserVO uservo
			,DisconnectInformer disconnectInformer) {
		this.uservo = uservo;
		this.orderManagement = orderManagement;
		this.branchManagement = branchManagement;
		this.disconnectInformer = disconnectInformer;
		dataContainer = new DataContainer();
		
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		initialize();
	}
	
	private void addCancelButton(JPanel showPanel){
		JButton cancelButton = new ZYXButton("取消");
		cancelButton.setBounds(800, 290, 160, 40);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				initialize();
			}
		});
		showPanel.add(cancelButton);
	}
	
	public void initialize(){
		chooseOrderPanel = new ChooseOrderPanel(orderManagement,this,disconnectInformer);
		chooseOrderPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		choosedestinationPanel = new ChooseDestinationPanel(branchManagement,this,disconnectInformer);
		addCancelButton(choosedestinationPanel);
		choosedestinationPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		chooseCarPanel = new ChooseCarPanel(branchManagement, this,disconnectInformer);
		addCancelButton(chooseCarPanel);
		chooseCarPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		chooseDriverPanel = new ChooseDriverPanel(branchManagement, this,disconnectInformer);
		addCancelButton(chooseDriverPanel);
		chooseDriverPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		inputPersonInfoPanel = new InputPersonInfoPanel(this,BranchFrame.SHOWPANEL_WIDTH
				, BranchFrame.SHOWPANEL_HEIGHT);
		addCancelButton(inputPersonInfoPanel);
		
		changePanelTo(chooseOrderPanel);
		
//		JPanel checkPanel = new CheckPanel(new DataContainer(orderManagement.getStayOrderList(),"00250003","新街口营业厅"
//				,"00250001015","00250001107","卡梦然","李凯","孟鑫"
//				,branchManagement.getTransferFee(orderManagement.getStayOrderList())), branchManagement
//				, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT,this,uservo);
//		changePanelTo(checkPanel);
	}

	public void chooseOrder(ArrayList<OrderVO> selectedOrderList) {
		dataContainer.selectedOrderList = selectedOrderList;
		try {
			dataContainer.fee = branchManagement.getTransferFee(selectedOrderList);
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		changePanelTo(choosedestinationPanel);
	}

	/**
	 * 如果意图发往营业厅，则id为营业厅号码，如果意图发往本地中转中心，则id为null
	 * @param id
	 */
	public void chooseDestination(String id, String destinationname) {
		if(id == null || id.length() < 1){
			id = branchManagement.getUpperCenterID();
			try {
				destinationname = branchManagement.getUpperCenterName() + "中转中心";
			} catch (RemoteException e) {
				disconnectInformer.findDisconnect();
				return;
			}
		}else{
			destinationname += "营业厅";
		}
		dataContainer.destinationid = id;
		dataContainer.destinationname = destinationname;
		changePanelTo(chooseCarPanel);
	}

	public void chooseCar(String id) {
		dataContainer.carid = id;
		changePanelTo(chooseDriverPanel);
	}

	public void chooseDriver(String id, String drivername) {
		dataContainer.driverid = id;
		dataContainer.drivername = drivername;
		changePanelTo(inputPersonInfoPanel);
	}

	public void inputObserverAndSuperCargo(String observer, String supercargo) {
		dataContainer.observer = observer;
		dataContainer.supercargo = supercargo;
		
		JPanel checkPanel = new CheckPanel(dataContainer, branchManagement
				, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT,this,uservo,disconnectInformer);
		addCancelButton(checkPanel);
		changePanelTo(checkPanel);
	}
	
	private void changePanelTo(JPanel panel){
		if(presentPanel != null) remove(presentPanel);
		
		presentPanel = panel;
		add(panel);
		panel.repaint();
	}
}
