package edu.nju.logistics.ui.branchstaff.load.chooseDestination;


import edu.nju.logistics.ui.branchstaff.load.ShowPanel_Load;
import edu.nju.logistics.ui.util.tableBuilder.Refresher;

public class ChooseCenter implements Refresher{

	private ShowPanel_Load owner;
	public ChooseCenter(ShowPanel_Load showPanel_Load) {
		owner = showPanel_Load;
	}

	@Override
	public void refresh() {
		owner.chooseDestination(null,null);
	}

	@Override
	public String getButtonName() {
		return "发往上级中转中心";
	}
}
