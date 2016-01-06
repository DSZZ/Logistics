package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;

public class RightClickDealer_Show implements RightClickDealer{

	private Refresher refresher;
	private BranchManagementService branchManagement;
	private ArrayList<CarPO> carList;
	private DisconnectInformer disconnectInformer;
	
	public RightClickDealer_Show(BranchManagementService branchManagement
			, ArrayList<CarPO> carList, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		this.carList = carList;
	}
	
	@Override
	public String getServiceName() {
		return "编辑";
	}

	@Override
	public void dealRightClick(int row) {
		new AddPanel_Car(branchManagement,null,refresher,carList.get(row),disconnectInformer);
	}
	
	@Override
	public void addRefresher(Refresher refresher){
		this.refresher = refresher;
	}

}
