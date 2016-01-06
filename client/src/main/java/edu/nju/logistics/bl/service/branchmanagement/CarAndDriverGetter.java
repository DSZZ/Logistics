package edu.nju.logistics.bl.service.branchmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;

public interface CarAndDriverGetter {

	/**
	 * @param institutionid 营业厅编号
	 * @return 车辆列表
	 * @throws RemoteException
	 */
	public ArrayList<CarPO> getInstitutionCarList(String institutionid) throws RemoteException;
	
	/**
	 * @param institutionid 营业厅编号
	 * @return 司机列表
	 * @throws RemoteException
	 */
	public ArrayList<DriverPO> getInstitutionDriverList(String institutionid) throws RemoteException;
}
