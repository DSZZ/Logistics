package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 期初建账中使用到的
 * @author   侍硕
 *PS：SM代表的是ShortMessage
 */
public class CarSM  implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//车辆代号
	private String carID;
	 //车牌号
	private String plateNum;	
	  //开始服役时间，格式：2009/08/05
	private String startWorkTime;
	public CarSM(String carID, String plateNum , String startWorkTime){
		  this.setCarID(carID);
		  this.setPlateNum(plateNum);
		  this.setStartWorkTime(startWorkTime);
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	
	
}
