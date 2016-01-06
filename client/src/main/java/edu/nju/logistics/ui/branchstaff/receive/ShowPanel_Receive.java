package edu.nju.logistics.ui.branchstaff.receive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.receive.check.CheckPanel;
import edu.nju.logistics.ui.branchstaff.receive.chooseStatus.ChooseStatusPanel;
import edu.nju.logistics.ui.branchstaff.receive.chooseTransferSheet.ChooseTransferSheetPanel;
import edu.nju.logistics.ui.commonButton.ZYXButton;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;

public class ShowPanel_Receive extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6262304603622918923L;

	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private JPanel presentPanel;
	
	private JPanel chooseTransferSheetPanel;
	private JPanel chooseStatusPanel;
	
	private TransferSheetVO sheetToReceive;
	private ReceiptState sheetStatus;
	private String staffid;

	public ShowPanel_Receive(BranchManagementService branchManagement,String staffid
			,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.staffid = staffid;
		this.branchManagement = branchManagement;
		
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		initialize();
	}

	private void initialize() {
		chooseTransferSheetPanel = new ChooseTransferSheetPanel(branchManagement,this,disconnectInformer);
		chooseTransferSheetPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		
		chooseStatusPanel = new ChooseStatusPanel(this, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
		addCancelButton(chooseStatusPanel);
		
		changePanelTo(chooseTransferSheetPanel);
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

	public void chooseTransferSheet(TransferSheetVO sheet) {
		sheetToReceive = sheet;
		
		changePanelTo(chooseStatusPanel);
	}

	public void chooseStatus(ReceiptState status) {
		sheetStatus = status;
		
		JPanel checkPanel = new CheckPanel(BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT
				, this, sheetToReceive, sheetStatus);
		addCancelButton(checkPanel);
		changePanelTo(checkPanel);
	}

	public void confirmReceive() {
		try {
			branchManagement.receiveTransferSheet(sheetToReceive.getID(), staffid, sheetStatus);
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
		}
		initialize();
	}
	
}
