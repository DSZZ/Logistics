package edu.nju.logistics.bl.service.storehousemanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.BufferVO;

public interface StorehouseManagementBLCenterService {

	/**
	 * 获取待发货列表
	 * @param centerID
	 * @return
	 */
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException;
	
	/**
	 * 添加到待入库列表
	 * @param vo
	 */
	public void setToImport(BufferVO vo) throws RemoteException;
	
	/**
	 * 删除id的缓存
	 * @param id
	 */
	public void deleteBuffer(String id) throws RemoteException;
}
