package edu.nju.logistics.ui.branchstaff.distribute.chooseOrder;

import java.util.ArrayList;

import edu.nju.logistics.ui.branchstaff.distribute.ShowPanel_Distribute;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OKCaller_ChooseOrder implements OKCallerService{

	private ArrayList<OrderVO> stayOrderList;
	private ShowPanel_Distribute owner;
	
	public OKCaller_ChooseOrder(ShowPanel_Distribute owner,
			ArrayList<OrderVO> stayOrderList) {
		this.stayOrderList = stayOrderList;
		this.owner = owner;
	}

	@Override
	public void select(int[] selectedRows) {
		ArrayList<OrderVO> selectedOrderList = new ArrayList<>();
		if(selectedRows.length < 1)
			return;
		for(int rowIndex : selectedRows){
			selectedOrderList.add(stayOrderList.get(rowIndex));
		}
		owner.chooseOrder(selectedOrderList);
		
	}

	@Override
	public String getButtonName() {
		return "分配";
	}

}
