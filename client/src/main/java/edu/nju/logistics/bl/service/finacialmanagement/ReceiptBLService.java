package edu.nju.logistics.bl.service.finacialmanagement;

import java.util.ArrayList;

import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;
import edu.nju.logistics.po.courierIncomeReceipt.ShortOrderPO;
import edu.nju.logistics.vo.courierIncomeReceipt.CourierIncomeReceiptVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.courierIncomeReceipt.ShortOrderVO;

/**
 * 
 * 该接口的创建是为了给财务人员查看收款单提供服务
 * 注意：区别于CourierIncomeReceiptBL
 * @author ShiShuo
 * 财务人员可以查看现有的所有收款单，也可以请求导出
 *
 */
public interface ReceiptBLService {
	/**
	 * 从服务器取得全部收款单PO 的列表
	 * @return
	 */
	public void findReceiptList();
	
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
	  public ArrayList<ReceiptInfo> getInfoListByBoth(String date, String ID) ;
	  
	/**
	 * 根据用户选中的某个收款单的ID来显示相应的首款单的信息
	 * @param ID
	 * @return
	 */
	public CourierIncomeReceiptVO getReceiptVO (String ID);
	/**
	 * 把一个收款单VO对象转化为Info对象
	 * @param vo
	 */
	public  ReceiptInfo  ReceiptVOToInfo(CourierIncomeReceiptVO vo);
	/**
	 * 将一个收款单的持久化对象变为值对象
	 * @param po
	 */
	public CourierIncomeReceiptVO  ReceiptPOToVO(CourierIncomeReceiptPO po);
	
	/**
	 * 把一个ShortOrder的持久化对象转化为值对象
	 * @param po
	 */
	public ShortOrderVO ShortOrderPOToVO(ShortOrderPO po);
	/**
	 * 根据用户选中的所有收款单的ID来生成一个ID的链表，等待导出
	 * @param IDs
	 */
	public void outputReceipts(ArrayList<String> IDs);
}
