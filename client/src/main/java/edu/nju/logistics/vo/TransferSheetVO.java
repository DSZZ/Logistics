package edu.nju.logistics.vo;

import java.util.ArrayList;

import edu.nju.logistics.vo.ordermanagement.OrderVO;

/**
 * 中转单
 * @author HanzZou
 *
 */
public class TransferSheetVO {
	/**
	 * 始发地ID
	 */
	private String originID;
	/**
	 * 目的地ID
	 */
	private String destinationID;
	/**
	 * 生成日期 2009/06/02
	 */
	private String time;
	/**
	 * 所有订单号
	 */
	private ArrayList<OrderVO> items;
	/**
	 * 中转单号
	 */
	private String id;
	/**
	 * 交通方式
	 */
	private Transportation transportation;
	/**
	 * 交通代号
	 */
	private String transportationID;
	/**
	 * 监装员
	 */
	private String observer;
	/**
	 * 押运员
	 */
	private String supercargo;
	/**
	 * 运费
	 */
	private double fee;
	
	public TransferSheetVO(String originID, String destinationID, String time, ArrayList<OrderVO> items, String id,
			Transportation transportation, String transporationID, String observer, String supercargo, double fee) {
		super();
		this.originID = originID;
		this.destinationID = destinationID;
		this.time = time;
		this.items = items;
		this.id = id;
		this.transportation = transportation;
		this.transportationID = transporationID;
		this.observer = observer;
		this.supercargo = supercargo;
		this.fee = fee;
	}

	public String getOriginID() {
		return originID;
	}
	
	public void setOriginID(String originID) {
		this.originID = originID;
	}
	
	public String getDestinationID() {
		return destinationID;
	}
	
	public void setDestinationID(String destinationID) {
		this.destinationID = destinationID;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public ArrayList<OrderVO> getItems() {
		return items;
	}
	
	public void setItems(ArrayList<OrderVO> items) {
		this.items = items;
	}
	
	public String getID() {
		return id;
	}
	
	public void getID(String id) {
		this.id = id;
	}
	
	public Transportation getTransportation() {
		return transportation;
	}
	
	public void setTransportation(Transportation transportation) {
		this.transportation = transportation;
	}
	
	public String getTransportationID() {
		return transportationID;
	}

	public void setTransportationNumber(String transportationNumber) {
		this.transportationID = transportationNumber;
	}

	public String getObserver() {
		return observer;
	}
	
	public void setObserver(String observer) {
		this.observer = observer;
	}
	
	public String getSupercargo() {
		return supercargo;
	}

	public void setSupercargo(String supercargo) {
		this.supercargo = supercargo;
	}

	public double getFee() {
		return fee;
	}
	
	public void setFee(double fee) {
		this.fee = fee;
	}
	
}
