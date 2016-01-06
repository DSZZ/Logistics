package edu.nju.logistics.vo.financial;

import java.io.Serializable;

/**
 * 
 * @author 侍硕  
 * 银行账户的相关操作只能由高级财务人员完成
 *
 */
public class BankAccountVO  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 账户名称，格式为：10位英文和数字，英文开头
	 */
	private String name ;
	/**
	 * 账户的余额，余额在账户创建后不可随意修改，经收入和支出计算可得。
	 */
	private double balance;
	
	public BankAccountVO(String name , double balance){
		this.name = name;
		this.balance = balance;
	}
	
	public String getName (){
		return name;
	}
	
	public double getBalance (){
		return balance;
	}
	
	public void setName (String name){
		 this.name=name;
	}
	
	public void setBalance(double balance){
		this.balance=balance;
	}
	
}
