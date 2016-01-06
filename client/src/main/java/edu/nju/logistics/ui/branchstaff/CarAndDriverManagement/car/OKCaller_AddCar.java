package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;

public class OKCaller_AddCar implements OKCallerService{

	private BranchManagementService branchManagement;
	private Refresher refresher;
	private DisconnectInformer disconnectInformer;
	
	public OKCaller_AddCar(BranchManagementService branchManagement, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
	}
	@Override
	public void select(int[] selectedRows) {
		new AddPanel_Car(branchManagement,null,refresher, disconnectInformer);
	}

	@Override
	public String getButtonName() {
		return "增加";
	}
	
	public void addRefresher(Refresher refresher){
		this.refresher = refresher;
	}
	

}
