package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.financial.AccountInfo;
import edu.nju.logistics.vo.financial.AccountVO;

/**
 * 期初建账的逻辑接口
 * @author 侍硕
 *
 */
public interface AccountInitBLService {
	/**
	 * 持久化新的系统账目对象
	 * @throws RemoteException 
	 */
	public void saveAccount (AccountVO accountVO) throws RemoteException; 
	/*
	 * 显示某个历史系统账目的信息
	 */
	public AccountVO showOldAccount (String date); 
	/**
	 * 显示当期系统账目的信息
	 * @return
	 */
	public AccountVO showNewAccount (); 
	/**
	 * 返回历史系统账目列表
	 * @return
	 */
	public ArrayList<String> showDateList();
	
	
}
