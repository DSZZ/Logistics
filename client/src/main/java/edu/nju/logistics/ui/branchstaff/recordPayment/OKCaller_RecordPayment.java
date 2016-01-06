package edu.nju.logistics.ui.branchstaff.recordPayment;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.ui.util.globalHint.GlobalHintInserter;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OKCaller_RecordPayment implements OKCallerService{

	private OrderManagementService orderManagement;
	private ArrayList<OrderVO> unpayedOrderList;
	public OKCaller_RecordPayment(OrderManagementService orderManagement, ArrayList<OrderVO> unpayedOrderList) {
		this.orderManagement = orderManagement;
		this.unpayedOrderList = unpayedOrderList;
	}
	
	@Override
	public void select(int[] selectedRows){
		ArrayList<OrderVO> selectedOrderList = new ArrayList<>();
		if(selectedRows.length < 1)
			return;
		for(int rowIndex : selectedRows){
			selectedOrderList.add(unpayedOrderList.get(rowIndex));
		}
		try {
			orderManagement.payOrder(selectedOrderList);
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		GlobalHintInserter.insertGlobalHint("收款成功！");
	}

	@Override
	public String getButtonName() {
		return "确认";
	}
}
