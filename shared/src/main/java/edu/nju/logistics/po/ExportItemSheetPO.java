package edu.nju.logistics.po;

public class ExportItemSheetPO extends SheetPO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExportItemSheetPO(String id, String employeeID, String type, String date, String state, String description,
			String centerID, String area, int line, int shelf, int position, String number, int money, String destination) {
		super(id, employeeID, type, date, state, description);
		this.centerID = centerID;
		this.area = area;
		this.line = line;
		this.shelf = shelf;
		this.position = position;
		this.number = number;
		this.money = money;
		this.destination = destination;
	}

	/*
	 * 中转中心ID
	 */
	private String centerID;
	
	/*
	 * 区域
	 */
	private String area;
	
	/*
	 * 排号
	 */
	private int line;
	
	/*
	 * 架号
	 */
	private int shelf;
	
	/*
	 * 位号
	 */
	private int position;
	
	/*
	 * 编号
	 */
	private String number;
	
	/*
	 * 金额
	 */
	private int money;
	
	/*
	 * 目的地
	 */
	private String destination;

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
	
	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
