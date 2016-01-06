package edu.nju.logistics.ui.branchstaff.load.chooseOrder;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class VectorBuilder_ChooseOrder implements VectorGetter{
	
	private ArrayList<OrderVO> stayOrderList;

	public VectorBuilder_ChooseOrder(ArrayList<OrderVO> stayOrderList) {
		this.stayOrderList = stayOrderList;
	}

	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("订单编号");
		columnNameVector.add("重量");
		columnNameVector.add("种类");
		columnNameVector.add("发往省份");
		columnNameVector.add("发往城市");
		columnNameVector.add("发往区域");
		columnNameVector.add("详细地址");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(OrderVO order : stayOrderList){
			Vector<Object> orderValue = new Vector<>();
			String[] address = order.getReceiverAddress().split("-");
			orderValue.add(order.getOrderID());
			orderValue.add(order.getRealWeight());
			orderValue.add(order.getOrderKind());
			orderValue.add(address[0]);
			orderValue.add(address[1]);
			orderValue.add(address[2]);
			orderValue.add(address[3]);
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return stayOrderList.size();
	}

}
