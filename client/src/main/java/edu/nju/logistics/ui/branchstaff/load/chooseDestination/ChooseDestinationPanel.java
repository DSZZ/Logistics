package edu.nju.logistics.ui.branchstaff.load.chooseDestination;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.InstitutionGetter;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;
import edu.nju.logistics.vo.branchmanagement.BranchVO;

public class ChooseDestinationPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048636366272152104L;
	
	private InstitutionGetter institutionGetter;
	private ShowPanel_Load owner;
	private DisconnectInformer disconnectInformer;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tabelPanel;
	private Refresher refresher;

	public ChooseDestinationPanel(InstitutionGetter branchManagement
			, ShowPanel_Load showPanel_Load, DisconnectInformer disconnectInformer) {
		institutionGetter = branchManagement;
		owner = showPanel_Load;
		refresher = new ChooseCenter(showPanel_Load);
		initShowPanel();
	}

	private void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<BranchVO> branchList;
		try {
			branchList = institutionGetter.getLocalBranchList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(branchList == null || branchList.size() == 0){
			nilHintPanel = new NilHintPanel(refresher,"当前无可发往的本地营业厅");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_ChooseDestination payOrderCaller = new OKCaller_ChooseDestination(owner, branchList);
			tabelPanel = new TableHolder(new VectorBuilder_ChooseDestination(branchList), payOrderCaller,refresher,
					"请选择要发往的本地营业厅或点击右下按钮发往本地中转中心");
			tabelPanel.setSingleChoose();
			tabelPanel.changeRefreshButtonToCommonButton();
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


}
