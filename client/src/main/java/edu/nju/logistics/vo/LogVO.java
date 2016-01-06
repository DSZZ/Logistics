package edu.nju.logistics.vo;

/**
 * 日志VO对象
 * @author 董轶波
 *
 */
public class LogVO{
   /**
    * 该日志的生成时间
    */
	private String date;
	/**
	 * 员工的ID
	 */
	private String employeeID;
	/**
	 *该日志的内容 
	 */
	private String content;
	
	public LogVO(String date, String employeeID, String content) {
		this.date = date;
		this.employeeID=employeeID;
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public String getContent() {
		return content;
	}

	public String getEmployeeID() {
		return employeeID;
	}
	
	
	
}
