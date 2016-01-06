package edu.nju.logistics.po.courierIncomeReceipt;

import java.io.Serializable;
import java.util.ArrayList;

public class CourierIncomeReceiptPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<ShortOrderPO> orderList;
	
	/**
	 * format: 2009/02/05
	 */
	private String ReceiptID;
	public void setReceiptID(String receiptID) {
		ReceiptID = receiptID;
	}
	private String date;
	private double totalFee = 0;
	private String institutionid;
	
	public CourierIncomeReceiptPO(ArrayList<ShortOrderPO> orderList,
			String time, String institutionid) {
		super();
		this.orderList = orderList;
		this.ReceiptID="";
		this.date = time;
		this.institutionid = institutionid;
		
		for(ShortOrderPO shortOrder : orderList){
			totalFee += shortOrder.getFee();
		}
	}
	public ArrayList<ShortOrderPO> getOrderList() {
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

}
