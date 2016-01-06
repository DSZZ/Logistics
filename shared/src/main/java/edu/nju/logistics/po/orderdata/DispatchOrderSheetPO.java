package edu.nju.logistics.po.orderdata;

import java.util.ArrayList;

import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class DispatchOrderSheetPO extends SheetPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208025439672523885L;
	
	private ArrayList<OrderVO> selectedOrderList;
	private String institutionid;
	private String courierid;
	
	public String getCourierid() {
		return courierid;
	}

	public String getInstitutionid() {
		return institutionid;
	}

	public ArrayList<OrderVO> getSelectedOrderList() {
		return selectedOrderList;
	}

	public DispatchOrderSheetPO(String id, String employeeID, String type,
			String date, String state, String description,
			ArrayList<OrderVO> selectedOrderList,String institutionid,String courierid) {
		super(id, employeeID, type, date, state, description);
		this.selectedOrderList = selectedOrderList;
		this.institutionid = institutionid;
		this.courierid = courierid;
	}

}