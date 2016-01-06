package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.financial.PaymentPO;
import edu.nju.logistics.vo.financial.PaymentVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class PaymentDST {
     public static void main(String args []) throws RemoteException{
    	   PaymentBL bl = new PaymentBL();
    	   PaymentInsertBL insertbl = new PaymentInsertBL();
    	   for(int i=0;i<5;i++){
    		   PaymentVO vo =bl.showNewChart();
    		   vo.setBankAccountName("ss14");
    		   vo.setEmployeeId("&000002");
        	   PaymentPO po = bl.PaymentVOtoPO(vo);
    	       insertbl.addPayment(po);
    	   }
    	   bl.findPaymentPOList();
     }
}  
