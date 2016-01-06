package edu.nju.logistics.bl.impl.financialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.BankAccountBLService;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.BankAccountVO;

public class Mock_BankAccountBL  implements BankAccountBLService{

	@Override
	public ArrayList<BankAccountPO> findBankAccountPOList() {
		// TODO Auto-generated method stub
		ArrayList<BankAccountPO> BankAccountList  =  new ArrayList<>();
		BankAccountList.add(new BankAccountPO("SS141250110",200000));
		return BankAccountList;
	}

	public boolean addBankAccount(String name, double balance) {
		// TODO Auto-generated method stub
		if(balance>0){
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<BankAccountVO> getBankAccountVOList() {
		// TODO Auto-generated method stub
		ArrayList<BankAccountVO> BankAccountList  =  new ArrayList<>();
		BankAccountList.add(new BankAccountVO("SS141250110",200000));
		return BankAccountList;
	}

	@Override
	public ArrayList<BankAccountVO> getBankAccountVOListByKey(String key) {
		// TODO Auto-generated method stub
		if(key.equals("ss")){
			ArrayList<BankAccountVO> BAVOList = new ArrayList<>();
			BAVOList.add(new BankAccountVO("ss141250110",20000));
			return BAVOList;
		}
		return null;
	}

	@Override
	public BankAccountVO getBankAccountVO(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modifyBankAccount(String oldName, String newName) {
		// TODO Auto-generated method stub
		 if(oldName.length()!=10){
			 return false;
		 }
		 if(newName.length()!=10){
			 return false;
		 }
		 return true;
	}

	@Override
	public void deleteBankAccount(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BankAccountVO BankAccountPoToVo(BankAccountPO po) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addBankAccount(String name, String balance) {
		// TODO Auto-generated method stub
		return false;
	}



}
