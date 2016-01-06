package edu.nju.logistics.ui.branchstaff.load.chooseOrder;

import java.util.ArrayList;

import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OKCaller_ChooseOrder implements OKCallerService{

	private ArrayList<OrderVO> stayOrderList;
	private ShowPanel_Load owner;
	
	public OKCaller_ChooseOrder(ShowPanel_Load owner,
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
		return "装车";
	}

}
