package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;

public class RightClickDealer_Show implements RightClickDealer{

	private Refresher refresher;
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private ArrayList<DriverPO> driverList;
	
	public RightClickDealer_Show(BranchManagementService branchManagement
			, ArrayList<DriverPO> driverList, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		this.driverList = driverList;
	}
	
	@Override
	public String getServiceName() {
		return "编辑/查看详细信息";
	}

	@Override
	public void dealRightClick(int row) {
		new AddPanel_Driver(branchManagement,null,refresher,driverList.get(row),disconnectInformer);
	}
	
	@Override
	public void addRefresher(Refresher refresher){
		this.refresher = refresher;
	}

}
