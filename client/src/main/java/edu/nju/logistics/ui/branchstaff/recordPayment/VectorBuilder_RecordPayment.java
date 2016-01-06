package edu.nju.logistics.ui.branchstaff.recordPayment;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class VectorBuilder_RecordPayment implements VectorGetter{

	private ArrayList<OrderVO> unpayedOrderList;
	public VectorBuilder_RecordPayment(ArrayList<OrderVO> unpayedOrderList) {
		this.unpayedOrderList = unpayedOrderList;
	}
	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("订单编号");
		columnNameVector.add("发往省份");
		columnNameVector.add("收款快递员");
		columnNameVector.add("订单种类");
		columnNameVector.add("包装费");
		columnNameVector.add("运费");
		columnNameVector.add("合计费用");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(OrderVO order : unpayedOrderList){
			Vector<Object> orderValue = new Vector<>();
			
			orderValue.add(order.getOrderID());
			orderValue.add(order.getReceiverAddress().substring(0, 2));
			orderValue.add(order.getCouriername());
			orderValue.add(order.getOrderKind());
			orderValue.add(order.getPackageMoney());
			orderValue.add(order.getTransportationCost());
			orderValue.add(order.getTotalfee());
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}
	@Override
	public int getRowsCount() {
		return unpayedOrderList.size();
	}

}
