package edu.nju.logistics.bl.impl.ordermanagement.serviceProvider;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.ordermanagement.GetOrderPO;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.orderdata.OrderPO;

public class GetOrderPOImpl implements GetOrderPO{

	private OrderDataService orderDataManager;
	
	public GetOrderPOImpl() throws RemoteException {
		try {
			orderDataManager = (OrderDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/OrderData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrderPO getOrderPO(String orderid) throws RemoteException {
		return orderDataManager.getOrder(orderid);
	}
	
	public static void main(String[] args) {
		OrderPO o = null;
		try {
			GetOrderPOImpl gg = new GetOrderPOImpl();
			gg.addHistoryTest("0001115555","2015.5.23 15:37 快递员张琳芃收件");
			gg.addHistoryTest("0001115555","2015.5.24 22:55 琳达小学营业厅装车");
			gg.addHistoryTest("0001115555","2015.5.25 12:20 兴旺中转中心 接收");
			gg.addHistoryTest("0001115555","2015.5.25 12:20 兴旺中转中心装车 ");
			gg.addHistoryTest("0001115555","2015.5.25 12:20 南师附中营业厅接收");
			gg.addHistoryTest("0001115555","2015.5.25 12:20 快递员小王派件");
			
			o = new GetOrderPOImpl().getOrderPO("0001115555");
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println(o.getHistoryTrace());
	}
	private void addHistoryTest(String orderid, String history) throws RemoteException{
		orderDataManager.addOrderHistoryTrace(orderid, history);
	}
}
