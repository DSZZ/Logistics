package edu.nju.logistics.bl.service.storehousemanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.ExportItemSheetPO;
import edu.nju.logistics.po.ImportItemSheetPO;

/**
 * 提供给总经理执行操作
 * @author HanzZou
 *
 */
public interface StorehouseManagementExecutionService {

	/**
	 * 总经理审批入库单通过执行
	 * @param po
	 */
	public void executeImportItem(ImportItemSheetPO po) throws RemoteException;
	
	/**
	 * 总经理审批出库单通过执行
	 * @param po
	 */
	public void executeExportItem(ExportItemSheetPO po) throws RemoteException;
	
}
