package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.financial.PaymentPO;
import edu.nju.logistics.vo.financial.PaymentInfo;
import edu.nju.logistics.vo.financial.PaymentVO;

/**
 * 
 * @author 侍硕
 * 财务人员可以通过生成付款单来进行成本结算
 */
public interface PaymentBLService {
     /**
      * 从服务器上读取所有付款单PO
      */
	public void  findPaymentPOList ();

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
    /**
     * 转化PO-VO
     * @param po
     * @return
     */
	public PaymentVO PaymentPOtoVO(PaymentPO po);
	/**
	 *转化VO-PO在确定创建付款单执行，将界面的信息保存并转化为PO
	 * @param vo
	 * @return
	 */
	public PaymentPO PaymentVOtoPO(PaymentVO vo);
	/**
	 * VO转化为Info
	 * @param vo
	 * @return
	 */
	public PaymentInfo PaymentVOtoInfo(PaymentVO vo);
} 
