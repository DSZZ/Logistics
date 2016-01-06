package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.PaymentBankAccountService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.BankAccountDataService;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;

public class PaymentBankAccountBL  implements PaymentBankAccountService{
	  
	 private ArrayList<BankAccountPO>  bankAccountPOList;
	 private BankAccountDataService dataService ;
	
	 public PaymentBankAccountBL(){
	        bankAccountPOList = new ArrayList<>();
	        try{
		           this.dataService = (BankAccountDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/"+"BankAccountDSImpl");
	               System.out.println("BankAccountDataService awake!");
	        } catch (Exception e) {
		           System.out.println("BankAccountDS failed");
			       e.printStackTrace();
		    }
	       //每次创建BL会自动从服务器读取全部的银行账户
	        findBankAccountPOList();
	 }
	@Override
	 public ArrayList<BankAccountPO> findBankAccountPOList() {
		// TODO Auto-generated method stub
		try {
			System.out.println("create a new bl and new listPO");
			if(bankAccountPOList.size()==0){
				System.out.println("bankPO is  empty!");
			}
			bankAccountPOList =dataService.getAll();
			
			System.out.println("The data on server is :");
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
	public BankAccountVO getBankAccountVO(String name) {
		// TODO Auto-generated method stub
		for(int i=0;i<bankAccountPOList.size();i++){
			if(bankAccountPOList.get(i).getName().equals(name)){
				return  BankAccountPoToVo(bankAccountPOList.get(i)) ;
			}
		}
		System.out.println("Can't find the bankaccountVO:  " +name);
		return null;
	}

	@Override
	public boolean  updateBankAccountPO(BankAccountPO po) {
		// TODO Auto-generated method stub
		int index=-1;
		for(int i=0;i<bankAccountPOList.size();i++){
			if(bankAccountPOList.get(i).getName().equals(po.getName())){
			                 index=i;
			                 break;
			}
		}
		
		if(index==-1){
			System.out.println("服务器上不存在银行账户："+po.getName());
			return false;
		}else{
			bankAccountPOList.get(index).setName(po.getName());
			bankAccountPOList.get(index).setBalance(po.getBalance());
			return true;
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

	@Override
	public BankAccountVO BankAccountPoToVo(BankAccountPO po) {
		// TODO Auto-generated method stub
		BankAccountVO vo = new BankAccountVO(po.getName(), po.getBalance());
		return vo;
	}

}
