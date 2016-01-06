package edu.nju.logistics.bl.impl.branchmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.DriverController;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.branchdata.BranchDataService;
import edu.nju.logistics.po.branchdata.DriverPO;

public class DriverControllerImpl implements DriverController{

	private BranchDataService branchDataManager;
	
	public DriverControllerImpl() throws RemoteException {
		try {
			branchDataManager = (BranchDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/BranchData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException {
		ArrayList<DriverPO> driverList = branchDataManager.getAllDriverList();
		driverList = branchDataManager.getAllDriverList();
		return driverList;
	}

	@Override
	public void updateDriverMonth(String driverid, String institution, String newMonth) throws RemoteException {
		branchDataManager.updateDriverMonth(driverid, institution, newMonth);
	}

}
