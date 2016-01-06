package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.financial.PaymentInfo;
import edu.nju.logistics.vo.financial.PaymentVO;

public interface PaymentControllerService {
	/**
	 * 返回全部付款单的VO
	 * @return
	 */
	public ArrayList<PaymentVO> getPaymentVOList ();
	/**
	 * 返回全部付款单的信息类
	 * @return
	 */
	public ArrayList<PaymentInfo> getPaymentInfoList();
	/**
	 * 根据ID返回付款单VO并返回
	 * @param ID
	 * @return
	 */
	public PaymentVO showOldChart(String ID);
	/**
	 * 新创一个付款单VO并返回
	 * @param ID
	 * @return
	 * @throws RemoteException 
	 */
	public PaymentVO showNewChart() throws RemoteException;
	/**
	 * 持久化一个付款单并送至总经理审批
	 * @return
	 */
	public void createPayment (PaymentVO paymentVO);
} 
