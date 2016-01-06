package edu.nju.logistics.ui.branchstaff.distribute.chooseCourier;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.UserVO;

public class VectorBuilder_ChooseCourier implements VectorGetter{
	
	private ArrayList<UserVO> courierList;

	public VectorBuilder_ChooseCourier(ArrayList<UserVO> courierList) {
		this.courierList = courierList;
	}

	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("快递员编号");
		columnNameVector.add("姓名");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(UserVO courier : courierList){
			Vector<Object> orderValue = new Vector<>();
			orderValue.add(courier.getId());
			orderValue.add(courier.getName());
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return courierList.size();
	}

}
