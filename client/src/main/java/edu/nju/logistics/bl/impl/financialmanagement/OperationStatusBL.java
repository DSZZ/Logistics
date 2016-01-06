package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.OperationStatusBLService;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.financial.PaymentInfo;
/**
 * 经营情况表的逻辑实现类
 * @author 侍硕
 *
 */
public class OperationStatusBL implements OperationStatusBLService{
    private PaymentBL paymentbl;
    private ReceiptBL receiptbl;
    private ArrayList<PaymentInfo> paymetInfoList;
    private ArrayList<ReceiptInfo> receiptInfoList;
    public OperationStatusBL() throws RemoteException{
    	this.paymentbl = new PaymentBL();
    	System.out.println("OperationStatusBL 构造方法--");
    	this.receiptbl = new ReceiptBL();
    	System.out.println("OperationStatusBL 构造方法--");
    	this.paymetInfoList = this.paymentbl.getPaymentInfoList();
    	this.receiptInfoList = this.receiptbl.getInfoList();
    }
    
    
	@Override
	public ArrayList<PaymentInfo> showPaymentList(String date1, String date2) {
		// TODO Auto-generated method stub
		ArrayList<PaymentInfo> tempList = new ArrayList<>();
		for(int i=0;i<this.paymetInfoList.size();i++){
			if(this.paymetInfoList.get(i).getDate().compareTo(date1)>=0
					&&this.paymetInfoList.get(i).getDate().compareTo(date2)<=0){
				tempList.add(paymetInfoList.get(i));
			}
		}
		return tempList;
	}

	@Override
	public ArrayList<ReceiptInfo> showReceiptList(String date1, String date2) {
		// TODO Auto-generated method stub
		ArrayList<ReceiptInfo> tempList = new ArrayList<>();
		for(int i=0;i<this.receiptInfoList.size();i++){
			if(this.receiptInfoList.get(i).getDate().compareTo(date1)>=0
					&&this.receiptInfoList.get(i).getDate().compareTo(date2)<=0){
				tempList.add(receiptInfoList.get(i));
			}
		}
		return tempList;
	}

	@Override
	public void outputChart() {
		// TODO Auto-generated method stub
		
	}

}
