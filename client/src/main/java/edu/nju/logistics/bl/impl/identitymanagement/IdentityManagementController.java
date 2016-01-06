package edu.nju.logistics.bl.impl.identitymanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.bl.service.identitymanagement.LoginUserList;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.UserVO;

/**
 * 管理员账户管理控制器
 * @author 董轶波
 *
 */
public class IdentityManagementController implements IdentityManagementBLService,LoginUserList{
	
	private IdentityManagementBL identityManagementBL=null;
	
	public IdentityManagementController() throws RemoteException {
		this.identityManagementBL=new IdentityManagementBL();
	}

	@Override
	public ArrayList<UserVO> show() throws RemoteException {
		return this.identityManagementBL.show();
	}

	@Override
	public ArrayList<UserVO> showAddWindow() throws RemoteException {
		return this.identityManagementBL.showAddWindow();
	}

	@Override
	public ArrayList<UserVO> showDeleteWindow() throws RemoteException {
		return this.identityManagementBL.showDeleteWindow();
	}

	@Override
	public boolean addUser(UserVO vo) throws RemoteException {
		return this.identityManagementBL.addUser(vo);
	}

	@Override
	public boolean deleteUser(String id) throws RemoteException {
		return this.identityManagementBL.deleteUser(id);
	}

	@Override
	public boolean updateUser(UserVO vo) throws RemoteException {
		return this.identityManagementBL.updateUser(vo);
	}


	@Override
	public UserVO checkUserByID(String id) throws RemoteException {
		return this.identityManagementBL.checkUserByID(id);
	}

	@Override
	public ArrayList<UserVO> checkUserByRole(String role) throws RemoteException {
		return this.identityManagementBL.checkUserByRole(role);
	}

	@Override
	public void setNewID(String oldID,String newID) throws RemoteException {
		this.identityManagementBL.setNewID(oldID,newID);
	}

	@Override
	public ArrayList<UserPO> getIdentityUsers() throws RemoteException{
		return this.identityManagementBL.getIdentityUsers();
	}

}
