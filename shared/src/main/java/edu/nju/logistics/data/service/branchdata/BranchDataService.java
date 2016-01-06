package edu.nju.logistics.data.service.branchdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.*;

public interface BranchDataService extends Remote {
	
	/**
	 * 在数据库中增加一条车辆信息
	 * @param carPO
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void insertCar(CarPO carPO,String instituionid) throws RemoteException;
		
	/**
	 * 将车辆号为id的车辆的状态改为busyOrNot，如果找不到这个车辆，不进行任何操作
	 * @param carid
	 * @param busyOrNot
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void setCarBusyStatus(String carid ,boolean busyOrNot,String instituionid) throws RemoteException;
	
	/**
	 * 将编号为id的车辆删除，如果找不到这个车辆，不进行任何操作
	 * @param carid
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void deleteCar(String carid,String instituionid) throws RemoteException;
	
	/**
	 * 将营业厅的车辆列表返回。
	 * @return
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public ArrayList<CarPO> getCarList(String instituionid) throws RemoteException;
	
	/**.
	 * 在数据库中增加一条司机信息
	 * @param driverPO
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void insertDriver(DriverPO driverPO,String instituionid) throws RemoteException;
	
	/**
	 * 将车辆号为id的司机的状态改为busyOrNot，如果找不到这个司机，不进行任何操作
	 * @param driverid
	 * @param busyOrNot
	 * @param instituionid
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void setDriverBusyStatus(String driverid ,boolean busyOrNot,String instituionid) throws RemoteException;
	
	/**
	 * 将编号为id的司机删除，如果找不到这个司机，不进行任何操作
	 * @param driverid
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public void deleteDriver(String driverid,String instituionid) throws RemoteException;
	
	/**
	 * 将营业厅的司机列表返回
	 * @return
	 * @throws RemoteException
	 * @throws DislocatedException
	 */
	public ArrayList<DriverPO> getDriverList(String instituionid) throws RemoteException;
	
	/**
	 * 每开一次车，加一
	 * @param driverid
	 * @param instituionid
	 */
	public void addDriverCount(String driverid,String instituionid) throws RemoteException;
	
	/**
	 * 更新司机的月份（财务人员操作）
	 * @param driverid
	 * @param institution
	 * @param newMonth
	 * @throws RemoteException
	 */
	public void updateDriverMonth(String driverid, String institution,
			String newMonth) throws RemoteException;

	/**
	 * 获得 【所有】司机的列表
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException;
}
