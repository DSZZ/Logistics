package edu.nju.logistics.po;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.po.orderdata.OrderPO;

/**
 * 接收货物的单据
 * @author HanzZou
 *
 */
public class ReceiveItemSheetPO extends SheetPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReceiveItemSheetPO(String id, String employeeID, String type, String date, String state, String description,
			TransferSheetPO po) {
		super(id, employeeID, type, date, state, description);
		this.po = po;
	}

	private TransferSheetPO po;

	public TransferSheetPO getPO() {
		return po;
	}

	public void setPO(TransferSheetPO po) {
		this.po = po;
	}
	
}
