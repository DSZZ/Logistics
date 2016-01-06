package edu.nju.logistics.bl.service.transfersheetmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.ReceiveItemSheetPO;
import edu.nju.logistics.po.SendItemSheetPO;

/**
 * 提供给总经理执行操作
 * @author HanzZou
 *
 */
public interface TransferSheetManagementExecutionService {

	/**
	 * 提供给总经理发货
	 * @param po
	 */
	public void executeSendItem(SendItemSheetPO po) throws RemoteException;
	
	/**
	 * 提供给总经理收货
	 * @param po
	 */
	public void executeReceiveItem(ReceiveItemSheetPO po) throws RemoteException;
}
