package edu.nju.logistics.bl.service.identitymanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.UserPO;

/**
 * 总经理获得的用户登录信息接口
 * @author 董轶波
 *
 */
public interface LoginUserList {
	/**
	 *  总经理修改员工ID同时更新登录ID
	 * @param po
	 * @throws RemoteException 
	 */
	public void setNewID(String oldID,String newID) throws RemoteException;
	/**
	 * 得到登录信息用户
	 * @return
	 */
	public ArrayList<UserPO> getIdentityUsers()throws RemoteException;
}
