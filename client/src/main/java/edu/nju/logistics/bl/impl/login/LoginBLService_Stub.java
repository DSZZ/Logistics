package edu.nju.logistics.bl.impl.login;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.login.LoginBLService;
import edu.nju.logistics.vo.UserVO;

public class LoginBLService_Stub implements LoginBLService{

	public LoginBLService_Stub() {
	}

	public boolean login(UserVO vo) {
		if(vo.getId().equals("111111")&&vo.getPassword().equals("222222")){
			return true;
		}
		return false;
	}

	@Override
	public String getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void record(UserVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRepeatLogin(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeCurrentUser(String id) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
