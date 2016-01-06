package edu.nju.logistics.vo.courierIncomeReceipt;

public class ShortOrderVO {

	private String orderID;
	private double orderFee;
	
	/**
	 * 收款快递员的信息
	 */
	private String courierID;
	private String courierName;
	
	public ShortOrderVO(String id, double fee,String courierid,String couriername) {
		super();
		this.orderID = id;
		this.orderFee = fee;
		this.courierID = courierid;
		this.courierName = couriername;
	}
	public String getOrderId() {
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
