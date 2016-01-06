package edu.nju.logistics.bl.impl.checkorder;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.ordermanagement.GetOrderPO;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.vo.checkOrder.CheckOrderVO;

public class Mock_OrderServcie  implements GetOrderPO {


	public CheckOrderVO CheckOrder(String id) {
		// TODO Auto-generated method stub
		String []  track= {"南京市营业厅", "南京市中转中心","苏州市营业厅","苏州市中转中心"};
		
		CheckOrderVO vo = new CheckOrderVO(CheckOrderVO.OrderStatus.received, track);
		return vo;
	}

	@Override
	public OrderPO getOrderPO(String orderid) throws RemoteException {
		// TODO Auto-generated method stub
//          ArrayList<String>   track=  new ArrayList<>();
//		  OrderPO po = new OrderPO(null, orderid);
        return null;
		
	}
   
}
