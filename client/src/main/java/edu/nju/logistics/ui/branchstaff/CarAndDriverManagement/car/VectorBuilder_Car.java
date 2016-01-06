package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.car;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;

public class VectorBuilder_Car implements VectorGetter{

	private ArrayList<CarPO> carList;
	public VectorBuilder_Car(ArrayList<CarPO> carList) {
		this.carList = carList;
	}
	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> list = new Vector<>();
		list.add("车辆代号");
		list.add("车牌号");
		list.add("开始服役时间");
		list.add("是否忙");
		return list;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		for(CarPO car : carList){
			Vector<Object> orderValue = new Vector<>();
			orderValue.add(car.getId());
			orderValue.add(car.getPlateNum());
			orderValue.add(car.getStartWorkTime());
			if(car.isBusy())
				orderValue.add("忙碌");
			else
				orderValue.add("空闲");
			
			tableValueVector.add(orderValue);
		}
		
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return carList.size();
	}

}
