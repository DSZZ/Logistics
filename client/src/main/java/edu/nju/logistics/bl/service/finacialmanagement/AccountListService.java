package edu.nju.logistics.bl.service.finacialmanagement;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.vo.financial.*;
/*
 * 该类是创建新的系统账目的逻辑类AccountList的接口
 */
public interface AccountListService {
	/**
	 * 从服务器取得机构的持久化对象列表
	 * @throws RemoteException 
	 */
	public void findInstiPOList () throws RemoteException;
	/**
	 * 从服务器取得人员的持久化对象列表
	 * @throws RemoteException 
	 */
	public void findUserPOList () throws RemoteException;
	/*
	 * 从服务器取得司机的持久化对象列表
	 */
	public void findDriverPOList () throws RemoteException;
	/**
	 * 从服务器取得车辆的持久化对象列表
	 */
	public void findCarPOList ()throws RemoteException;
	/**
	 * 从服务器取得银行账户的持久化对象列表
	 * @return
	 * @throws RemoteException 
	 */
	public void findBankAccountPOList() throws RemoteException;
	/**
	 * 从服务器获取库存信息
	 * @throws RemoteException
	 */
	public void computeStorage()throws RemoteException;
   /**
 * 将机构PO转化为机构信息
 * @param instiPO  
 * @return
 */
	public InstiSM ToInstiShortMessage (InstitutionPO instiPO);
  /**
   * 将用户PO转化为用户信息列表
   * @param userPO
   * @return
   */
	public UserSM ToUserShortMessage (UserPO userPO);
	/**
	 * 将司机PO转化为司机信息
	 * @return
	 */
	public DriverSM ToDriverShortMessage(DriverPO driverPO);
	/**
	 * 将车辆PO转化为车辆信息
	 * @return
	 */
	public CarSM ToCarShortMessage (CarPO carPO);
	/**
	 * 将银行账户PO转化为VO
	 * @param baPO
	 * @return
	 */
	public BankAccountVO ToBankAccountVO (BankAccountPO baPO);
	/**
	 * 返回一个当前系统账目VO
	 * @return
	 */
	public AccountVO createNewAccount();
}
