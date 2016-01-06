package edu.nju.logistics.ui.branchstaff.load.chooseCar;

import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;

public class OKCaller_ChooseCar implements OKCallerService{

	private ArrayList<CarPO> carList;
	private ShowPanel_Load owner;
	
	public OKCaller_ChooseCar(ShowPanel_Load owner, ArrayList<CarPO> carList) {
		this.carList = carList;
		this.owner = owner;
	}

	@Override
	public void select(int[] selectedRows) {
		CarPO car = null;
		if(selectedRows.length < 1)
			return;
		car = carList.get(selectedRows[0]);
		owner.chooseCar(car.getId());
		
	}

	@Override
	public String getButtonName() {
		return "装到此车";
	}

}
