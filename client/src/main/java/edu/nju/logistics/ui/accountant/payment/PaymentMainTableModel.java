package edu.nju.logistics.ui.accountant.payment;
import edu.nju.logistics.vo.financial.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
/**
 * 表格的辅助类
 * 进入成本结算界面时要显示历史付款单的概要信息列表。在新建付款单并送往总经理审批时，要更新该列表
 * 为了再下次进入该界面时仍然可以看到那个“提交中”的付款单，所以我们需要在服务器上插入新建的PO 其状态为提交
 * 待总经理审核通过后其状态将会被改为通过
 * 
 * 该类会在创建时委托PayemntController从服务器读取全部的收款单，然后转化为信息类用于显示
 * 新建付款单的后置条件：
 *  1.VO转化为PO，插入BL中的POList、同时更新VOList和InfoList
 *  2.送往总经理审批
 *  3.更新TableModel中InfoList
 * @author  侍硕
 *
 */
public class PaymentMainTableModel  extends AbstractTableModel{
    
	   private static final long serialVersionUID = 1L;

	  /**
	    * 用于表格显示的历史付款单概要信息列表
	    */
	   private ArrayList<PaymentInfo>  paymentInfoList;
	   
	   private Vector<String> title=null;
	   private Vector<Vector<String>> data=null;
	   
		public  PaymentMainTableModel(ArrayList<PaymentInfo>  paymentInfoList){
			   super();
			   this.init ( paymentInfoList);
		}
		
		public void init(ArrayList<PaymentInfo>  paymentInfoList){
			
			  this.paymentInfoList = paymentInfoList;
			  title = new Vector<String>();
			  data = new Vector<Vector<String>>();
			  initModel();
		}
		
		public void initModel(){
			title.add("付款单号");
			title.add("会计编号");
			title.add("付款日期");
			title.add("付款总额");
			title.add("状态");
			
			for(int i = 0; i< paymentInfoList.size();i++){
				Vector<String> temp = new Vector<String> ();
				temp.add(paymentInfoList.get(i).getSheetID());
				temp.add(paymentInfoList.get(i).getAccountantID());
				temp.add(paymentInfoList.get(i).getDate());
                temp.add(paymentInfoList.get(i).getTotalFee()+"");
                temp.add(paymentInfoList.get(i).getState());
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
		//历史付款单不可编辑
	          	return false;
	}
	
	/**
	 * 新建付款单后调用次方法来更新表格中显示的数据
	 */
    public void  refresh(ArrayList<PaymentInfo>  paymentInfoList){
    	   
    	    this.data=new Vector<Vector<String>>();
    	    this.paymentInfoList=paymentInfoList;
    	    
    	    for(int i = 0; i< paymentInfoList.size();i++){
				Vector<String> temp = new Vector<String> ();
				temp.add(paymentInfoList.get(i).getSheetID());
				temp.add(paymentInfoList.get(i).getAccountantID());
				temp.add(paymentInfoList.get(i).getDate());
                temp.add(paymentInfoList.get(i).getTotalFee()+"");
                temp.add(paymentInfoList.get(i).getState());
				data.add(temp);
			}
    	    fireTableDataChanged();
    }
}
