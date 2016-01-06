package edu.nju.logistics.bl.service.identitymanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.UserVO;

/**
 * 用于管理员管理登陆账户
 * @author 董轶波
 *
 */
public interface IdentityManagementBLService {
	/**
	 * 界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> show() throws RemoteException;
	/**
	 * 增加用户界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> showAddWindow() throws RemoteException;
	/**
	 * 删除用户界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> showDeleteWindow() throws RemoteException;
	/**
	 * 增加用户
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean addUser(UserVO vo) throws RemoteException;
	/**
	 * 删除用户
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public boolean deleteUser(String id) throws RemoteException;
	/**
	 * 修改用户信息
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean updateUser(UserVO vo) throws RemoteException;
	/**
	 * 根据ID查看用户
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public UserVO checkUserByID(String id) throws RemoteException;
	/**
	 * 根据职位查看用户
	 * @param role
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> checkUserByRole(String role) throws RemoteException;
}
