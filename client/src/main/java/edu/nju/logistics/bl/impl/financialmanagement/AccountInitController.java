package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.AccountInitBLService;
import edu.nju.logistics.vo.financial.AccountVO;

public class AccountInitController implements AccountInitBLService{
     private AccountInitBL bl;
     public AccountInitController(AccountInitBL bl) throws RemoteException{
    	   this.bl  = bl;
     }
	@Override
	public void saveAccount(AccountVO accountVO) throws RemoteException {
		// TODO Auto-generated method stub
		bl.saveAccount(accountVO);
	}

	@Override
	public AccountVO showOldAccount(String date ) {
		// TODO Auto-generated method stub
		return bl.showOldAccount(date);
	}

	@Override
	public AccountVO showNewAccount() {
		// TODO Auto-generated method stub
		return bl.showNewAccount();
	}

	@Override
	public ArrayList<String> showDateList(){
		return bl.showDateList();
	}

	
}
