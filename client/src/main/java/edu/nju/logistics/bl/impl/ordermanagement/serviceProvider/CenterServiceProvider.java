package edu.nju.logistics.bl.impl.ordermanagement.serviceProvider;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.ordermanagement.OrderManagementBLCenterService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.orderdata.OrderStatus;
import edu.nju.logistics.server.data.impl.orderdata.OrderDataManager;

public class CenterServiceProvider implements OrderManagementBLCenterService{

	private OrderDataService orderDataManager;
	
	public CenterServiceProvider() throws RemoteException {
		try {
			orderDataManager = (OrderDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/OrderData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void changeOrderStatus(String orderid, OrderStatus newOrderStatus) throws RemoteException {
		orderDataManager.changeOrderStatus(orderid, newOrderStatus);
	}
	@Override
	public void addOrderHistoryTrace(String orderid, String newHistoryTrace) throws RemoteException {
		orderDataManager.addOrderHistoryTrace(orderid, newHistoryTrace);
	}
	@Override
	public void receiveSuccess(String orderid, String originid, String destinationid) throws RemoteException {
		boolean destinationIsBranch = isBranch(destinationid);
		
		if(destinationIsBranch){
			System.out.println("exexcute move: orderid : " + orderid + "to " + destinationid);
			orderDataManager.moveTo(orderid, destinationid);
		}
	}

	@Override
	public void loadSuccess(String orderid, String originid, String destinationid) throws RemoteException {
		
		boolean originIsBranch = isBranch(originid);
		boolean destinationIsBranch = isBranch(destinationid);
		if(originIsBranch){
			if(destinationIsBranch){
				System.out.println("exexcute move: orderid : " + orderid + "to [branchCloud]" );
				orderDataManager.moveTo(orderid, OrderDataManager.branchCloud);
			}else{
				System.out.println("exexcute move: orderid : " + orderid + "to " + destinationid);
				orderDataManager.moveTo(orderid, destinationid);
			}
		}
	}

	private boolean isBranch(String institutionid){
		if(institutionid.length() < 7)
			return false;
		else
			return true;
	}

}
