package edu.nju.logistics.ui.branchstaff.load.chooseDestination;

import java.util.ArrayList;

import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.branchmanagement.BranchVO;

public class OKCaller_ChooseDestination implements OKCallerService{

	private ArrayList<BranchVO> branchList;
	private ShowPanel_Load owner;
	
	public OKCaller_ChooseDestination(ShowPanel_Load owner,
			ArrayList<BranchVO> branchList) {
		this.branchList = branchList;
		this.owner = owner;
	}

	@Override
	public void select(int[] selectedRows) {
		BranchVO branch = null;
		if(selectedRows.length < 1)
			return;
		branch = branchList.get(selectedRows[0]);
		System.out.println(selectedRows[0]);
		owner.chooseDestination(branch.getId(),branch.getName());
		
	}

	@Override
	public String getButtonName() {
		return "发往选定的营业厅";
	}

}
