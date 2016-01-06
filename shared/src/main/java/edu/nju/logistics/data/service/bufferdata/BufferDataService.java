package edu.nju.logistics.data.service.bufferdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.SheetPO;

/**
 * 所有单据的数据接口
 * @author 董轶波
 *
 */
public interface BufferDataService extends Remote{
	/**
	 * 获得所有的单据数据
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<SheetPO> getAll() throws RemoteException;
	/**
	 * 所有的单据写入文本
	 */
	public void writeAll(ArrayList<SheetPO> po) throws RemoteException;
//	/**
//	 * 将某单据插入文本
//	 */
//	public void insert(SheetPO po) throws RemoteException;
}
