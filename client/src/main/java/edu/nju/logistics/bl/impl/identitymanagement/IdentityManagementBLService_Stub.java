package edu.nju.logistics.bl.impl.identitymanagement;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.vo.UserVO;

public class IdentityManagementBLService_Stub implements IdentityManagementBLService{
	public ArrayList<UserVO> show() {
		ArrayList<UserVO> vo=new ArrayList<UserVO>();
		UserVO vo1=new UserVO("111111","222222","总经理");
		UserVO vo2=new UserVO("222222","333333","快递员");
		vo.add(vo1);
		vo.add(vo2);
		return vo;
	}

	public ArrayList<UserVO> showAddWindow() {
		ArrayList<UserVO> vo=new ArrayList<UserVO>();
		UserVO vo1=new UserVO("111111","222222","总经理");
		UserVO vo2=new UserVO("222222","333333","快递员");
		vo.add(vo1);
		vo.add(vo2);
		return vo;
	}

	public ArrayList<UserVO> showDeleteWindow() {
		ArrayList<UserVO> vo=new ArrayList<UserVO>();
		UserVO vo1=new UserVO("111111","222222","总经理");
		UserVO vo2=new UserVO("222222","333333","快递员");
		vo.add(vo1);
		vo.add(vo2);
		return vo;
	}

	public boolean addUser(UserVO vo) {
		if(vo.getId().equals("111111")&&vo.getPassword().equals("222222")){
			return true;
		}
		return false;
	}

	public boolean deleteUser(String id) {
		if(id.equals("111111")){
			return true;
		}
		return false;
	}

	public boolean updateUser(UserVO vo) {
		if(vo.getId().equals("111111")&&vo.getPassword().equals("222222")){
			return true;
		}
		return false;
	}

	public boolean checkUser(UserVO vo) {
		if(vo.getId().equals("111111")&&vo.getPassword().equals("222222")){
			return true;
		}
		return false;
	}

	@Override
	public UserVO checkUserByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserVO> checkUserByRole(String role) {
		// TODO Auto-generated method stub
		return null;
	}

}
