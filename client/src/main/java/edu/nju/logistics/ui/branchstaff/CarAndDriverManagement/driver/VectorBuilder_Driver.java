package edu.nju.logistics.ui.branchstaff.CarAndDriverManagement.driver;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;

public class VectorBuilder_Driver implements VectorGetter{

	private ArrayList<DriverPO> driverList;
	public VectorBuilder_Driver(ArrayList<DriverPO> driverList) {
		this.driverList = driverList;
	}
	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> list = new Vector<>();
		list.add("司机代号");
		list.add("姓名");
		list.add("手机号");
		list.add("性别");
		list.add("行驶证到期时间");
		list.add("是否忙");
		return list;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		for(DriverPO driver : driverList){
			Vector<Object> orderValue = new Vector<>();
			orderValue.add(driver.getId());
			orderValue.add(driver.getName());
			orderValue.add(driver.getPhone());
			if(driver.isMale())
				orderValue.add("男");
			else
				orderValue.add("女");
			orderValue.add(driver.getLicenseEndTime());
			if(driver.isBusy())
				orderValue.add("忙碌");
			else
				orderValue.add("空闲");
			
			tableValueVector.add(orderValue);
		}
		
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return driverList.size();
	}

}
