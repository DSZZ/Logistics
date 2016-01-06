package edu.nju.logistics.ui.branchstaff.distribute.chooseCourier;

import java.util.ArrayList;

import edu.nju.logistics.ui.branchstaff.distribute.ShowPanel_Distribute;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.UserVO;

public class OKCaller_ChooseCourier implements OKCallerService{

	private ArrayList<UserVO> courierList;
	private ShowPanel_Distribute owner;
	
	public OKCaller_ChooseCourier(ShowPanel_Distribute owner,
			ArrayList<UserVO> courierList) {
		this.courierList = courierList;
		this.owner = owner;
	}

	@Override
	public void select(int[] selectedRows) {
		UserVO courier = null;
		if(selectedRows.length < 1)
			return;
		courier = courierList.get(selectedRows[0]);
		owner.chooseCourier(courier);
		
	}

	@Override
	public String getButtonName() {
		return "分配";
	}

}
