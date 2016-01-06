package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.PaymentInsertBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.PaymentDataService;
import edu.nju.logistics.po.financial.PaymentPO;

public class PaymentInsertBL implements PaymentInsertBLService{
	  /*
     * 付款单的数据存取逻辑对象
     */
	 private PaymentDataService dataService;
	 
     public PaymentInsertBL(){
    	 try{
		        this.dataService = (PaymentDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/"+"PaymentDSImpl");
	            System.out.println("PaymentDataService awake!");
	     } catch (Exception e) {
		        System.out.println("PaymentDS failed");
			    e.printStackTrace();
	    }
     }
	@Override
	public void addPayment(PaymentPO po) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		try{
		      this.dataService.insert(po);
		}catch (RemoteException e){
			e.printStackTrace();
		}
	}

}
