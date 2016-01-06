package edu.nju.logistics.bl.impl.login;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.login.LoginBLService;
import edu.nju.logistics.vo.UserVO;
/**
 * 登陆验证的控制器
 * @author 董轶波
 *
 */
public class LoginController implements LoginBLService{
	
	private LoginBL loginBL=null;
	
	public LoginController() throws RemoteException, MalformedURLException, NotBoundException {
		this.loginBL=new LoginBL();
	}
	/**
	 * 验证用户登陆
	 * @throws RemoteException 
	 */
	@Override
	public boolean login(UserVO vo) throws RemoteException {
		return this.loginBL.login(vo);
	}
	/**
	 * 获得用户职位
	 */
	@Override
	public String getRole() {
		return this.loginBL.getRole();
	}
	/**
	 * 记录成功登录的用户
	 */
	@Override
	public void record(UserVO vo) throws RemoteException{
		this.loginBL.record(vo);
	}
	/**
	 * 判断您是否重复登录
	 */
	@Override
	public boolean isRepeatLogin(String ID) throws RemoteException {
		return this.loginBL.isRepeatLogin(ID);
	}
	/**
	 * 删除登录信息
	 */
	@Override
	public void removeCurrentUser(String id) throws RemoteException {
		this.loginBL.removeCurrentUser(id);
		
	}

}
