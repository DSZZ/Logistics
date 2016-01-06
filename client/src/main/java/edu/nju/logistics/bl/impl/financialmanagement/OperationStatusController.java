package edu.nju.logistics.bl.impl.financialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.OperationStatusBLService;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.financial.PaymentInfo;

public class OperationStatusController  implements OperationStatusBLService{
     private OperationStatusBL bl ;
     
     public OperationStatusController(OperationStatusBL bl){
    	    this.bl = bl;
     }
     
	@Override
	public ArrayList<PaymentInfo> showPaymentList(String date1, String date2) {
		// TODO Auto-generated method stub
		return bl.showPaymentList(date1, date2);
	}

	@Override
	public ArrayList<ReceiptInfo> showReceiptList(String date1, String date2) {
		// TODO Auto-generated method stub
		return bl.showReceiptList(date1, date2);
	}

	@Override
	public void outputChart() {
		// TODO Auto-generated method stub
		bl.outputChart();
	}

}
