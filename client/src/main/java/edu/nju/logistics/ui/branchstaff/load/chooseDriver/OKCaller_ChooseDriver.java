package edu.nju.logistics.ui.branchstaff.load.chooseDriver;

import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;

public class OKCaller_ChooseDriver implements OKCallerService{

	private ArrayList<DriverPO> driverList;
	private ShowPanel_Load owner;
	
	public OKCaller_ChooseDriver(ShowPanel_Load owner, ArrayList<DriverPO> driverList) {
		this.driverList = driverList;
		this.owner = owner;
	}

	@Override
	public void select(int[] selectedRows) {
		DriverPO driver = null;
		if(selectedRows.length < 1)
			return;
		driver = driverList.get(selectedRows[0]);
		owner.chooseDriver(driver.getId(),driver.getName());
		
	}

	@Override
	public String getButtonName() {
		return "选择此司机";
	}

}
