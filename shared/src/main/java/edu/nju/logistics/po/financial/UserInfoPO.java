package edu.nju.logistics.po.financial;
/**
 * 计算系统成本时使用的人员信息项
 * 包括员工ID、工资、奖励
 * @author 侍硕
 *
 */
public class UserInfoPO {
	private String UserID;
	/**
	 * 员工所在机构ID
	 */
	private String InstiID;
	private String name;
	private String role;
    private double salary;
    private double reward;
    public UserInfoPO (String UserID,String instiID,String name,String role,double salary , double reward){
    	 this.UserID=UserID;
    	 this.setInstiID(instiID);
    	 this.setName(name);
    	 this.setRole(role);
    	 this.salary=salary;
    	 this.reward=reward;
    }
    
    public String getUserID(){
    	return this.UserID;
    }
    
    public double getSalary(){
    	return this.salary;
    }
    
    public double  getReward(){
    	return this.reward;
    }
    
    public void setUserID(String UserID){
    	  this.UserID = UserID;
    }
    
    public void setSalary(double salary){
    	this.salary = salary;
    }
    
    public void setReward(double Reward){
    	this.reward=Reward;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstiID() {
		return InstiID;
	}

	public void setInstiID(String instiID) {
		InstiID = instiID;
	}
}
