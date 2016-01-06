package edu.nju.logistics.ui.branchstaff.receive.chooseTransferSheet;

import java.util.ArrayList;

import edu.nju.logistics.ui.branchstaff.receive.ShowPanel_Receive;
import edu.nju.logistics.ui.util.tableBuilder.OKCallerService;
import edu.nju.logistics.vo.TransferSheetVO;

public class OKCaller_ChooseTransferSheet implements OKCallerService{

	private ArrayList<TransferSheetVO> transferSheetVOList;
	private ShowPanel_Receive owner;
	
	public OKCaller_ChooseTransferSheet(ShowPanel_Receive showPanel_receive,
			ArrayList<TransferSheetVO> transferSheetVOList) {
		this.transferSheetVOList = transferSheetVOList;
		this.owner = showPanel_receive;
	}

	@Override
	public void select(int[] selectedRows) {
		TransferSheetVO sheet = null;
		if(selectedRows.length < 1)
			return;
		sheet = transferSheetVOList.get(selectedRows[0]);
		owner.chooseTransferSheet(sheet);
	}

	@Override
	public String getButtonName() {
		return "接收选定的中转单";
	}

}
