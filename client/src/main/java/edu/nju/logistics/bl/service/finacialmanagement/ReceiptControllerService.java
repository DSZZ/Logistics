package edu.nju.logistics.bl.service.finacialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;

/**
 * 该接口是作为ReceiptController的服务接口创建的,区别于ReceiptBLService
 * 值提供了界面所需要的接口.
 * @author 侍硕
 *
 */
public interface ReceiptControllerService {
	/**
	 * 返回所有收款单的信息列表用于表格展示
	 * @return
	 */
	  public ArrayList<ReceiptInfo> getInfoList();
	  /**
	   * 根据特定日期返回收款单信息
	   * @param date
	   * @return
	   */
	  public ArrayList<ReceiptInfo> getInfoListByDate(String date);
	  /**
	   * 根据特定营业厅编号返回收款单信息
	   * @param date
	   * @return
	   */
	  public ArrayList<ReceiptInfo> getInfoListByInstiID(String ID) ;
	  /**
	   * 根据特定日期和特定营业厅编号返回收款单信息
	   * @param date
	   * @param ID
	   * @return
	   */
	  public ArrayList<ReceiptInfo> getInfoListByBoth(String date,String ID) ;
	  
	/**
	 * 根据用户选中的某个收款单的ID来显示相应的首款单的信息
	 * @param ID
	 * @return
	 */
	public CourierIncomeReceiptVO getReceiptVO (String ID);
	/**
	 * 根据用户选中的所有收款单的ID来生成一个ID的链表，等待导出
	 * @param IDs
	 */
	public void outputReceipts(ArrayList<String> IDs);
}
