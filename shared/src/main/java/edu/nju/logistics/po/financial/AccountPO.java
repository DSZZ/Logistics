package edu.nju.logistics.po.financial;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.vo.financial.BankAccountVO;
import edu.nju.logistics.vo.financial.CarSM;
import edu.nju.logistics.vo.financial.DriverSM;
import edu.nju.logistics.vo.financial.InstiSM;
import edu.nju.logistics.vo.financial.StorageSM;
import edu.nju.logistics.vo.financial.UserSM;

/**
 * 系统账目的持久化对象类
 * @author 侍硕
 *
 */
public class AccountPO  implements Serializable {
	  
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
       private ArrayList<InstiSM>     instiSMList;
       private ArrayList<UserSM>    userSMList;
       private ArrayList<DriverSM> driverSMList;
       private ArrayList<CarSM>       carSMList;
       private ArrayList<BankAccountVO> bankaccountVOList;
       private ArrayList<StorageSM>  storageSMList;
       
       public AccountPO(String date,ArrayList<InstiSM> instiSMList,ArrayList<UserSM>    userSMList
    		   ,ArrayList<DriverSM> driverSMList, ArrayList<CarSM> carSMList,
    		   ArrayList<BankAccountVO> bankaccountVOList ,ArrayList<StorageSM>  storageSMList ){
    	  
    	   this.date = date;
    	   this.instiSMList = instiSMList;
    	   this.userSMList = userSMList;
    	   this.driverSMList = driverSMList;
    	   this.carSMList = carSMList;
    	   this.bankaccountVOList = bankaccountVOList;
    	   this.storageSMList = storageSMList;
       }

	public ArrayList<InstiSM> getInstiSMList() {
		return instiSMList;
	}

	public void setInstiSMList(ArrayList<InstiSM> instiSMList) {
		this.instiSMList = instiSMList;
	}

	public ArrayList<UserSM> getUserSMList() {
		return userSMList;
	}

	public void setUserSMList(ArrayList<UserSM> userSMList) {
		this.userSMList = userSMList;
	}

	public ArrayList<DriverSM> getDriverSMList() {
		return driverSMList;
	}

	public void setDriverSMList(ArrayList<DriverSM> driverSMList) {
		this.driverSMList = driverSMList;
	}

	public ArrayList<CarSM> getCarSMList() {
		return carSMList;
	}

	public void setCarSMList(ArrayList<CarSM> carSMList) {
		this.carSMList = carSMList;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<BankAccountVO> getBankaccountVOList() {
		return bankaccountVOList;
	}

	public void setBankaccountVOList(ArrayList<BankAccountVO> bankaccountVOList) {
		this.bankaccountVOList = bankaccountVOList;
	}

	public ArrayList<StorageSM> getStorageSMList() {
		return storageSMList;
	}

	public void setStorageSMList(ArrayList<StorageSM> storageSMList) {
		this.storageSMList = storageSMList;
	}
}
