package edu.nju.logistics.ui.accountant.receipt;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ShortOrderVO;
/**
 * 该类是在查看收款单详细信息时表格使用到的Model辅助类
 * 拥有一个收款单VO的成员变量，在构造函数中需要传递一个VO 对象。
 * @author 侍硕
 *
 */
public class ReceiptOBTableModel extends AbstractTableModel{
	    private static final long serialVersionUID = 1L;
	    private CourierIncomeReceiptVO receiptVO;
	    private  ArrayList<ShortOrderVO> orderList; 
	    private Vector<String> title=null;
	    private Vector<Vector<String>> data=null;
	   
		public  ReceiptOBTableModel(CourierIncomeReceiptVO receiptVO){
			   super();
			   this.init (receiptVO);
		}
		
		public void init(CourierIncomeReceiptVO receiptVO){
			
		      this.setReceiptVO(receiptVO);
		      this.orderList=receiptVO.getOrderList();
			  title = new Vector<String>();
			  data = new Vector<Vector<String>>();
			  initModel();
		}
		
		public void initModel(){
			title.add("订单条形码");
			title.add("订单费");
			title.add("快递员ID");
			title.add("快递员姓名");
			
			for(int i = 0; i< orderList.size();i++){
				Vector<String> temp = new Vector<String> ();
			    temp.add(orderList.get(i).getOrderId());
			    temp.add(orderList.get(i).getFee()+"");
			    temp.add(orderList.get(i).getCourierid());
			    temp.add(orderList.get(i).getCouriername());
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
	public Object getValueAt(int rowIndex,	 int columnIndex) {
		// TODO Auto-generated method stub
		return data.get(rowIndex).get(columnIndex);
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

	public CourierIncomeReceiptVO getReceiptVO() {
		return receiptVO;
	}

	public void setReceiptVO(CourierIncomeReceiptVO receiptVO) {
		this.receiptVO = receiptVO;
	}
	


}
