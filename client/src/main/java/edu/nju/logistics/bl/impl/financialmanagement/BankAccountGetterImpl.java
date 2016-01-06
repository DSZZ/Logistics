package edu.nju.logistics.bl.impl.financialmanagement;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.BankAccountGetter;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;
import edu.nju.logistics.po.financial.BankAccountPO;

public class BankAccountGetterImpl implements BankAccountGetter {
	private BankAccountDataService dataService ;
	public  BankAccountGetterImpl() throws RemoteException{
		
  		  try {
			this.dataService = (BankAccountDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/"+"BankAccountDSImpl");
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	      System.out.println("BankAccountDataService awake!");
  	   
	}
	
	@Override
	public ArrayList <BankAccountPO>  getAllBankAccount() throws RemoteException {
		// TODO Auto-generated method stub
			
			return dataService.getAll();
	}

}
