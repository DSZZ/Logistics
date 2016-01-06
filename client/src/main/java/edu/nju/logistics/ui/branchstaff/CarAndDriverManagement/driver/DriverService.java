package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.CommonService;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;
import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;

public class DriverService implements CommonService{

	private BranchManagementService branchManagement;
	private ArrayList<DriverPO> driverList = null;
	private OKCaller_AddDriver okCaller;
	private ArrayList<RightClickDealer> rightClickDealerList;
	private DisconnectInformer disconnectInformer;
	
	public DriverService(BranchManagementService branchManagement,DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		okCaller = new OKCaller_AddDriver(branchManagement,disconnectInformer);

	}
	
	@Override
	public void updateList() {
		try {
			driverList = branchManagement.getAllDriverList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		rightClickDealerList = new ArrayList<>();
		rightClickDealerList.add(new RightClickDealer_Show(branchManagement,driverList,disconnectInformer));
		rightClickDealerList.add(new RightClickDealer_Delete(branchManagement,driverList,disconnectInformer));
	}

	@Override
	public String getName() {
		return "司机";
	}

	@Override
	public boolean listIsEmpty() {
		if(driverList == null || driverList.size() < 1)
			return true;
		else
			return false;
	}

	@Override
	public VectorGetter getVectorBuilder() {
		ArrayList<DriverPO> driverList;
		try {
			driverList = branchManagement.getAllDriverList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return new VectorBuilder_Driver(new ArrayList<>());
		}
		
		if(driverList == null)
			driverList = new ArrayList<>();
		
		return new VectorBuilder_Driver(driverList);
	}

	@Override
	public OKCallerService getOrderCaller() {
		return okCaller;
	}

	@Override
	public void setRefresher(Refresher refresher) {
		okCaller.addRefresher(refresher);
		for(RightClickDealer d : rightClickDealerList){
			d.addRefresher(refresher);
		}
	}

	@Override
	public ArrayList<RightClickDealer> getRightClickDealerList() {
		return rightClickDealerList;
	}


}
