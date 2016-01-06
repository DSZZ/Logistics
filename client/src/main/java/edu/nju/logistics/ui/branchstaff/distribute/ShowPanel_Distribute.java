package edu.nju.logistics.ui.branchstaff.distribute;

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
import edu.nju.logistics.ui.branchstaff.distribute.chooseCourier.ChooseCourierPanel;
import edu.nju.logistics.ui.branchstaff.distribute.chooseOrder.ChooseOrderPanel;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class ShowPanel_Distribute extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4865860159421225937L;
	
	private OrderManagementService orderManagement;
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	
	private JPanel chooseOrderPanel;
	private JPanel chooseCourierPanel;
	
	private JPanel presentPanel;
	
	private ArrayList<OrderVO> selectedOrderList;

	public ShowPanel_Distribute(OrderManagementService orderManagement,
			BranchManagementService branchManagement,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.orderManagement = orderManagement;
		this.branchManagement = branchManagement;
		
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		initialize();
	}

	private void initialize() {
		chooseOrderPanel = new ChooseOrderPanel(orderManagement, this,disconnectInformer);
		chooseOrderPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		chooseCourierPanel = new ChooseCourierPanel(branchManagement, this,disconnectInformer);
		chooseCourierPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		addCancelButton(chooseCourierPanel);
		
		changePanelTo(chooseOrderPanel);
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

	
	private void changePanelTo(JPanel panel){
		if(presentPanel != null) remove(presentPanel);
		
		presentPanel = panel;
		add(panel);
		panel.repaint();
	}

	public void chooseOrder(ArrayList<OrderVO> selectedOrderList) {
		this.selectedOrderList = selectedOrderList;
		
		changePanelTo(chooseCourierPanel);
	}

	public void chooseCourier(UserVO courier) {
		try {
			orderManagement.distributeOrderToCourier(selectedOrderList,courier.getId());
			GlobalHintInserter.insertGlobalHint("分配成功！");
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		initialize();
	}
}
