package edu.nju.logistics.bl.impl.login;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.login.LoginBLService;
import edu.nju.logistics.vo.UserVO;

public class LoginBLService_Driver {
	
	private LoginBLService loginBLService;
	
	public LoginBLService_Driver(LoginBLService loginBLService) {
		this.loginBLService=loginBLService;
	}
	public void drive(LoginBLService loginBLService){
		boolean result = false;
		try {
			result = loginBLService.login(new UserVO("111111","222222"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result){
			System.out.println("登陆成功");
		}
		else{
			System.out.println("登陆失败");
		}
	}
}
