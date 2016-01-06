package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;
import edu.nju.logistics.vo.financial.BalanceChartVO;

/**
 * 此接口是拥有创建成本收益表的逻辑的BalanceChartList类的接口
 * @author 侍硕
 *@see BalanceChartList
 */
public interface BalanceChartListService {
	/**
	 * 从服务器取得所有收款单持久化对象
	 * @return
	 * @throws RemoteException 
	 */
	public void  findReceiptPOList () throws RemoteException;
	/**
	 * 依赖CostCalculator计算系统成本
	 * @throws RemoteException 
	 */
	public void computeSystemCost () throws RemoteException;
	/**
	 * 计算系统总支出（成本）
	 */
	public void computeCost ();
	/**
	 * 计算总收入
	 */
	public void computeIncome ();
	/**
	 * 计算总利润
	 */
	public void computeBalnce();
	
	/**
	 * 创建一个成本收益表的VO 
	 * @return
	 */
	public BalanceChartVO createNewPayment();
}
