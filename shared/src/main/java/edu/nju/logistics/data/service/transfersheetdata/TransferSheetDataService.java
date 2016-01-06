package edu.nju.logistics.data.service.transfersheetdata;

import java.rmi.*;
import java.util.ArrayList;

import edu.nju.logistics.po.TransferSheetPO;

public interface TransferSheetDataService extends Remote {

	/**
	 * 查找指定机构的待接收中转单
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<TransferSheetPO> find(String centerID) throws RemoteException;
	
	/**
	 * 删除中转单
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public boolean delete(String ID) throws RemoteException;
	
	/**
	 * 插入中转单
	 * @param po
	 * @return
	 * @throws RemoteException
	 */
	public boolean insert(TransferSheetPO po) throws RemoteException;
	
	/**
	 * 获取中转单号
	 * @return
	 * @throws RemoteException
	 */
	public String getTransferSheetID() throws RemoteException;

	/**
	 * 根据id获得中转单对象
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public TransferSheetPO getTransferSheetByID(String id) throws RemoteException;
	
}
