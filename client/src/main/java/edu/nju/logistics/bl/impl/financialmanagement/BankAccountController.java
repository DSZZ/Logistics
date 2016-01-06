package edu.nju.logistics.bl.impl.financialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.BankAccountBLService;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;


public class BankAccountController  implements BankAccountBLService{
       BankAccountBLService  bl ; 
       public BankAccountController(BankAccountBLService bl){
    	      this.bl =bl;
       }
       
	@Override
	public ArrayList<BankAccountPO> findBankAccountPOList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  boolean  addBankAccount(String name, String balance) {
		// TODO Auto-generated method stub
		return bl.addBankAccount(name,balance);
	}

	@Override
	public ArrayList<BankAccountVO> getBankAccountVOList() {
		// TODO Auto-generated method stub
		return bl.getBankAccountVOList();
	}

	@Override
	public ArrayList<BankAccountVO> getBankAccountVOListByKey(String key) {
		// TODO Auto-generated method stub
		return bl.getBankAccountVOListByKey(key);
	}

	@Override
	public BankAccountVO getBankAccountVO(String name) {
		// TODO Auto-generated method stub
		return bl.getBankAccountVO(name);
	}

	@Override
	public boolean modifyBankAccount(String oldName, String newName) {
		// TODO Auto-generated method stub
		return bl.modifyBankAccount(oldName, newName);
	}

	@Override
	public void deleteBankAccount(String name) {
		// TODO Auto-generated method stub
		bl.deleteBankAccount(name);
	}
	
	@Override
	public BankAccountVO BankAccountPoToVo(BankAccountPO po) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		 this.bl.updateData();
	}


	 
	
    
}
