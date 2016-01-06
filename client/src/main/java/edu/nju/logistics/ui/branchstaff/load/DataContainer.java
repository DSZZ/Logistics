package edu.nju.logistics.ui.branchstaff.load;

import java.util.ArrayList;

import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class DataContainer {

	public ArrayList<OrderVO> selectedOrderList;
	public String destinationid;
	public String destinationname;
	public String carid;
	public String driverid;
	public String drivername;
	public String observer;
	public String supercargo;
	public double fee;
	
	public DataContainer() {
		// TODO 自动生成的构造函数存根
	}

	public DataContainer(ArrayList<OrderVO> selectedOrderList,
			String destinationid, String destinationname, String carid,
			String driverid, String drivername, String observer,
			String supercargo, double fee) {
		super();
		this.selectedOrderList = selectedOrderList;
		this.destinationid = destinationid;
		this.destinationname = destinationname;
		this.carid = carid;
		this.driverid = driverid;
		this.drivername = drivername;
		this.observer = observer;
		this.supercargo = supercargo;
		this.fee = fee;
	}
	
	

}
