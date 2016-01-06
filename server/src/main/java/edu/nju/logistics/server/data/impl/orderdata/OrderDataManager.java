package edu.nju.logistics.server.data.impl.orderdata;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import edu.nju.logistics.data.service.institutiondata.InstitutionDataService;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.orderdata.*;

public class OrderDataManager extends UnicastRemoteObject implements OrderDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3069878076676088086L;

	private InstitutionDataService institutionDataGetter;
	private LocalFileManager fileManager;
	
	public OrderDataManager(InstitutionDataService institutionDataGetter) throws RemoteException {
		super();
		this.institutionDataGetter = institutionDataGetter;
		fileManager = new LocalFileManager();
		fileManager.updateLocalInstitution(institutionDataGetter);
	}

	@Override
	public void insert(OrderPO orderPO, String institutionid)
			throws RemoteException {
		fileManager.addToBranchList(STAYLIST,orderPO, institutionid);
		fileManager.addToBranchList(UNPAY,orderPO, institutionid);
	}

	@Override
	public OrderPO getOrder(String id) throws RemoteException {
		return GlobalOrderManager.getOrder(id);
	}

	@Override
	public void updateInstitutionList() throws RemoteException {
		fileManager.updateLocalInstitution(institutionDataGetter);	
	}

	@Override
	public ArrayList<OrderPO> getStayOrderList(String institutionid)
			throws RemoteException {
		return fileManager.getBranchList(STAYLIST, institutionid);
	}

	@Override
	public ArrayList<OrderPO> getUnpayedOrderList(String institutionid)
			throws RemoteException {
		return fileManager.getBranchList(UNPAY, institutionid);
	}

	@Override
	public void updateCourierList(ArrayList<String> courierLidist,
			String institutionId) throws RemoteException {
		fileManager.updateCourierList(courierLidist,institutionId);
	}

	@Override
	public void addOrderToCourierOrderList(ArrayList<OrderPO> orderPOList, String courierid,
			String institutionID) throws RemoteException {
		fileManager.addOrderToCourierOrderList(orderPOList, courierid,
				institutionID);
	}
	
	@Override
	public ArrayList<OrderPO> getCourierOrderList(String courierID, String institutionID)
			throws RemoteException {
		return fileManager.getCourierOrderList(courierID,institutionID);
	}

	@Override
	public void deleteOrderFromBranchList(String listKind,
			String institutionid, String orderID) {
		fileManager.deleteFromBranchList(listKind,orderID, institutionid);
	}

	@Override
	public void deleteOrderFromCourierList(String courierid,
			String institutionid, String orderID) {
		fileManager.deleteFromCourierList(courierid,orderID, institutionid);
	}

	@Override
	public void changeOrderStatus(String orderid, OrderStatus newOrderStatus) {
		OrderPO order = GlobalOrderManager.getOrder(orderid);
		order.changeStatusTo(newOrderStatus);
		GlobalOrderManager.replace(orderid,order);
	}

	@Override
	public void addOrderHistoryTrace(String orderid, String newHistoryTrace) {
		OrderPO order = GlobalOrderManager.getOrder(orderid);
		order.addHistoryTrace(newHistoryTrace);
		GlobalOrderManager.replace(orderid,order);
	}

	@Override
	public void distributeOrderToCourier(ArrayList<String> selectedOrderList,
			String courierid, String institutionId) throws RemoteException {
		for(String orderid : selectedOrderList){
			GlobalOrderManager.moveToCourierList(orderid, institutionId, courierid);
		}
	}

	@Override
	public void moveTo(String orderid, String destinationid)
			throws RemoteException {
		GlobalOrderManager.moveTo(orderid, destinationid);
		
	}
	
}
