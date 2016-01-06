package edu.nju.logistics.bl.impl.branchmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.impl.transfersheetmanagement.TransferSheetManagementController;
import edu.nju.logistics.bl.service.branchmanagement.BranchManagementService;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.branchdata.BranchDataService;
import edu.nju.logistics.po.branchdata.*;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.branchmanagement.BranchVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class BranchManagementImpl implements BranchManagementService{

	private BranchDataService branchDataManager;
	private EnvironmentGetter environmentGetter;
	private String localInstitutionId;
	private TransferSheetManagementBLService transferSheetManagement;
	
	public BranchManagementImpl(UserVO operator) throws RemoteException {
		transferSheetManagement = new TransferSheetManagementController();

		try {
			branchDataManager = (BranchDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/BranchData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
		environmentGetter = new OperationManagementController();
		localInstitutionId = environmentGetter.getUserInstitutionID(operator.getId());
	}

	@Override
	public void deleteCar(String carid) throws RemoteException {
		branchDataManager.deleteCar(carid, localInstitutionId);
	}

	@Override
	public void deleteDriver(String driverid) throws RemoteException {
		branchDataManager.deleteDriver(driverid, localInstitutionId);
	}

	@Override
	public void addDriver(DriverPO driverVO) throws RemoteException {
		driverVO.setInstitutionid(localInstitutionId);
		branchDataManager.insertDriver(driverVO, localInstitutionId);
	}

	@Override
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException {
		return branchDataManager.getDriverList(localInstitutionId);

	}

	@Override
	public ArrayList<CarPO> getAllCarList() throws RemoteException {
		return branchDataManager.getCarList(localInstitutionId);
	}

	@Override
	public ArrayList<DriverPO> getFreeDriverList() throws RemoteException {
		ArrayList<DriverPO> all = getAllDriverList();
		ArrayList<DriverPO> part = new ArrayList<>();
		for(DriverPO driver : all){
			if(!driver.isBusy())
				part.add(driver);
		}
		return part;
	}

	@Override
	public ArrayList<CarPO> getFreeCarList() throws RemoteException {
		ArrayList<CarPO> all = getAllCarList();
		ArrayList<CarPO> part = new ArrayList<>();
		for(CarPO car : all){
			if(!car.isBusy())
				part.add(car);
		}
		return part;
	}

	@Override
	public void addCar(CarPO car) throws RemoteException {
		branchDataManager.insertCar(car, localInstitutionId);	
	}
	
	@Override
	public String getNextDriverID() throws RemoteException {
		ArrayList<DriverPO> driverList = getAllDriverList();
		ArrayList<Integer> idPostfixList = new ArrayList<>();
		for(DriverPO driver : driverList){
			String id = driver.getId();
			String postfix = id.substring(id.length() - 2, id.length());
			idPostfixList.add(Integer.parseInt(postfix));
		}
		
		for(int i = 0 ; i < 100 ; i++){
			if(!idPostfixList.contains(i)){
				if(i < 10){
					return localInstitutionId + "10" + i;
				}
				else{
					return localInstitutionId + "1" + i;
				}
			}
		}
		return localInstitutionId + "9999";
	}

	@Override
	public String getNextCarID() throws RemoteException {
		ArrayList<CarPO> carList = getAllCarList();
		ArrayList<Integer> idPostfixList = new ArrayList<>();
		for(CarPO car : carList){
			String id = car.getId();
			String postfix = id.substring(id.length() - 2, id.length());
			idPostfixList.add(Integer.parseInt(postfix));
		}
		
		for(int i = 0 ; i < 100 ; i++){
			if(!idPostfixList.contains(i)){
				if(i < 10){
					return localInstitutionId + "00" + i;
				}
				else{
					return localInstitutionId + "0" + i;
				}
			}
		}
		return localInstitutionId + "9999";
	}

	@Override
	public double getTransferFee(ArrayList<OrderVO> orderList) throws RemoteException {
		double fee = 0.0;
		final double minDistance = 100;
		final double price = environmentGetter.getServicePrice("汽车");
		
		for(OrderVO order : orderList){
			int weight = Integer.parseInt(order.getRealWeight());
			fee += price/1000*weight*minDistance;
		}
		
		return fee;
	}

	@Override
	public String getUpperCenterID() {
		return localInstitutionId.substring(0, localInstitutionId.length() - 2);
	}

	@Override
	public String getUpperCenterName() throws RemoteException {
		return environmentGetter.getCenterName(getUpperCenterID());
	}

	@Override
	public void load(ArrayList<OrderVO> selectedOrderList,
			String destinationid, String carid, String driverid,
			String observer, String supercargo, double fee, String staffid) throws RemoteException {
		
		TransferSheetVO transferSheet = new TransferSheetVO(localInstitutionId,
				destinationid, TimeUtil.getCurrentDate(), selectedOrderList
				, transferSheetManagement.getTransferSheetID(),
				Transportation.CAR, carid, observer, supercargo, fee);

		branchDataManager.addDriverCount(driverid, localInstitutionId);
		branchDataManager.setCarBusyStatus(carid, true, localInstitutionId);
		branchDataManager.setDriverBusyStatus(driverid, true, localInstitutionId);
		transferSheetManagement.createTransferSheet(transferSheet, staffid);
	}
	
	@Override
	public ArrayList<TransferSheetVO> getLocalTransferSheetList() throws RemoteException{
		return transferSheetManagement.show(localInstitutionId);
	}

	@Override
	public void receiveTransferSheet(String id, String staffid, ReceiptState status) throws RemoteException {
		transferSheetManagement.receiveItems(id, staffid, status);
	}
	
	@Override
	public ArrayList<UserVO> getCourierList() throws RemoteException{
		return environmentGetter.getCourierList(localInstitutionId);
	}

	@Override
	public ArrayList<BranchVO> getLocalBranchList() throws RemoteException {
		
		ArrayList<BranchVO> branchList = new ArrayList<>();
		String centerid = localInstitutionId.substring(0, localInstitutionId.length() - 2);
		ArrayList <String> intsitutionIDList = environmentGetter.getBranchesID(centerid);
		for(String id : intsitutionIDList){
			branchList.add(new BranchVO(id, environmentGetter.getBranchName(id)));
		}
		return branchList;
	}

}
