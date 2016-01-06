package edu.nju.logistics.bl.service.branchmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.DriverPO;

public interface DriverController {

	/**
	 * 获得所有营业厅的司机列表
	 * @return 
	 * @throws RemoteException 
	 */
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException;
	
	/**
	 * 财务人员更新车辆的月份
	 * @param driverid
	 * @param institution
	 * @param newMonth
	 */
	public void updateDriverMonth(String driverid, String institution, String newMonth) throws RemoteException;
}
