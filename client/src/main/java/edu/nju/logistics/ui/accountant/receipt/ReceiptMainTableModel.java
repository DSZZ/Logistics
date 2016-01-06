package edu.nju.logistics.ui.accountant.receipt;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;

public class ReceiptMainTableModel extends AbstractTableModel{
	   private static final long serialVersionUID = 1L;

	  /**
	    * 用于表格显示的历史收款单概要信息列表
	    */
	   private ArrayList<ReceiptInfo>  receiptInfoList;
	   
	   private Vector<String> title=null;
	   private Vector<Vector<String>> data=null;
	   public  ReceiptMainTableModel(ArrayList<ReceiptInfo>  receiptInfoList){
		   super();
		   this.init ( receiptInfoList);
	}
	
	public void init(ArrayList<ReceiptInfo>  receiptInfoList){
		
		  this.receiptInfoList = receiptInfoList;
		  title = new Vector<String>();
		  data = new Vector<Vector<String>>();
		  initModel();
	}
	
	public void initModel(){
		title.add("收款单号");
		title.add("营业厅编号");
		title.add("收款日期");
		title.add("收款额");
		
		for(int i = 0; i< receiptInfoList.size();i++){
			Vector<String> temp = new Vector<String> ();
			temp.add(receiptInfoList.get(i).getReceiptID());
			temp.add(receiptInfoList.get(i).getInstitutionID());
			temp.add(receiptInfoList.get(i).getDate());
            temp.add(receiptInfoList.get(i).getTotalFee()+"");
			data.add(temp);
		}
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return  title.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return data.get(arg0).get(arg1);
	}
    
	
	@Override
	public String getColumnName(int arg0) {
		
		return title.get(arg0);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		//历史收款单不可编辑
	          	return false;
	}
	
	/**
	 * 按营业厅或者按日期检索收款单后
	 * 调用此方法来更新表格中显示的数据
	 */
    public void  refresh(ArrayList<ReceiptInfo>  receiptInfoList){
    	   
    	    this.data=new Vector<Vector<String>>();
    	    this.receiptInfoList=receiptInfoList;
    	    
    	    for(int i = 0; i< receiptInfoList.size();i++){
    			Vector<String> temp = new Vector<String> ();
    			temp.add(receiptInfoList.get(i).getReceiptID());
    			temp.add(receiptInfoList.get(i).getDate());
    			temp.add(receiptInfoList.get(i).getInstitutionID());
                temp.add(receiptInfoList.get(i).getTotalFee()+"");
    			data.add(temp);
    		}
    	    fireTableDataChanged();
    }
}
