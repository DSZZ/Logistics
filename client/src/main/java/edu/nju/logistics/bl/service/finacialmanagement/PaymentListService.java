package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.vo.financial.PaymentVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public interface PaymentListService {
	/**
	 * 创建一个系统成本对象的VO
	 * @see   CostCalculator
	 * @return
	 * @throws RemoteException 
	 */
	public SystemCostVO computeSystemCost () throws RemoteException;
	/**
	 * 新创一个付款单对象
	 * @return
	 * @throws RemoteException 
	 */
	public PaymentVO createNewPayment() throws RemoteException;
}
