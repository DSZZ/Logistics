package edu.nju.logistics.po;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

/**
 * 中转单
 * @author HanzZou
 *
 */
public class TransferSheetPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 出发地机构ID
	 */
	private String originID;
	/**
	 * 目的地机构ID
	 */
	private String destinationID;
	/**
	 * 生成日期
	 */
	private String time;
	/**
	 * 所有订单信息
	 */
	private ArrayList<OrderVO> items;
	/**
	 * 中转单编号
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
	
	public TransferSheetPO(String originID, String destinationID, String time, ArrayList<OrderVO> items, String id,
			Transportation transportation, String transportationID, String observer, String supercargo, double fee) {
		super();
		this.originID = originID;
		this.destinationID = destinationID;
		this.time = time;
		this.items = items;
		this.id = id;
		this.transportation = transportation;
		this.transportationID = transportationID;
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

	public void setID(String id) {
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

	public void setTransportationID(String transportationID) {
		this.transportationID = transportationID;
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
