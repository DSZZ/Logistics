package edu.nju.logistics.bl.impl.operationmanagement;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;

public class Client {
	public static void main(String[] args) {
		OperationManagementBLService operationManagementController=new OperationManagementBLService_Stub();
		OperationManagementBLService_Driver driver=new OperationManagementBLService_Driver(operationManagementController);
		driver.drive(operationManagementController);
		
	}
}
