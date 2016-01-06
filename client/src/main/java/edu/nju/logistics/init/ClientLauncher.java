package edu.nju.logistics.init;

import edu.nju.logistics.ui.manager.ManagerFrame;
import edu.nju.logistics.vo.UserVO;

public class ClientLauncher {

	public static void main(String[] args){
		//总经理
		ManagerFrame managerFrame=new ManagerFrame(new UserVO("1111111", "111111", "总经理"));
	}
}
