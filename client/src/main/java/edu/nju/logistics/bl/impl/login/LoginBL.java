package edu.nju.logistics.bl.impl.login;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.identitydata.IdentityDataService;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.UserVO;
/**用户登陆业务逻辑
 * 
 * @author 董轶波
 *
 */
public class LoginBL{
	/**
	 * 人员登陆数据
	 */
	private IdentityDataService identityDataService=null;
	/**
	 * 用户PO列表
	 */
	private ArrayList<UserPO> po=null;
	/**
	 * 用户的职位
	 */
	private String role=null;
	public LoginBL() throws  RemoteException, MalformedURLException, NotBoundException{
				this.identityDataService=(IdentityDataService)Naming.lookup("rmi://"+RMI.getIP()+":2014/IdentityData");
				System.out.println("rmi调用成功！");
	}
	/**
	 * 验证用户登陆
	 * @throws RemoteException 
	 */
	public boolean login(UserVO vo) throws RemoteException {
		boolean flag=false;
			this.po=this.identityDataService.getAll();
			for (int i = 0; i < this.po.size(); i++) {
				UserPO temp=po.get(i);
				if(temp.getId().equals(vo.getId())&&
				   temp.getPassword().equals(vo.getPassword())){
					flag=true;
					this.role=temp.getRole();
				}
			}
		return flag;
	}
	/**
	 * 获得用户的职位
	 */
	public String getRole() {	
		return this.role;
	}
	/**
	 * 记录成功登录的用户
	 * @param vo
	 * @throws RemoteException 
	 */
	public void record(UserVO vo) throws RemoteException {
		this.identityDataService.record(vo.getId());
	}
	/**
	 * 判断您是否重复登录
	 */
	public boolean isRepeatLogin(String id) throws RemoteException{
		
		//判断是否已登录
		ArrayList<String> userList=this.identityDataService.getCurrentLoginUsers();
		for (int i = 0; i <userList.size(); i++) {
			String string=userList.get(i);
			if(id.equals(string)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 删除用户登录信息
	 * @param id
	 */
	public void removeCurrentUser(String id)throws RemoteException {
		ArrayList<String> list=this.identityDataService.getCurrentLoginUsers();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(id)){
				list.remove(id);
				break;
			}
		}
		this.identityDataService.updateLoginUsers(list);
	}

}
