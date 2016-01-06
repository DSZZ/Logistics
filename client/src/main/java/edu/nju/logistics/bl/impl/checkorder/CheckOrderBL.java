package edu.nju.logistics.bl.impl.checkorder;


import java.rmi.RemoteException;

import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.GetOrderPOImpl;
import edu.nju.logistics.bl.service.checkorder.CheckOrderBLService;
import edu.nju.logistics.bl.service.ordermanagement.GetOrderPO;
import edu.nju.logistics.po.orderdata.OrderPO;

public class CheckOrderBL implements CheckOrderBLService{
	   GetOrderPO  service ;
	   public CheckOrderBL() throws RemoteException{
		           service = new  GetOrderPOImpl();
	   }
	    @Override
	    public boolean isValidInput(String ID) {
	    	if(ID.length()==10){
	    		return true;
	    	}
		    return false;
	    }

	    @Override
	     public OrderPO getOrderPO(String ID) throws RemoteException {
	         	return  service.getOrderPO(ID);
	     }
   
}
