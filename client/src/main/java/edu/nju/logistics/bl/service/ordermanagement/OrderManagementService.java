package edu.nju.logistics.bl.service.ordermanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.ordermanagement.*;

public interface OrderManagementService {

	/**
	 * @return 返回报价和预期时间的VO
	 * @param orderVO 这是presentation曾创建的VO，里面包含了创建对象的各类信息
	 * @throws RemoteException 
	 */
	public void createOrder(OrderVO orderVO, String courierid) throws RemoteException;
	
	/**
	 * @param courierid 
	 * @return 返回待派送的订单列表VO
	 * @throws RemoteException 
	 */
	public ArrayList<OrderVO> getCourierUndispatchedList (String courierid) throws RemoteException;
	
	/**
	 * @return 返回某营业厅未收款的订单列表
	 * @throws RemoteException 
	 */
	public ArrayList<OrderVO> getUnpayedOrderList() throws RemoteException;

	/**
	 * 将订单从未收款列表中移除
	 * @param payOrderList 付款的名单
	 * @throws RemoteException 
	 */
	public void payOrder(ArrayList<OrderVO> payOrderList) throws RemoteException;

	/**
	 * 快递员派送订单
	 * @param selectedOrderList 派送订单的列表
	 * @param courierid 执行派送任务的快递员编号
	 * @throws RemoteException 
	 */
	public void dispatchOrder(ArrayList<OrderVO> selectedOrderList,String courierid) throws RemoteException;
	
	/**
	 * 获得本营业厅的滞留列表
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<OrderVO> getStayOrderList() throws RemoteException;
	
	/**
	 * 将selectedOrderList内的内容委派个courierid指向的的快递员
	 * @param selectedOrderList
	 * @param id
	 * @throws RemoteException 
	 */
	public void distributeOrderToCourier(ArrayList<OrderVO> selectedOrderList, String courierid) throws RemoteException;
}
