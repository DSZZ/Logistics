package edu.nju.logistics.po;

import java.io.Serializable;

/**
 * 库存记录PO
 * @author HanzZou
 *
 */
public class StorageRecordPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StorageRecordPO(String centerID, String area, int line, int shelf, int position, String number,
			String state, int money, String time) {
		super();
		this.centerID = centerID;
		this.area = area;
		this.line = line;
		this.shelf = shelf;
		this.position = position;
		this.number = number;
		this.state = state;
		this.money = money;
		this.time = time;
	}

	/**
	 * 中转中心ID
	 */
	private String centerID;
	
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
	 * 编号
	 */
	private String number;
	
	/**
	 * 入库，调整，出库
	 */
	private String state;
	
	/**
	 * 金额
	 */
	private int money;
	
	/**
	 * 时间
	 */
	private String time;

	public String getCenterID() {
		return centerID;
	}

	public void setCenterID(String centerID) {
		this.centerID = centerID;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
