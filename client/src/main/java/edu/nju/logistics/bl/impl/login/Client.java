package edu.nju.logistics.bl.impl.login;

import edu.nju.logistics.bl.service.login.LoginBLService;
 
public class Client {

	public static void main(String[] args) {
		LoginBLService loginController=new LoginBLService_Stub();
		LoginBLService_Driver driver=new LoginBLService_Driver(loginController);
		driver.drive(loginController);
	}

}
