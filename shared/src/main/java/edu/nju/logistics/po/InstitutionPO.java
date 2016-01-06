package edu.nju.logistics.po;

import java.io.Serializable;

/**
 * 机构的PO
 * @author 董轶波
 *
 */
public class InstitutionPO implements Serializable{
	/**
	 * 机构所属地
	 */
	private String location;
	/**
	 * 机构id
	 */
	private String id;
	/**
	 * 种类（中转中心或营业厅）
	 */
	private String type;
	/**
	 * 租金
	 */
	private double rental;
	/**
	 * 上次付款的年份
	 */
	private String year;
	
	public InstitutionPO(String location, String id, String type,double rental) {
		this.location = location;
		this.id = id;
		this.type = type;
		this.rental=rental;
	}


	public InstitutionPO(String location, String id, String type) {
		this.location = location;
		this.id = id;
		this.type = type;
	}
	public InstitutionPO(){
		
	}

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public double getRental() {
		return rental;
	}


	public void setRental(double rental) {
		this.rental = rental;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	@Override
	public String toString() {
		return location+" "+id+" "+type;
	}
   
	
}