package edu.nju.logistics.ui.courier.dispatch;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class VectorBuilder_Dispatch implements VectorGetter{

	private ArrayList<OrderVO> undispatchedOrderList;
	
	public VectorBuilder_Dispatch(ArrayList<OrderVO> undispatchedOrderList) {
		this.undispatchedOrderList = undispatchedOrderList;
	}

	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("订单编号");
		columnNameVector.add("详细地址");
//		columnNameVector.add("收件人姓名");
//		columnNameVector.add("收件人单位");
		columnNameVector.add("收件人电话");
		columnNameVector.add("收件人手机");
		columnNameVector.add("种类");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(OrderVO order : undispatchedOrderList){
			Vector<Object> orderValue = new Vector<>();
			
			String[] address = order.getReceiverAddress().split("-");
			
			orderValue.add(order.getOrderID());
			orderValue.add(address[2] + " - " + address[3]);
//			orderValue.add(order.getReceiverName());
//			orderValue.add(order.getReceiverCompany());
			orderValue.add(order.getReceiverFixedPhone());
			orderValue.add(order.getReceiverMobilePhone());
			orderValue.add(order.getOrderKind());
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return undispatchedOrderList.size();
	}


}
