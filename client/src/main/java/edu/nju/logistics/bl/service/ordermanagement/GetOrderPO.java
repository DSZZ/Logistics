package edu.nju.logistics.bl.service.ordermanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.orderdata.OrderPO;

public interface GetOrderPO {

	/**
	 * 根据id获取orderPO
	 * @param orderid
	 * @return 如果查询不存在，返回 null
	 * @throws RemoteException 网络原因
	 */
	public OrderPO getOrderPO(String orderid) throws RemoteException;
}
