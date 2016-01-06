package edu.nju.logistics.po.courierIncomeReceipt;

import java.io.Serializable;

public class ShortOrderPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderID;
	private double orderFee;
	
	/**
	 * 收款快递员的信息
	 */
	private String courierID;
	private String courierName;
	
	public ShortOrderPO(String id, double fee,String courierid,String couriername) {
		super();
		this.orderID = id;
		this.orderFee = fee;
		this.courierID = courierid;
		this.courierName = couriername;
	}
	public String getId() {
		return orderID;
	}
	public double getFee() {
		return orderFee;
	}
	public String getCourierid() {
		return courierID;
	}
	public String getCouriername() {
		return courierName;
	}
}
