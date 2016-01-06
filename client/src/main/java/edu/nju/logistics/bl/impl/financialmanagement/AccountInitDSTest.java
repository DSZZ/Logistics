package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.vo.financial.AccountVO;

public class AccountInitDSTest {
         public static void main(String args [] ) throws RemoteException{
        	     AccountInitBL bl = new AccountInitBL();
        	    AccountVO vo = bl.showNewAccount();
        	     AccountVO vo1 = bl.showNewAccount();
        	    vo.setDate("2015/10/30");
        	     vo1.setDate("2015/11/01");
        	     bl.saveAccount(vo);
        	     bl.saveAccount(vo1);
         }
}
