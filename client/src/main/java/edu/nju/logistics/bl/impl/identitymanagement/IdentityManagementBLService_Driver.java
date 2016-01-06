package edu.nju.logistics.bl.impl.identitymanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.identitymanagement.IdentityManagementBLService;
import edu.nju.logistics.vo.UserVO;

public class IdentityManagementBLService_Driver {
	private IdentityManagementBLService identityManagementBLService;

	public IdentityManagementBLService_Driver(IdentityManagementBLService identityManagementBLService) {
		this.identityManagementBLService = identityManagementBLService;
	}

	public void drive(IdentityManagementBLService identityManagementBLService) {

		try {
			for (int i = 0; i < identityManagementBLService.show().size(); i++) {
				System.out.println(identityManagementBLService.show().get(i).getRole());
			}
			for (int i = 0; i < identityManagementBLService.showAddWindow().size(); i++) {
				System.out.println(identityManagementBLService.showAddWindow().get(i).getRole());
			}
			for (int i = 0; i < identityManagementBLService.showDeleteWindow().size(); i++) {
				System.out.println(identityManagementBLService.showDeleteWindow().get(i).getRole());
			}
			UserVO vo = new UserVO("111111", "222222", "总经理");
			System.out.println(identityManagementBLService.addUser(vo) + " "
					+ identityManagementBLService.deleteUser(vo.getId()) + " "

					+ identityManagementBLService.updateUser(vo));

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
