package edu.nju.logistics.ui.accountant.bankaccount;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import edu.nju.logistics.bl.impl.financialmanagement.BankAccountController;
import edu.nju.logistics.vo.financial.BankAccountVO;
public class BATableModel  extends AbstractTableModel{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		/*
	     * 管理银行账户的controller
	     */
	    private BankAccountController controller;
	    /*
	     * 用于界面展示的VO表
	     */
	    private ArrayList<BankAccountVO> baVOList;
	    /**
	     * 用于构造table model
	     */
	    private Vector<String> title=null;
		private Vector<Vector<String>> data=null;

		public BATableModel (BankAccountController controller){
			super();
			this.init( controller);
		}
		
		private void init(BankAccountController controller){
			this.controller =controller;
			baVOList = new ArrayList<>();
			
			title = new Vector<String>();
			data = new Vector<Vector<String>>();
			
			baVOList=controller.getBankAccountVOList();
		//	System.out.println("The data in volist is :");
			
		//	for(int i=0;i<baVOList.size();i++){
		//		System.out.println(baVOList.get(i).getName()+" "+baVOList.get(i).getBalance());
		//	}
			
			String a = "账户";
			String b = "余额";

			title.add(a);
			title.add(b);
		
			 
			for(int i=0;i<baVOList.size();i++){
				Vector<String> temp = new Vector<String> ();
				temp.add(baVOList.get(i).getName());
				temp.add(baVOList.get(i).getBalance()+"");
				data.add(temp);
			}
		
		}
        //获取列数
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return title.size();
		}
		//获取行数
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.size();
		}
		//获取某行某列的值
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return data.get(rowIndex).get(columnIndex);
		}
		
		@Override
		public String getColumnName(int arg0) {
			
			return title.get(arg0);
		}
		
		@Override
	       public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	       {	      
	           controller.modifyBankAccount(data.get(rowIndex).get(columnIndex), (String)aValue);
			   data.get(rowIndex).set(columnIndex,(String)aValue);
	           fireTableCellUpdated(rowIndex, columnIndex);			
			
	       }
		
		
		
		@Override
		public boolean isCellEditable(int row, int column) {
				if(column==0){
				     return true;
				}
			return false;
		}
		
		public void removeRow(int rowIndex){
			String nameToDelete = (String)data.get(rowIndex).get(0);
			data.remove(rowIndex);
			fireTableDataChanged();
			controller.deleteBankAccount(nameToDelete);
		}
		
		public void addRow(String name,String balance){
			
			Vector<String> temp = new Vector<String>();
			temp.add(name);
			temp.add(balance);
			data.add(temp);
			fireTableDataChanged();
			controller.addBankAccount(name, balance);
		}
        
	    public BankAccountController  getController(){
	    	   return this.controller;
	    }
	    
	    public boolean isAlreadyExist(String name){
	    	   for(int i=0;i<this.baVOList.size();i++){
	    		   if(baVOList.get(i).getName().equals(name)){
	    			   return true;
	    			  
	    		   }
	    	   }
	    	   return false;
	    }
	    
	    public  void UpdateVOListByKey(String key){
	    	  baVOList = controller.getBankAccountVOListByKey(key);
	    	  //重新设置删选后的数据
	    	  Vector<Vector<String>> data = new Vector<Vector<String>>();
	    	  for(int i=0;i<baVOList.size();i++){
					Vector<String> temp = new Vector<String> ();
					temp.add(baVOList.get(i).getName());
					temp.add(baVOList.get(i).getBalance()+"");
					data.add(temp);
				}
	    	  
	    	  this.data=data;
	    	  
	    	  fireTableDataChanged();
	    }
}
