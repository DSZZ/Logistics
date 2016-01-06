package edu.nju.logistics.vo.ordermanagement;

import java.io.Serializable;


public class OrderVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4887457387075736605L;

	private String orderID;
	
	/**
	 * 范例："江苏省-南京市-栖霞区-仙林大道163号南京大学仙林校区"
	 */
	private String senderAddress;
	private String senderName;
	private String senderCompany;
	private String senderFixedPhone;
	private String senderMobilePhone;
	
	/**
	 * 地址格式同上
	 */
	private String receiverAddress;
	private String receiverName;
	private String receiverCompany;
	private String receiverFixedPhone;
	private String receiverMobilePhone;
	
	private String goodsName;
	private String goodsAmount;
	private String realWeight;
	private String size;
	
	private OrderKind orderKind;
	
	private int packageMoney;
	private int transportationCost;
	private int totalfee;
	
	private String courierid;
	private String couriername;
	
	public OrderVO(String orderID, String senderName, String senderAddress,
			String senderCompany,String senderFixedPhone, String senderMobilePhone,
			String receiverName, String receiverAddress,
			String receiverCompany, String receiverFixedPhone,
			String receiverMobilePhone, String goodsName, String goodsAmount,
			String realWeight, String size, OrderKind orderKind,
			int packageMoney, int transportationCost,String courierid,String couriername) {
		super();
		this.orderID = orderID;
		this.senderName = senderName;
		this.senderAddress = senderAddress;
		this.senderCompany = senderCompany;
		this.senderFixedPhone = senderFixedPhone;
		this.senderMobilePhone = senderMobilePhone;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.receiverCompany = receiverCompany;
		this.receiverFixedPhone = receiverFixedPhone;
		this.receiverMobilePhone = receiverMobilePhone;
		this.goodsName = goodsName;
		this.goodsAmount = goodsAmount;
		this.realWeight = realWeight;
		this.size = size;
		this.orderKind = orderKind;
		this.packageMoney = packageMoney;
		this.transportationCost = transportationCost;
		totalfee = transportationCost + packageMoney;
		this.courierid = courierid;
		this.couriername = couriername;
	}
	public String getOrderID() {
		return orderID;
	}
	public String getSenderName() {
		return senderName;
	}
	public String getSenderCompany() {
		return senderCompany;
	}
	public String getSenderFixedPhone() {
		return senderFixedPhone;
	}
	public String getSenderMobilePhone() {
		return senderMobilePhone;
	}
	public String getSenderAddress() {
		return senderAddress;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public String getReceiverCompany() {
		return receiverCompany;
	}
	public String getReceiverFixedPhone() {
		return receiverFixedPhone;
	}
	public String getReceiverMobilePhone() {
		return receiverMobilePhone;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getGoodsAmount() {
		return goodsAmount;
	}
	public String getRealWeight() {
		return realWeight;
	}
	public String getSize() {
		return size;
	}
	public OrderKind getOrderKind() {
		return orderKind;
	}
	public int getPackageMoney() {
		return packageMoney;
	}
	public int getTransportationCost() {
		return transportationCost;
	}
	public int getTotalfee() {
		return totalfee;
	}
	public String getCourierid() {
		return courierid;
	}
	public String getCouriername() {
		return couriername;
	}
}
