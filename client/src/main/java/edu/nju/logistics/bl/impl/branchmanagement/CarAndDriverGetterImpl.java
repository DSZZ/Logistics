package edu.nju.logistics.bl.impl.branchmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.branchmanagement.CarAndDriverGetter;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.branchdata.BranchDataService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;

public class CarAndDriverGetterImpl implements CarAndDriverGetter {

	private BranchDataService branchDataManager;
	
	public CarAndDriverGetterImpl() throws RemoteException {
		try {
			branchDataManager = (BranchDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/BranchData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<CarPO> getInstitutionCarList(String institutionid)
			throws RemoteException {
		return branchDataManager.getCarList(institutionid);
	}
	
	@Override
	public ArrayList<DriverPO> getInstitutionDriverList(String institutionid)
			throws RemoteException {
		return branchDataManager.getDriverList(institutionid);
	}

}
