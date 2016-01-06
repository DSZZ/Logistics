package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;

public class RightClickDealer_Delete implements RightClickDealer{

	private Refresher refresher;
	private BranchManagementService branchManagement;
	private DisconnectInformer disconnectInformer;
	private ArrayList<CarPO> carList;
	
	public RightClickDealer_Delete(BranchManagementService branchManagement
			, ArrayList<CarPO> carList, DisconnectInformer disconnectInformer) {
		this.branchManagement = branchManagement;
		this.disconnectInformer = disconnectInformer;
		this.carList = carList;
	}
	@Override
	public String getServiceName() {
		return "删除";
	}

	@Override
	public void dealRightClick(int row) {
		try {
			branchManagement.deleteCar(carList.get(row).getId());
			GlobalHintInserter.insertGlobalHint("车辆删除成功！");
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		refresher.refresh();
	}
	
	@Override
	public void addRefresher(Refresher refresher){
		this.refresher = refresher;
	}

}
