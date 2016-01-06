package edu.nju.logistics.vo.courierIncomeReceipt;

import java.util.ArrayList;

public class CourierIncomeReceiptVO {

	private ArrayList<ShortOrderVO> orderList;
	
	/**
	 * format: 2009/02/05
	 */
	private String date;
	private String ReceiptID;
	private double totalFee = 0;
	private String institutionid;
	
	public CourierIncomeReceiptVO(ArrayList<ShortOrderVO> orderList,String ReceiptID,
			String time, String institutionid) {
		super();
		this.orderList = orderList;
		this.setReceiptID(ReceiptID);
		this.date = time;
		this.institutionid = institutionid;
		
		for(ShortOrderVO shortOrder : orderList){
			totalFee += shortOrder.getFee();
		}
	}
	public ArrayList<ShortOrderVO> getOrderList() {
		return orderList;
	}
	public String getTime() {
		return date;
	}
	public double getTotalFee() {
		return totalFee;
	}
	public String getInstitutionid() {
		return institutionid;
	}
	public String getReceiptID() {
		return ReceiptID;
	}
	public void setReceiptID(String receiptID) {
		ReceiptID = receiptID;
	}
}
