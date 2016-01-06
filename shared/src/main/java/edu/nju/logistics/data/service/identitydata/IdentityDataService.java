package edu.nju.logistics.data.service.identitydata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.UserPO;

/**
 * 职员登录数据接口
 * @author 董轶波
 *
 */
public interface IdentityDataService extends Remote{
	/**
	 * 得到所有用户的数据
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserPO> getAll() throws RemoteException;
	/**
	 * 所有用户数据些文本
	 * @param po
	 * @throws RemoteException
	 */
	public void writeAll(ArrayList<UserPO> po) throws RemoteException;
	/**
	 * 记录登录成功的用户
	 * @param ID
	 * @throws RemoteException
	 */
	public void record(String ID)throws RemoteException;
	/**
	 * 得到当前所有已在线用户
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<String> getCurrentLoginUsers()throws RemoteException;
	/**
	 * 更新登录名单
	 * @throws RemoteException
	 */
	public void updateLoginUsers(ArrayList<String> list)throws RemoteException;
}
