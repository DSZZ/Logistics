package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.BankAccountUpdateBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;

public class BankAccountUpdateBL implements BankAccountUpdateBLService {
	private BankAccountDataService dataService ;
	public  BankAccountUpdateBL(){
		try{
  		  this.dataService = (BankAccountDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/"+"BankAccountDSImpl");
  	      System.out.println("BankAccountDataService awake!");
  	    } catch (Exception e) {
  		  System.out.println("BankAccountDS failed");
			e.printStackTrace();
		}
	}
	@Override
	public void updateBankAccountPO(String Name, double cost) {
		// TODO Auto-generated method stub
		try{
		dataService.Update(Name, cost);
		}catch (RemoteException e){
			e.printStackTrace();
		}
	}

}
