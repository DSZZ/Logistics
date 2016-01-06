package edu.nju.logistics.bl.service.ordermanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.orderdata.OrderStatus;

public interface OrderManagementBLCenterService {

	public void changeOrderStatus(String orderid, OrderStatus newOrderStatus) throws RemoteException;
	
	public void addOrderHistoryTrace(String orderid, String newHistoryTrace) throws RemoteException;
	
	public void receiveSuccess(String orderID, String originID, String destinationID) throws RemoteException;
	
	public void loadSuccess(String orderID, String originID, String destinationID) throws RemoteException;
}
