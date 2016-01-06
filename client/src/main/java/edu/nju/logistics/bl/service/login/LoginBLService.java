package edu.nju.logistics.bl.service.login;

import java.rmi.RemoteException;

import edu.nju.logistics.vo.UserVO;

/**
 * 用户登录接口
 * 
 * @author 董轶波
 *
 */
public interface LoginBLService {
	/**
	 * 验证用户的登录
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public boolean login(UserVO vo) throws RemoteException;

	/**
	 * 获得用户的职位
	 * 
	 * @param vo
	 * @return
	 */
	public String getRole();

	/**
	 * 记录成功登录的用户
	 * 
	 * @param vo
	 */
	public void record(UserVO vo)throws RemoteException;
	/**
	 * 判断是否重复登录
	 * @param ID
	 * @return
	 * @throws RemoteException
	 */
	public boolean isRepeatLogin(String ID)throws RemoteException;
	/**
	 * 删除登录信息
	 * @param id
	 */
	public void removeCurrentUser(String id)throws RemoteException;
}
