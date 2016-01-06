package edu.nju.logistics.po.financial;
import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.vo.financial.BankAccountVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

/**
 * 
 * @author 侍硕  
 * 付款单PO
 *
 */
public class PaymentPO extends SheetPO implements Serializable {
	
	 private static final long serialVersionUID = 1L;

		
		/**
		 * 付款的银行账户的列表
		 */
		private ArrayList<BankAccountVO>  BankAccountList;
		private SystemCostPO  systemcostPO ;
		private String bankAccountName;
		
		
		public PaymentPO(String id, String employeeID, String type, String date, String state, String description,
				SystemCostPO cost,String bankAccountName){
			super(id, employeeID, type, date, state, description);
			
			this.systemcostPO =cost;
			this.setBankAccountName(bankAccountName);
			BankAccountList = new ArrayList<>();
		}
	
		public ArrayList<BankAccountVO> getBankAccountList() {
			return BankAccountList;
		}
	
		
		public void addBankAccountList(BankAccountVO vo){
			     this.BankAccountList.add(vo);
		}
		
		public SystemCostPO getSystemCostPO(){
			  return this.systemcostPO;
		}

		public String getBankAccountName() {
			return bankAccountName;
		}

		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
		}

}