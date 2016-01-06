package edu.nju.logistics.ui.accountant.accountinit;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.vo.financial.BankAccountVO;

public class BankAccountTableModel extends SuperTableModel{
	
    /**
     * 
     */
private static final long serialVersionUID = 1L;

	private ArrayList<BankAccountVO> bankAccountVOList;
    
    public   BankAccountTableModel  (ArrayList<BankAccountVO> bankAccountVOList){
    	this.bankAccountVOList= bankAccountVOList;
    	 initModel();
    }
    public void initModel(){
		title.add("账号名称");
		title.add("账号余额");
		
		for(int i = 0; i< bankAccountVOList.size();i++){
			Vector<String> temp = new Vector<String> ();
			temp.add(bankAccountVOList.get(i).getName());
			temp.add(bankAccountVOList.get(i).getBalance()+"");
			data.add(temp);
		}
	}
    
    


}
