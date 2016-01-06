package edu.nju.logistics.bl.service.operationmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.vo.LogVO;

public interface InsertLogService {

	/**
	 * 记录日志
	 * 
	 * @param vo
	 * @throws RemoteException 
	 */
	public void insert(LogVO vo) throws RemoteException;
}
