package edu.nju.logistics.po.branchdata;

import java.io.Serializable;

public class CarPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3578442296423125682L;

	/**
	 * 车辆代号
	 */
	String id;
	
	/**
	 * 车牌号
	 */
	String plateNum;
	
	/**
	 * 开始服役时间，格式：2009/08/05
	 */
	String startWorkTime;
	
	boolean isBusy;
	
	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public String getId() {
		return id;
	}

	public String getPlateNum() {
		return plateNum;
	}

	public String getStartWorkTime() {
		return startWorkTime;
	}
	

	public CarPO(String id, String plateNum, String startWorkTime, boolean isbusy) {
		super();
		this.id = id;
		this.plateNum = plateNum;
		this.startWorkTime = startWorkTime;
		this.isBusy = isbusy;
	}


	
}
