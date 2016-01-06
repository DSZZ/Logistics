package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;

public class OKCaller_AddDriver implements OKCallerService{

	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private Refresher refresher;
	
	public OKCaller_AddDriver(BranchManagementService branchManagement, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
	}
	@Override
	public void select(int[] selectedRows) {
		new AddPanel_Driver(branchManagement,null,refresher,disconnectInformer);
	}

	@Override
	public String getButtonName() {
		return "增加";
	}
	
	public void addRefresher(Refresher refresher){
		this.refresher = refresher;
	}
	

}
