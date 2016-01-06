package edu.nju.logistics.po;

import java.io.Serializable;

/**
 * 用户的PO对象
 * 
 * @author 董轶波
 *
 */
public class UserPO implements Serializable {
	/**
	 * 用户id
	 */
	private String id;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户姓名
	 */
	private String name;
	/**
	 * 用户职位(权限)
	 */
	private String role;
	/**
	 * 用户所属机构（某中转中心，某营业厅）
	 */
	private InstitutionPO institution;
	/**
	 * 用户工资
	 */
	private double salary;
	/**
	 * 用户奖金
	 */
	private double bonus;
	/**
	 * 用户计次
	 */
	private int count;
	/**
	 * 用户当前状态
	 */
	private boolean isBusy;
	/**
	 * 上次被付款的月份
	 */
	private String month;

	public UserPO() {
	}
	
	public UserPO(String id, String password, String name, String role, InstitutionPO institution, double salary,
			double bonus, int count, boolean isBusy) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.role = role;
		this.institution = institution;
		this.salary = salary;
		this.bonus = bonus;
		this.count = count;
		this.isBusy = isBusy;
	}

	public UserPO(String id, String name, String role, InstitutionPO institution, double salary, double bonus,
			int count, boolean isBusy) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.institution = institution;
		this.salary = salary;
		this.bonus = bonus;
		this.count = count;
		this.isBusy = isBusy;
	}

	public UserPO(String id, String name, String role, InstitutionPO institution) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.institution = institution;
	}

	public UserPO(String id, boolean isBusy) {
		this.id = id;
		this.isBusy = isBusy;
	}

	public UserPO(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public UserPO(String id, String password, String role) {
		this.id = id;
		this.password = password;
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public InstitutionPO getInstitution() {
		return institution;
	}

	public void setInstitution(InstitutionPO institution) {
		this.institution = institution;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}