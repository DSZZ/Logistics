package edu.nju.logistics.data.service.employeedata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.UserPO;

/**
 * 员工数据接口
 * @author 董轶波
 *
 */
public interface EmployeeDataService extends Remote{
	/**
	 * 得到所有的职员数据
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserPO> getAll() throws RemoteException;
	/**
	 * 所有的员工数据写入文本
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(ArrayList<UserPO> po) throws RemoteException;
}
