package edu.nju.logistics.bl.impl.ordermanagement.serviceProvider;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.ordermanagement.DispatcherAndCreater;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.orderdata.DispatchOrderSheetPO;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.po.orderdata.OrderStatus;
import edu.nju.logistics.po.orderdata.SendOrderSheetPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class DispatcherAndCreaterImpl implements DispatcherAndCreater{

	private OrderDataService orderDataManager;
	public DispatcherAndCreaterImpl() throws RemoteException {
		try {
			orderDataManager = (OrderDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/OrderData");
		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void dispatchOrder(DispatchOrderSheetPO orderVOSheetPO) throws RemoteException {
		ArrayList<OrderVO> selectedOrderList = orderVOSheetPO.getSelectedOrderList();
		String institutionid = orderVOSheetPO.getInstitutionid();
		String courierid = orderVOSheetPO.getCourierid();
		for(OrderVO order : selectedOrderList){
			orderDataManager.deleteOrderFromCourierList(courierid, institutionid, order.getOrderID());
		}
	}

	@Override
	public void sendOrder(SendOrderSheetPO orderVOSheetPO) throws RemoteException {
		OrderVO orderVO = orderVOSheetPO.getOrderVO();
		String institutionid = orderVOSheetPO.getInstitutionid();
		String branchName = orderVOSheetPO.getBranchName();
		
		OrderPO orderPO = new OrderPO(orderVO, TimeUtil.getCurrentDate());

		orderDataManager.insert(orderPO,institutionid);
		String id = orderPO.getOrderID();
		orderDataManager.addOrderHistoryTrace(id,TimeUtil.getCurrentTime() + "-" + branchName +" 营业厅 已收件");
		orderDataManager.changeOrderStatus(id, OrderStatus.senderBranch);
	}

}
