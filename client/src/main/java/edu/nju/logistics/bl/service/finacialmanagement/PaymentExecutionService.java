package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.financial.PaymentPO;

public interface PaymentExecutionService {
	
	public void executePaymentItem(PaymentPO paymentpo) throws RemoteException;
}
