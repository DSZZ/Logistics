package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.PaymentControllerService;
import edu.nju.logistics.vo.financial.PaymentInfo;
import edu.nju.logistics.vo.financial.PaymentVO;

public class PaymentController  implements PaymentControllerService{
	
    private PaymentBL bl;
    
    public  PaymentController( PaymentBL bl){
    	  this.setBl(bl);
    }
	@Override
	public ArrayList<PaymentVO> getPaymentVOList() {
		// TODO Auto-generated method stub
		return bl.getPaymentVOList();
	}

	@Override
	public ArrayList<PaymentInfo> getPaymentInfoList() {
		// TODO Auto-generated method stub
		return bl.getPaymentInfoList();
	}

	@Override
	public PaymentVO showOldChart(String ID) {
		// TODO Auto-generated method stub
		return bl.showOldChart(ID);
	}

	@Override
	public PaymentVO showNewChart() throws RemoteException {
		// TODO Auto-generated method stub
		return bl.showNewChart();
	}

	@Override
	public void createPayment(PaymentVO paymentVO) {
		// TODO Auto-generated method stub
	    bl.createPayment(paymentVO);
	}
	public PaymentBL getBl() {
		return bl;
	}
	public void setBl(PaymentBL bl) {
		this.bl = bl;
	}
          
}
