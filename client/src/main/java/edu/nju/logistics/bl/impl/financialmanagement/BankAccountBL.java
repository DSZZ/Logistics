package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import edu.nju.logistics.bl.service.finacialmanagement.BankAccountBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;


public class BankAccountBL implements BankAccountBLService{
	
	 private ArrayList<BankAccountPO>  bankAccountPOList;
	 private ArrayList<BankAccountVO>  bankAccountVOList;
     private BankAccountDataService dataService ;
     /**
      * 在构造函数中获取服务器端的BankAccountDSImpl
      */
     public BankAccountBL (){
    	 bankAccountPOList = new ArrayList<>();
    	 bankAccountVOList = new ArrayList<>();
    	  try{
    		  this.dataService = (BankAccountDataService) Naming.lookup("rmi://"+RMI.getIP() + ":2014/"+"BankAccountDSImpl");
    	      System.out.println("BankAccountDataService awake!");
    	  } catch (Exception e) {
    		  System.out.println("BankAccountDS failed");
  			e.printStackTrace();
  		}
    	  //每次创建BL会自动从服务器读取全部的银行账户
         findBankAccountPOList();
         ConvertToVOList();
     }
     
     
     
	@Override
	public ArrayList<BankAccountPO> findBankAccountPOList()  {
		// TODO Auto-generated method stub
		//bankAccountPOList = new ArrayList<>();
		try {
			bankAccountPOList =dataService.getAll();
			
			System.out.println("BABL - findBankAccountPOList--The data on server is :");
			for(int i=0;i<bankAccountPOList.size();i++){
				System.out.println(bankAccountPOList.get(i).getName()+" "+bankAccountPOList.get(i).getBalance());
			}
	    } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Get all bankaccountpo from the server!");
		return bankAccountPOList;
	}
	
	@Override
	public ArrayList<BankAccountVO> getBankAccountVOList() {
		// TODO Auto-generated method stub
		//转化出VO表
		
		return  bankAccountVOList;
	}

	@Override
	public ArrayList<BankAccountVO> getBankAccountVOListByKey(String key) {
		// TODO Auto-generated method stub
		bankAccountVOList = new ArrayList<>();
		for(int i=0;i<bankAccountPOList.size();i++){
			String temp = bankAccountPOList.get(i).getName();
			if(temp.contains(key)){
				BankAccountVO vo = new BankAccountVO(temp,bankAccountPOList.get(i).getBalance());
				 bankAccountVOList.add(vo);
			}
		}
		return bankAccountVOList;
	}

	@Override
	public BankAccountVO getBankAccountVO(String name) {
		// TODO Auto-generated method stub
		for(int i=0;i<bankAccountVOList.size();i++){
			if(bankAccountVOList.get(i).getName().equals(name)){
				return bankAccountVOList.get(i);
			}
		}
		System.out.println("Can't find the bankaccount:  " +name);
		return null;
	}

	@Override
	public boolean addBankAccount(String name, String balance) {
		// TODO Auto-generated method stub
		BankAccountPO po = new BankAccountPO (name,Double.parseDouble(balance));
		bankAccountPOList.add(po);
		System.out.println("insert a new BankAccount into the data");
		return true;
	}
	
	@Override
	public boolean modifyBankAccount(String oldName, String newName) {
		// TODO Auto-generated method stub
		int index=-1;
		for(int i=0;i<bankAccountPOList.size();i++){
			if(bankAccountPOList.get(i).getName().equals(oldName)){
			                 index=i;
			                 break;
			}
		}
		if(index==-1){
			System.out.println("服务器上不存在银行账户："+oldName);
			return false;
		}else{
			bankAccountPOList.get(index).setName(newName);
			return true;
		}
	}
	


	@Override
	public void deleteBankAccount(String name) {
		   int index =-1;
		   for(int i =0;i<bankAccountPOList.size();i++){
			   if(bankAccountPOList.get(i).getName().equals(name)){
				       index=i;
				       break;
			   }
		   }
		   if(index==-1){
			   System.out.println("服务器上不存在银行账户："+name);
		   }else{
			   System.out.println("删除银行账户："+name);
			   bankAccountPOList.remove(index);
		   }
		   
	}
	@Override
	public BankAccountVO BankAccountPoToVo(BankAccountPO po) {
		// TODO Auto-generated method stub
		BankAccountVO vo = new BankAccountVO(po.getName(), po.getBalance());
		return vo;
	}
	
	public void  ConvertToVOList() {
		// TODO Auto-generated method stub
		
		if(bankAccountPOList==null){
			System.out.println("BankAccountListPO is empty!'");
		}else{
			for(int i=0;i<bankAccountPOList.size();i++){
				BankAccountPO  po = bankAccountPOList.get(i);
				bankAccountVOList.add(BankAccountPoToVo(po));
			}
		}
	
	}



	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		 try{
	    	 this.dataService.writeAll(bankAccountPOList);
	     }catch (RemoteException e){
	    	 e.printStackTrace();
	     }
	}









}
