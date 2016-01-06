package edu.nju.logistics.bl.impl.identitymanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.identitydata.IdentityDataService;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.UserVO;

/**
 * 管理员管理账户业务逻辑IdentityManagementBL
 * 
 * @author 董轶波
 *
 */
public class IdentityManagementBL {
	/**
	 * 人员登陆数据
	 */
	private IdentityDataService identityDataService = null;
	/**
	 * 环境变量接口
	 */
	private EnvironmentGetter environmentGetter = null;

	private ArrayList<UserPO> po = null;

	public IdentityManagementBL() throws RemoteException {
		try {
			this.identityDataService = (IdentityDataService) Naming
					.lookup("rmi://" + RMI.getIP() + ":2014/IdentityData");
			this.environmentGetter = new OperationManagementController();
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化显示
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> show() throws RemoteException {
		ArrayList<UserVO> vo = new ArrayList<UserVO>();
		this.po = this.identityDataService.getAll();
		for (int i = 0; i < this.po.size(); i++) {
			String ID = this.po.get(i).getId();
			String password = this.po.get(i).getPassword();
			String role = this.po.get(i).getRole();
			vo.add(new UserVO(ID, password, role));
		}
		return vo;
	}

	/**
	 * 显示增加变化
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> showAddWindow() throws RemoteException {
		ArrayList<UserVO> vo = new ArrayList<UserVO>();
		ArrayList<UserPO> temp = null;
		this.po = this.environmentGetter.getAllUser();
		temp = this.identityDataService.getAll();
		// 算法遍历找出所要增加的员工
		for (int i = 0; i < this.po.size(); i++) {
			int j;
			for (j = 0; j < temp.size(); j++) {
				if (this.po.get(i).getId().equals(temp.get(j).getId())) {
					break;
				}
			}
			if (j == temp.size()) {
				vo.add(new UserVO(this.po.get(i).getId(), "", this.po.get(i).getRole()));
			}
		}
		return vo;
	}

	/**
	 * 显示删除变化
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> showDeleteWindow() throws RemoteException {
		ArrayList<UserVO> vo = new ArrayList<UserVO>();
		ArrayList<UserPO> temp = null;
		this.po = this.environmentGetter.getAllUser();
		temp = this.identityDataService.getAll();
		// 算法遍历找出所要删除的员工
		for (int i = 0; i < temp.size(); i++) {
			int j;
			for (j = 0; j < this.po.size(); j++) {
				if (this.po.get(j).getId().equals(temp.get(i).getId())) {
					break;
				}
			}
			if (j == this.po.size()) {
				vo.add(new UserVO(temp.get(i).getId(), temp.get(i).getPassword(), temp.get(i).getRole()));
			}
		}
		return vo;
	}

	/**
	 * 增加账号
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public boolean addUser(UserVO vo) throws RemoteException {
		boolean flag = true;
		this.po = this.identityDataService.getAll();
		UserPO po = new UserPO(vo.getId(), vo.getPassword(), vo.getRole());
		this.po.add(po);
		this.identityDataService.writeAll(this.po);
		return flag;
	}

	/**
	 * 删除账号
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean deleteUser(String id) throws RemoteException {
		boolean flag = true;
		this.po = this.identityDataService.getAll();

		for (int i = 0; i < this.po.size(); i++) {
			if (id.equals(this.po.get(i).getId())) {
				this.po.remove(i);
				i--;
			}
		}
		this.identityDataService.writeAll(this.po);
		return flag;
	}

	/**
	 * 修改账号信息
	 * 
	 * @param vo
	 * @return
	 * @throws RemoteException
	 */
	public boolean updateUser(UserVO vo) throws RemoteException {
		boolean flag = true;
		this.po = this.identityDataService.getAll();

		for (int i = 0; i < this.po.size(); i++) {
			if (vo.getId().equals(this.po.get(i).getId())) {
				this.po.get(i).setPassword(vo.getPassword());
			}
		}
		this.identityDataService.writeAll(this.po);
		return flag;
	}

	/**
	 * 根据ID查询账号
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public UserVO checkUserByID(String id) throws RemoteException {
		this.po = this.identityDataService.getAll();
		for (int i = 0; i < this.po.size(); i++) {
			if (id.equals(this.po.get(i).getId())) {
				UserVO vo = new UserVO(id, this.po.get(i).getPassword(), this.po.get(i).getRole());
				return vo;
			}
		}
		return null;
	}

	/**
	 * 根据职位查询账号
	 * 
	 * @param role
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserVO> checkUserByRole(String role) throws RemoteException {
		ArrayList<UserVO> vo = null;
		this.po = this.identityDataService.getAll();
		vo = new ArrayList<UserVO>();
		for (int i = 0; i < this.po.size(); i++) {
			if (role.equals(this.po.get(i).getRole())) {
				vo.add(new UserVO(this.po.get(i).getId(), this.po.get(i).getPassword(), role));
			}
		}
		return vo;
	}

	/**
	 * 总经理修改员工ID同时更新登录ID
	 * 
	 * @param po
	 * @throws RemoteException
	 */
	public void setNewID(String oldID, String newID) throws RemoteException {
		this.po = this.identityDataService.getAll();
		for (int i = 0; i < this.po.size(); i++) {
			UserPO temp = this.po.get(i);
			if (temp.getId().equals(oldID)) {
				temp.setId(newID);
			}
		}
		this.identityDataService.writeAll(this.po);

	}
	/**
	 * 得到登录用户信息
	 * @return
	 */
	public ArrayList<UserPO> getIdentityUsers()throws RemoteException {
		return this.identityDataService.getAll();
	}
}
