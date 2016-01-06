package edu.nju.logistics.bl.impl.identitymanagement;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;

public class Client {
	public static void main(String[] args) {
		IdentityManagementBLService IdentityManagementController=new IdentityManagementBLService_Stub();
		IdentityManagementBLService_Driver driver=new IdentityManagementBLService_Driver(IdentityManagementController);
		driver.drive(IdentityManagementController);
		
		
	}
}
