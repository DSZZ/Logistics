package edu.nju.logistics.bl.impl.checkorder;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.checkorder.CheckOrderBLService;
import edu.nju.logistics.po.orderdata.OrderPO;

public class CheckOrderController implements CheckOrderBLService{
	 CheckOrderBL bl;
	 public CheckOrderController(CheckOrderBL bl){
		  this.bl = bl;
	 }
	@Override
	public boolean isValidInput(String orderID) {
		// TODO Auto-generated method stub
		return bl.isValidInput(orderID);
	}

	@Override
	public OrderPO getOrderPO(String ID) throws RemoteException {
		// TODO Auto-generated method stub
		return bl.getOrderPO(ID);
	}

}
