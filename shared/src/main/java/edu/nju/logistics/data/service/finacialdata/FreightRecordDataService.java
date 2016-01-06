package edu.nju.logistics.data.service.finacialdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.financial.FreightRecordPO;

public interface FreightRecordDataService extends Remote{
	/**
	 * 得到所有的运费记录
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<FreightRecordPO> getAll() throws RemoteException;
	/**
	 *将一系列运费记录传到服务器上
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(ArrayList<FreightRecordPO>   freightRecordList) throws RemoteException;
	/**
	 * 将单个运费记录传到服务器上
	 * @param po
	 * @throws RemoteException
	 */
	public void  insert(FreightRecordPO po) throws RemoteException;
}
