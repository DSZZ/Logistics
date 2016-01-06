package edu.nju.logistics.bl.service.storehousemanagement;

import java.rmi.RemoteException;

public interface StorehouseManagementBLFinanceService {

	/**
	 * 
	 * @param centerID 6位数字
	 * @param area 有四个：航运区，铁运区，汽运区，机动区
	 * @return
	 * @throws RemoteException
	 */
	public int getStorageCount(String centerID, String area) throws RemoteException;
	
	/**
	 * 
	 * @param centerID 6位数字
	 * @param area 有四个：航运区，铁运区，汽运区，机动区
	 * @return
	 * @throws RemoteException
	 */
	public int getScaleNumber(String centerID, String area) throws RemoteException;
}
