package edu.nju.logistics.ui.branchstaff.load.chooseCar;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.branchstaff.BranchFrame;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car.VectorBuilder_Car;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.NilHintPanel;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.TableHolder;

public class ChooseCarPanel extends JPanel implements Refresher{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4048636366272152104L;
	
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private ShowPanel_Load owner;
	
	private NilHintPanel nilHintPanel;
	private TableHolder tablePanel;

	public ChooseCarPanel(BranchManagementService branchManagement
			, ShowPanel_Load showPanel_Load, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		owner = showPanel_Load;
		initShowPanel();
	}

	private void initShowPanel() {
		setBorder(BorderFactory.createEtchedBorder());
		setOpaque(false);
		setLayout(null);
		
		ArrayList<CarPO> carList;
		try {
			carList = branchManagement.getFreeCarList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		if(carList == null || carList.size() == 0){
			nilHintPanel = new NilHintPanel(this,"当前无空闲的车辆，请刷新或前往车辆管理释放空闲车辆");
			nilHintPanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(nilHintPanel);
		}else{
			OKCaller_ChooseCar chooseCarCaller = new OKCaller_ChooseCar(owner, carList);
			tablePanel = new TableHolder(new VectorBuilder_Car(carList), chooseCarCaller,this,"请选择要装车的车辆");
			tablePanel.setSingleChoose();
//			tabelPanel.setPreferredWidth(0, 80);
//			tabelPanel.setPreferredWidth(1, 40);
//			tabelPanel.setPreferredWidth(2, 30);
//			tabelPanel.setPreferredWidth(3, 40);
//			tabelPanel.setPreferredWidth(4, 40);
//			tabelPanel.setPreferredWidth(5, 40);
//			tabelPanel.setPreferredWidth(6, 200);
			tablePanel.setBounds(0, 0, BranchFrame.SHOWPANEL_WIDTH, BranchFrame.SHOWPANEL_HEIGHT);
			this.add(tablePanel);
		}
		
	}

	@Override
	public void refresh() {
		JPanel beforePanel = null;
		if(nilHintPanel != null){
			beforePanel = nilHintPanel;
		}else if(tablePanel != null){
			beforePanel = tablePanel;
		}
		remove(beforePanel);
		nilHintPanel = null;
		tablePanel = null;
		repaint();
		initShowPanel();
	}

	@Override
	public String getButtonName() {
		return "刷新";
	}

}
