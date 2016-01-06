package edu.nju.logistics.po.branchdata;

import java.io.Serializable;

public class DriverPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8932818211572087596L;

	/**
	 * 司机编号
	 */
	private String id;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 出生日期
	 */
	private String birthday;
	
	/**
	 * 身份证号
	 */
	private String identityNum;
	
	private String institutionid;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 性别
	 */
	boolean isMale;
	
	boolean isBusy;

	/**
	 * 行驶证到期时间
	 */
	private String licenseEndTime;
	
	
	/**
	 * 用于记录上次付款的日期，由财务人员设置，初始化为00
	 */
	private String month="00";
	
	/**
	 * 计次，每个月清空，每开一次车加一
	 */
	private int count = 0;

	public DriverPO(String id, String name, String birthday,
			String identityNum, String phone, boolean isMale,
			String licenseEndTime, boolean isbusy) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.identityNum = identityNum;
		this.phone = phone;
		this.isMale = isMale;
		this.licenseEndTime = licenseEndTime;
		this.isBusy = isbusy;
		month = "00";
		count = 0;
	}
	
	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	
	public String getId() {
		return id;
	}
	
	public String getInstitutionid() {
		return institutionid;
	}

	public void setInstitutionid(String institutionid) {
		this.institutionid = institutionid;
	}

	public String getName() {
		return name;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getIdentityNum() {
		return identityNum;
	}

	public String getPhone() {
		return phone;
	}

	public boolean isMale() {
		return isMale;
	}

	public String getLicenseEndTime() {
		return licenseEndTime;
	}

	public String getMonth() {
		return month;
	}

	public int getCount() {
		return count;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void addCount() {
		this.count ++;
	}
	
	
	
}
