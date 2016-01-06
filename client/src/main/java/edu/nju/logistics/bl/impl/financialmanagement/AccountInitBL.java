package edu.nju.logistics.bl.impl.financialmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.AccountInitBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.AccountInitDataService;
import edu.nju.logistics.po.financial.AccountPO;
import edu.nju.logistics.vo.financial.AccountVO;

public class AccountInitBL implements AccountInitBLService{
      private AccountInitDataService dataService; 
      private AccountList accountList;
      private ArrayList<AccountPO> accountPOList;
      private ArrayList<AccountVO>   accountVOList;
      private ArrayList<String >  dateList;
      public AccountInitBL() throws  RemoteException{
 		 try {
			this.dataService = (AccountInitDataService) Naming.lookup("rmi://"+RMI.getIP() + ":2014/"+"AccountInitDSImpl");
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		 
 		 this.accountList = new AccountList();
 		 this.accountPOList = new ArrayList<>();
 		 this.accountVOList = new ArrayList<>();
 		 this.dateList = new ArrayList<>();
 		 
 		 findAccountPOList();
 	     ConvertToVOList();
 		 prepareDateList();
 		 
 	 }
      
      public void findAccountPOList() throws RemoteException{
    	     this.accountPOList = this.dataService.getAll();
    	    	 System.out.println("AccountintBL:" +"get "+ this.accountPOList.size()+" accountPO");
      }
      
      public void ConvertToVOList(){
    	    for(int i=0;i<this.accountPOList.size();i++){
    	    	this.accountVOList.add(this.AccountPOToVO(this.accountPOList.get(i)));
    	    
    	    }
      }
      
      public void prepareDateList(){
  	      for(int i=0;i<this.accountPOList.size();i++){
  	    	  this.dateList.add(this.accountPOList.get(i).getDate());
  	      }
    }
       
	@Override
	public void saveAccount(AccountVO accountVO) throws RemoteException {
		// TODO Auto-generated method stub
		  
		   this.accountPOList.add(AccountVOToPO(accountVO));
		   this.accountVOList.add(accountVO);
		   this.dateList.add(accountVO.getDate());
		   
		   this.dataService.insert(AccountVOToPO(accountVO));
	}

	@Override
	public AccountVO showOldAccount(String date) {
		// TODO Auto-generated method stub
		for(int i=0;i<this.accountVOList.size();i++){
			if(this.accountVOList.get(i).getDate().equals(date)){
				return this.accountVOList.get(i);
			}
		}
		System.out.println("没有这个时间创建的系统账目");
		return null;
	}

	@Override
	public AccountVO showNewAccount() {
		// TODO Auto-generated method stub
		return this.accountList.createNewAccount() ;
	}

	@Override
	public ArrayList<String> showDateList(){
		return  this.dateList;
	}
	
	public AccountPO AccountVOToPO(AccountVO  vo ){
		AccountPO  po  = new AccountPO(vo.getDate(), vo.getInstiSMList(), 
				vo.getUserSMList(), vo.getDriverSMList(), 
				vo.getCarSMList(), vo.getBankaccountVOList(), vo.getStorageSMList());
		return po;
	}
	
	public AccountVO AccountPOToVO(AccountPO  po ){
		AccountVO  vo  = new AccountVO(po.getDate(), po.getInstiSMList(), 
				po.getUserSMList(), po.getDriverSMList(), 
				po.getCarSMList(), po.getBankaccountVOList(), po.getStorageSMList());
		return vo;
	}

}
