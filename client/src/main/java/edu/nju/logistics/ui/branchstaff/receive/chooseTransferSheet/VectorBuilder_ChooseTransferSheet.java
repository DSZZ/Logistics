package edu.nju.logistics.ui.branchstaff.receive.chooseTransferSheet;

import java.util.ArrayList;
import java.util.Vector;

import edu.nju.logistics.ui.util.tableBuilder.VectorGetter;
import edu.nju.logistics.vo.TransferSheetVO;

public class VectorBuilder_ChooseTransferSheet implements VectorGetter{
	
	private ArrayList<TransferSheetVO> transferSheetVOList;

	public VectorBuilder_ChooseTransferSheet(ArrayList<TransferSheetVO> transferSheetVOList) {
		this.transferSheetVOList = transferSheetVOList;
	}

	@Override
	public Vector<String> getColumnNameVector() {
		Vector<String> columnNameVector = new Vector<>();
		columnNameVector.add("中转单编号");
		columnNameVector.add("始发地id");
		columnNameVector.add("装车时间");
		columnNameVector.add("订单个数");
		columnNameVector.add("车辆代号");
		columnNameVector.add("押运员");
		return columnNameVector;
	}

	@Override
	public Vector<Vector<Object>> getTableValueVector() {
		Vector<Vector<Object>> tableValueVector = new Vector<>();
		
		for(TransferSheetVO transferSheet : transferSheetVOList){
			Vector<Object> orderValue = new Vector<>();
			
			orderValue.add(transferSheet.getID());
			orderValue.add(transferSheet.getOriginID());
			orderValue.add(transferSheet.getTime());
			orderValue.add(transferSheet.getItems().size());
			orderValue.add(transferSheet.getTransportationID());
			orderValue.add(transferSheet.getSupercargo());
			
			tableValueVector.add(orderValue);
		}
		return tableValueVector;
	}

	@Override
	public int getRowsCount() {
		return transferSheetVOList.size();
	}

}
