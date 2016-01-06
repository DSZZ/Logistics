package edu.nju.logistics.po.orderdata;

import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class SendOrderSheetPO extends SheetPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208025439672523885L;
	
	private OrderVO orderVO;
	private String institutionid;
	private String branchName;
	
	public String getBranchName() {
		return branchName;
	}

	public String getInstitutionid() {
		return institutionid;
	}

	public OrderVO getOrderVO() {
		return orderVO;
	}

	public SendOrderSheetPO(String id, String employeeID, String type,
			String date, String state, String description,
			OrderVO orderVO,String institutionid,String branchName) {
		super(id, employeeID, type, date, state, description);
		this.orderVO = orderVO;
		this.institutionid = institutionid;
		this.branchName = branchName;
	}

}