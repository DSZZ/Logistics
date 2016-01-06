package edu.nju.logistics.bl.impl.financialmanagement;

public class BankAccountTest {
	public static void main(String args []){
		BankAccountBL bl = new  BankAccountBL();
		bl.addBankAccount("ss14", "200000");
		bl.addBankAccount("ss15", "30000");
		bl.addBankAccount("ss16", "40000");
		bl.updateData();
      //  bl.findBankAccountPOList();
		
	}
	
    
	

}
