package edu.nju.logistics.ui.branchstaff.receive.chooseTransferSheet;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.receive.ShowPanel_Receive;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;
import edu.nju.logistics.vo.TransferSheetVO;

public class ChooseTransferSheetPanel extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048636366272152104L;
	
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private ShowPanel_Receive owner;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tabelPanel;

	public ChooseTransferSheetPanel(BranchManagementService branchManagement
			, ShowPanel_Receive showPanel_receive,DisconnectInformer disconnectInformer) {
		this.branchManagement = branchManagement;
		this.disconnectInformer = disconnectInformer;
		owner = showPanel_receive;
		
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		initShowPanel();
	}

	private void initShowPanel() {
		
		
		ArrayList<TransferSheetVO> transferSheetList;
		try {
			transferSheetList = branchManagement.getLocalTransferSheetList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(transferSheetList == null || transferSheetList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无需要接收的中转单");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_ChooseTransferSheet chooseOrderCaller = new OKCaller_ChooseTransferSheet(owner, transferSheetList);
			tabelPanel = new TableHolder(new VectorBuilder_ChooseTransferSheet(transferSheetList), chooseOrderCaller,this,"请选择要接收的中转单");
			tabelPanel.setSingleChoose();
//			tabelPanel.setPreferredWidth(0, 80);
//			tabelPanel.setPreferredWidth(1, 40);
//			tabelPanel.setPreferredWidth(2, 30);
//			tabelPanel.setPreferredWidth(3, 40);
//			tabelPanel.setPreferredWidth(4, 40);
//			tabelPanel.setPreferredWidth(5, 40);
//			tabelPanel.setPreferredWidth(6, 200);
			tabelPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(tabelPanel);
		}
		
	}

	@Override
	public void refresh() {
		JPanel beforePanel = null;
		if(nilHintPanel != null){
			beforePanel = nilHintPanel;
		}else if(tabelPanel != null){
			beforePanel = tabelPanel;
		}
		remove(beforePanel);
		nilHintPanel = null;
		tabelPanel = null;
		repaint();
		initShowPanel();
	}

	@Override
	public String getButtonName() {
		return "刷新";
	}

}
