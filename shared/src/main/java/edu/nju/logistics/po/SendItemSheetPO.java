package edu.nju.logistics.po;

import java.io.Serializable;

public class SendItemSheetPO extends SheetPO implements Serializable{

	public SendItemSheetPO(String id, String employeeID, String type, String date, String state, String description, TransferSheetPO po) {
		super(id, employeeID, type, date, state, description);
		this.po = po;
	}

	private TransferSheetPO po;

	public TransferSheetPO getPo() {
		return po;
	}

	public void setPo(TransferSheetPO po) {
		this.po = po;
	}
}
