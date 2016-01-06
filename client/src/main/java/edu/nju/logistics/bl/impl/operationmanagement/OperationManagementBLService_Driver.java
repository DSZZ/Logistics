package edu.nju.logistics.bl.impl.operationmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.UserVO;

public class OperationManagementBLService_Driver {
	private OperationManagementBLService operationManagementBLService;
	public OperationManagementBLService_Driver(OperationManagementBLService operationManagementBLService) {
		this.operationManagementBLService=operationManagementBLService;
	}
	public void drive(OperationManagementBLService operationManagementBLService){
		try{
		for(int i=0;i<operationManagementBLService.checkSystemLog("2015.10.25").size();i++){
			operationManagementBLService.checkSystemLog("2015.10.25").get(i).getContent();
		}
		}catch(Exception  e){
			
		}
		
		
		
		try {
			for(int i=0;i<operationManagementBLService.salaryShow().size();i++){
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			operationManagementBLService.makeDistance(null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			for(int i=0;i<operationManagementBLService.distanceShow().size();i++){
				System.out.println(operationManagementBLService.distanceShow().get(i).getDistance());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			operationManagementBLService.makePrice(null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			System.out.println(operationManagementBLService.priceShow().getMap().get("火车"));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try{
			operationManagementBLService.setApproval(null);
	
		
		
			operationManagementBLService.setMassApproval(null);
		
		
			for(int i=0;i<operationManagementBLService.sheetShow(null).size();i++){
				System.out.println(operationManagementBLService.sheetShow(null).get(i).getDescription());
			}
	
		for(int i=0;i<operationManagementBLService.institutionShow().size();i++){
			System.out.println(operationManagementBLService.institutionShow().get(i).getLocation());
		}
		
		operationManagementBLService.employeeShow(null);
		
		InstitutionVO vo=new InstitutionVO("南京", "1001", "营业厅");
		UserVO vo1=new UserVO("111111","222222");
		System.out.println(operationManagementBLService.addInstitution(vo)+" "
				+operationManagementBLService.deleteInstitution("1111")+" "
				+" "
				
				+operationManagementBLService.addEmployee(vo1)+" "
				+operationManagementBLService.deleteEmployee("111111")+" "
				
				);
		
		}catch(Exception e){
			
		}
	}
}
