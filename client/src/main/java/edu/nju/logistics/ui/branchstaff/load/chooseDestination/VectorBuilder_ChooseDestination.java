package edu.nju.logistics.ui.branchstaff.load.chooseDestination;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.branchmanagement.BranchVO;

public class VectorBuilder_ChooseDestination implements VectorGetter{
	
	private ArrayList<BranchVO> branchList;
	public VectorBuilder_ChooseDestination(ArrayList<BranchVO> branchList) {
		this.branchList = branchList;
	}

	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("本地营业厅编号");
		columnNameVector.add("位置");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(BranchVO branch : branchList){
			Vector<Object> orderValue = new Vector<>();
			
			orderValue.add(branch.getId());
			orderValue.add(branch.getName());
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return branchList.size();
	}

}
