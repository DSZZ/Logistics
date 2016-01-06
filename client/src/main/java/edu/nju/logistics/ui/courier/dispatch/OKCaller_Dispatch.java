package edu.nju.logistics.ui.courier.dispatch;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.reconnection.DisconnectInformer;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OKCaller_Dispatch implements OKCallerService{

	private OrderManagementService orderManagement;
	private ArrayList<OrderVO> undispatchedOrderList;
	private String courierid;
	private DisconnectInformer disconnectInformer;
	
	public OKCaller_Dispatch(OrderManagementService orderManagement,
			ArrayList<OrderVO> undispatchedOrderList,String courierID,
			DisconnectInformer disconnectInformer) {
		this.orderManagement = orderManagement;
		this.undispatchedOrderList = undispatchedOrderList;
		this.courierid = courierID;
		this.disconnectInformer = disconnectInformer;
	}

	@Override
	public void select(int[] selectedRows) {
		ArrayList<OrderVO> selectedOrderList = new ArrayList<>();
		if(selectedRows.length < 1)
			return;
		for(int rowIndex : selectedRows){
			selectedOrderList.add(undispatchedOrderList.get(rowIndex));
		}
		try {
			orderManagement.dispatchOrder(selectedOrderList,courierid);
			GlobalHintInserter.insertGlobalHint("派送成功！");
		} catch (RemoteException e) {
			disconnectInformer.findDisconnect();
			return;
		}
	}

	@Override
	public String getButtonName() {
		return "确认";
	}

}
