package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.CommonService;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;
import edu.nju.logistics.ui.util.tableBuilder.RightClickDealer;
import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;

public class CarService implements CommonService{

	private BranchManagementService branchManagement;
	private ArrayList<CarPO> carList = null;
	private OKCaller_AddCar okCaller;
	private ArrayList<RightClickDealer> rightClickDealerList;
	private DisconnectInformer disconnectInformer;
	
	public CarService(BranchManagementService branchManagement, DisconnectInformer disconnectInformer) {
		this.disconnectInformer = disconnectInformer;
		this.branchManagement = branchManagement;
		okCaller = new OKCaller_AddCar(branchManagement,disconnectInformer);

	}
	
	@Override
	public void updateList() {
		try {
			carList = branchManagement.getAllCarList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
		rightClickDealerList = new ArrayList<>();
		rightClickDealerList.add(new RightClickDealer_Show(branchManagement,carList,disconnectInformer));
		rightClickDealerList.add(new RightClickDealer_Delete(branchManagement,carList,disconnectInformer));
	}

	@Override
	public String getName() {
		return "车辆";
	}

	@Override
	public boolean listIsEmpty() {
		if(carList == null || carList.size() < 1)
			return true;
		else
			return false;
	}

	@Override
	public VectorGetter getVectorBuilder() {
		ArrayList<CarPO> carList;
		try {
			carList = branchManagement.getAllCarList();
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return new VectorBuilder_Car(new ArrayList<>());
		}
		
		if(carList == null)
			carList = new ArrayList<>();
		
		return new VectorBuilder_Car(carList);
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
