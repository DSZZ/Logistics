package edu.nju.logistics.vo.financial;

import java.util.ArrayList;

import edu.nju.logistics.vo.SheetVO;
/**
 * 
 * @author 侍硕  
 * 付款单PO
 *
 */
public class PaymentVO  extends SheetVO {
	 private static final long serialVersionUID = 1L;

		
		/**
		 * 付款的银行账户的列表
		 */
		private ArrayList<BankAccountVO>  BankAccountList;
		private SystemCostVO  systemcostVO ;
		private String bankAccountName;
		
		public PaymentVO(String id, String employeeID, String type, String date, String state, String description,
				SystemCostVO cost,String bankAccountName){
			super(id, employeeID, type, date, state, description);
			
			this.systemcostVO =cost;
			this.setBankAccountName(bankAccountName);
			BankAccountList = new ArrayList<>();
		}
	
		public ArrayList<BankAccountVO> getBankAccountList() {
			return BankAccountList;
		}
	
		
		public void addBankAccountList(BankAccountVO vo){
			     this.BankAccountList.add(vo);
		}
		
		public SystemCostVO getSystemCostVO(){
			  return this.systemcostVO;
		}

		public String getBankAccountName() {
			return bankAccountName;
		}

		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
		}
	
	
	
    
	
}