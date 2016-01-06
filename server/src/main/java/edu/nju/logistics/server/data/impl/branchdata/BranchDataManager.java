package edu.nju.logistics.server.data.impl.branchdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.branchdata.BranchDataService;
import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;

public class BranchDataManager extends UnicastRemoteObject implements BranchDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8217600869593821714L;
	private LocalFileManager fileManager;
	
	public BranchDataManager(InstitutionDataService institutionDataGetter) throws RemoteException{
		fileManager = new LocalFileManager();
		fileManager.updateLocalInstitution(institutionDataGetter);
		
	}
	@Override
	public void insertCar(CarPO carPO, String instituionid)throws RemoteException {
		fileManager.addCar(carPO, instituionid);
	}

	@Override
	public void setCarBusyStatus(String carid, boolean busyOrNot,
			String instituionid) throws RemoteException {
		fileManager.setCarBusyStatus(carid, busyOrNot, instituionid);
		
	}

	@Override
	public void deleteCar(String carid, String instituionid)throws RemoteException {
		fileManager.deleteCar(carid, instituionid);
	}

	@Override
	public ArrayList<CarPO> getCarList(String institutionid)throws RemoteException {
		return fileManager.getCarList(institutionid);
	}

	@Override
	public void insertDriver(DriverPO driverPO, String instituionid)throws RemoteException {
		fileManager.addDriver(driverPO, instituionid);
		
	}

	@Override
	public void setDriverBusyStatus(String driverid, boolean busyOrNot,
			String institutionid) throws RemoteException {
		fileManager.setDriverBusyStatus(driverid, busyOrNot, institutionid);
		
	}

	@Override
	public void deleteDriver(String driverid, String institutionid)throws RemoteException {
		fileManager.deleteDriver(driverid, institutionid);
		
	}

	@Override
	public ArrayList<DriverPO> getDriverList(String institutionid)throws RemoteException {
		return fileManager.getDriverList(institutionid);
	}
	@Override
	public void addDriverCount(String driverid, String instituionid) throws RemoteException{
		DriverPO driver = DriverReplacer.getDriver(driverid, instituionid);
		driver.addCount();
		DriverReplacer.replace(driverid, instituionid, driver);
	}
	@Override
	public void updateDriverMonth(String driverid, String institution,String newMonth) throws RemoteException {
		DriverPO driver = DriverReplacer.getDriver(driverid, institution);
		driver.setMonth(newMonth);
		DriverReplacer.replace(driverid, institution, driver);
		
	}
	@Override
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException {
		return AllDriverListGetter.getAllDriverList();
	}
	
}
