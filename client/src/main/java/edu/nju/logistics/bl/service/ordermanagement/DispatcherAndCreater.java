package edu.nju.logistics.bl.service.ordermanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.orderdata.DispatchOrderSheetPO;
import edu.nju.logistics.po.orderdata.SendOrderSheetPO;

public interface DispatcherAndCreater {

	public void dispatchOrder(DispatchOrderSheetPO orderVOSheetPO) throws RemoteException;
	
	public void sendOrder(SendOrderSheetPO orderVOSheetPO) throws RemoteException;

}
