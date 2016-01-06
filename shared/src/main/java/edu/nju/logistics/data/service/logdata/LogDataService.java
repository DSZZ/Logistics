package edu.nju.logistics.data.service.logdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.LogPO;

/**
 * 日志数据接口
 * @author 董轶波
 *
 */
public interface LogDataService extends Remote{
	/**
	 * 根据日期返回指定的日志
	 * @param data
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<LogPO> getLog(String date) throws RemoteException;
	/**
	 * 某项日志插入到文本
	 * @param po
	 * @throws RemoteException
	 */
	public void insert(LogPO po) throws RemoteException;
}
