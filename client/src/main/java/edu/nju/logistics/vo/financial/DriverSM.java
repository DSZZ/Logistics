package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 期初建账中使用到的
 * @author   侍硕
 *PS：SM代表的是ShortMessage
 */
public class DriverSM  implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 司机编号
	private String driverID;
	 // 姓名
	private String name;
	//机构编号
	private String institutionID;
	
	public DriverSM(String driverID,String name , String instiID){
		  this.setDriverID(driverID);
		  this.setName(name);
		  this.setInstitutionID(instiID);
	}

	public String getDriverID() {
		return driverID;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstitutionID() {
		return institutionID;
	}

	public void setInstitutionID(String institutionID) {
		this.institutionID = institutionID;
	}
	
	

}
