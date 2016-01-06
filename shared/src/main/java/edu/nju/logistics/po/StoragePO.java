package edu.nju.logistics.po;

import java.io.Serializable;

import edu.nju.logistics.po.StorageState;

public class StoragePO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 中转中心ID
	 */
	private String centerID;	
	/**
	 * 入库时间
	 */
	private String time;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * 排号
	 */
	private int line;
	/**
	 * 架号
	 */
	private int shelf;
	/**
	 * 位号
	 */
	private int position;
	/**
	 * 快递单号
	 */
	private String number;
	/**
	 * 目的地
	 */
	private String destination;
	/**
	 * 金额
	 */
	private int money;
	/**
	 * 入库待审批，库中，出库待审批
	 */
	private StorageState state;
	
	public StoragePO(String centerID, String time, String area, int line, int shelf, int position, String number,
			String destination, int money, StorageState state) {
		super();
		this.centerID = centerID;
		this.time = time;
		this.area = area;
		this.line = line;
		this.shelf = shelf;
		this.position = position;
		this.number = number;
		this.destination = destination;
		this.money = money;
		this.state = state;
	}

	public String getCenterID() {
		return centerID;
	}
	
	public void setCenterID(String centerID) {
		this.centerID = centerID;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public int getShelf() {
		return shelf;
	}
	
	public void setShelf(int shelf) {
		this.shelf = shelf;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}

	public StorageState getState() {
		return state;
	}

	public void setState(StorageState state) {
		this.state = state;
	}
	
	
}
