package edu.nju.logistics.bl.impl.financialmanagement;

import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.courierIncomeReceipt.*;
public class ReceiptDSTest {
      public static void main(String args[]) throws MalformedURLException, NotBoundException, RemoteException{
    	    CourierIncomeReceiptBL databl = new CourierIncomeReceiptBL();
    	   
    	    for(int j=0;j<10;j++){
    	    	     ArrayList<ShortOrderPO> orderList  = new ArrayList();
    	    
    	             for(int i=0;i<5;i++){
    	    	         orderList.add(new ShortOrderPO("0000"+i, i*100, "#003", "zz"));
    	             }
    	             CourierIncomeReceiptPO po = 
    	             new CourierIncomeReceiptPO(orderList, "2015/12/14", "025001");
    	             po.setReceiptID("R-000"+j);
  	             databl.addCourierIncomeReceiptPO
   	             (po);
    	    }
    	//    ReceiptBL bl = new ReceiptBL();
    	    
      }
}
