package edu.nju.logistics.ui.branchstaff.distribute.chooseCourier;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.distribute.ShowPanel_Distribute;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;
import edu.nju.logistics.vo.UserVO;

public class ChooseCourierPanel extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048636366272152104L;
	
	private BranchManagementService branchManagement;
	private ShowPanel_Distribute owner;
	private DisconnectInformer disconnectInformer;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tabelPanel;

	public ChooseCourierPanel(BranchManagementService branchManagement,
			ShowPanel_Distribute owner, DisconnectInformer disconnectInformer) {
		this.branchManagement = branchManagement;
		this.owner = owner;
		initShowPanel();
	}

	private void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<UserVO> courierList;
		try {
			courierList = branchManagement.getCourierList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(courierList == null || courierList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无可分配的快递员");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_ChooseCourier payOrderCaller = new OKCaller_ChooseCourier(owner, courierList);
			tabelPanel = new TableHolder(new VectorBuilder_ChooseCourier(courierList), payOrderCaller,this,"请选择要分配给的快递员");
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
