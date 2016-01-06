package edu.nju.logistics.bl.service.finacialmanagement;
import java.util.ArrayList;

import edu.nju.logistics.vo.financial.PaymentInfo;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;

/**
 * 经营情况表的逻辑接口
 * @author 侍硕
 * 
 */
public interface OperationStatusBLService {
	/**
	 *根据日期显示期间内的所有付款单信息
	 * @param date1开始日期
	 * @param date2结束日期
	 * @return
	 */
	public ArrayList<PaymentInfo> showPaymentList (String date1,String date2); 
	/**
	 *根据日期显示期间内的所有收款单信息
	 * @param date1开始日期
	 * @param date2结束日期
	 * @return
	 */
	public ArrayList<ReceiptInfo> showReceiptList (String date1,String date2); 
	
	/**
	 * 导出当前界面的经营情况表
	 */
	
	public void outputChart(); 
	
}
