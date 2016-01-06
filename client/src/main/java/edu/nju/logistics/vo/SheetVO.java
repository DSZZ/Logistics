package edu.nju.logistics.vo;


/**
 * 单据的VO对象
 * @author 董轶波
 *
 */
public class SheetVO{
	/**
	 * 该单据的ID
	 */
	private String id;
	/**
	 * 该单据的员工ID
	 */
	private String employeeId;
	/**
	 * 该单据的类别
	 */
	private String type;
	/**
	 * 该单据的生成的时间
	 */
	private String date;
	/**
	 * 该单据的状态（提交，已审批）
	 */
	private String state;
	/**
	 *该单据的内容描述
	 */
	private String description;
	
	public SheetVO(String id, String employeeID, String type, String date, String state, String description) {
		this.id = id;
		this.employeeId=employeeID;
		this.type = type;
		this.date = date;
		this.state = state;
		this.description=description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getDate() {
		return date;
	}

	public String getState() {
		return state;
	}

	public String getDescription() {
		return description;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	
	
	
}
