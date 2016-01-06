package edu.nju.logistics.data.service.orderdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.orderdata.*;

public interface OrderDataService extends Remote {

	public final static String UNPAY = "unpay";
	public final static String STAYLIST = "stay";
	
	public final static String branchCloud = "branchCloud";
	
	/**
	 * 新增订单
	 * @param orderPO
	 * @param institutionid 指示哪个营业厅新建的订单
	 * @throws RemoteException
	 */
	public void insert(OrderPO orderPO,String institutionid) throws RemoteException;
		
	/**
	 * 根据订单号查询订单
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public OrderPO getOrder(String id) throws RemoteException;
	
	/**
	 * 以快递员id与营业厅id为参数，获取某一个快递员的订单列表
	 * @param courier
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<OrderPO> getCourierOrderList(String courier, String institutionID) 
			throws RemoteException;
	
	/**
	 * 将订单加入快递员的待派送列表
	 * @param orderPO
	 * @param courierid
	 * @param institutionID
	 * @throws RemoteException
	 */
	public void addOrderToCourierOrderList(ArrayList<OrderPO> orderPOList, String courierid, String institutionID)
			throws RemoteException;
	
	/**
	 * 将外界的机构信息导入数据库
	 * @param institutionListPO
	 * @throws RemoteException
	 */
	public void updateInstitutionList () throws RemoteException;
	
	/**
	 * @param institutionid
	 * @return 获取营业厅的滞留列表
	 * @throws RemoteException
	 */
	public ArrayList<OrderPO> getStayOrderList(String institutionid) throws RemoteException;
	
	/**
	 * @param institutionid
	 * @return 获取营业厅的未收款列表
	 * @throws RemoteException
	 */
	public ArrayList<OrderPO> getUnpayedOrderList(String institutionid) throws RemoteException;

	/**
	 * 将某个营业厅的快递员的资料导入数据库
	 * @param courierList
	 * @param localInstitutionId
	 * @throws RemoteException
	 */
	public void updateCourierList(ArrayList<String> courieridList,String localInstitutionId)
			throws RemoteException;

	/**
	 * 将订单从某营业厅的某列表中删除
	 * @param listKind 只能为三个常量:UNPAY,STAYLIST,TODISPATCH,表示列表的种类
	 * @param institutionid 机构的id
	 * @param orderID 订单的id
	 */
	public void deleteOrderFromBranchList(String listKind,String institutionid,String orderID) throws RemoteException;
	
	/**
	 * 将订单从某快递员的待派送列表中删除
	 * @param courierid
	 * @param institutionid
	 * @param orderID
	 */
	public void deleteOrderFromCourierList(String courierid,String institutionid,String orderID) throws RemoteException;

	/**
	 * 改变某订单的状态
	 * @param orderid
	 * @param newOrderStatus
	 */
	public void changeOrderStatus(String orderid, OrderStatus newOrderStatus) throws RemoteException;
	
	/**
	 * 给某订单增加历史轨迹
	 * @param orderid
	 * @param newHistoryTrace
	 */
	public void addOrderHistoryTrace(String orderid, String newHistoryTrace) throws RemoteException;

	/**
	 * 将快件分配给营业员
	 * @param selectedOrderList
	 * @param courierid
	 * @param localInstitutionId
	 * @throws RemoteException
	 */
	public void distributeOrderToCourier(ArrayList<String> selectedOrderList,
			String courierid, String localInstitutionId) throws RemoteException;
	
	/**
	 * 将某订单移动到其他营业厅或branchCloud去
	 * originid和destinationid，既可以是营业厅8位，也可以是中转中心6位
	 * @param orderid
	 * @param destinationid
	 * @throws RemoteException
	 */
	public void moveTo(String orderid, String destinationid) throws RemoteException;
}
