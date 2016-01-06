package edu.nju.logistics.bl.impl.ordermanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.impl.financialmanagement.CourierIncomeReceiptBL;
import edu.nju.logistics.bl.impl.log.LogBL;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.finacialmanagement.CourierIncomeReceiptBLService;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.operationmanagement.InsertLogService;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.orderdata.OrderDataService;
import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;
import edu.nju.logistics.po.courierIncomeReceipt.ShortOrderPO;
import edu.nju.logistics.po.orderdata.DispatchOrderSheetPO;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.po.orderdata.OrderStatus;
import edu.nju.logistics.po.orderdata.SendOrderSheetPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.LogVO;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class OrderManagementImpl implements OrderManagementService{

	private OrderDataService orderDataManager;
	private EnvironmentGetter environmentGetter;
	private CourierIncomeReceiptBLService receiptCreater;
	private ExamineBL examineBL;
	private InsertLogService logInserter;

	/**
	 * 本营业厅的ID，在对象初始化时自动从本地获取
	 */
	private String localInstitutionId;
	
	public OrderManagementImpl(UserVO operator) throws RemoteException {
		environmentGetter = new OperationManagementController();
		localInstitutionId = environmentGetter.getUserInstitutionID(operator.getId());
		examineBL = new ExamineBL();
		logInserter = new LogBL();
		
		try {
			receiptCreater = new CourierIncomeReceiptBL();
			orderDataManager = (OrderDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/OrderData");


		} catch (MalformedURLException | NotBoundException e) {
			e.printStackTrace();
		}

		ArrayList<UserVO> courierList = environmentGetter.getCourierList(localInstitutionId);
		ArrayList<String> courieridList = new ArrayList<>();
		for(UserVO courier : courierList){
			courieridList.add(courier.getId());
		}
		
		orderDataManager.updateCourierList(courieridList,localInstitutionId);
	}
	@Override
	public void createOrder(OrderVO orderVO, String courierid) throws RemoteException {
		
		String description = "快递员" + orderVO.getCouriername()
				+ "创建了寄件单，运费为" + orderVO.getTotalfee()
				+ "，发往" + orderVO.getReceiverAddress() + "。";
		
		SendOrderSheetPO sheet = new SendOrderSheetPO(examineBL.getSheetID(),courierid
				,"寄件单",TimeUtil.getCurrentDate(),"提交",description,orderVO,localInstitutionId,
				environmentGetter.getBranchName(localInstitutionId));
		examineBL.insertSheet(sheet);
		logInserter.insert(new LogVO(TimeUtil.getCurrentTime(), courierid, description));
	}
	@Override
	public void dispatchOrder(ArrayList<OrderVO> selectedOrderList,
			String courierid) throws RemoteException {
		String description = "快递员" + courierid
				+ "创建了派件件单，总件数为" + selectedOrderList.size();
		
		DispatchOrderSheetPO sheet = new DispatchOrderSheetPO(examineBL.getSheetID(),courierid
				,"派件单",TimeUtil.getCurrentDate(),"提交",description,selectedOrderList,localInstitutionId,
				courierid);
		examineBL.insertSheet(sheet);
		logInserter.insert(new LogVO(TimeUtil.getCurrentTime(), courierid, description));
	}

	@Override
	public ArrayList<OrderVO> getUnpayedOrderList() throws RemoteException {
		ArrayList<OrderPO> orderPOList = null;
		orderPOList = orderDataManager.getUnpayedOrderList(localInstitutionId);
		
		ArrayList<OrderVO> orderVOList = convertPOListToVOList(orderPOList);
		return orderVOList;
	}
	private ArrayList<OrderVO> convertPOListToVOList(ArrayList<OrderPO> orderPOList) {
		
		if(orderPOList == null)
			return new ArrayList<>();
		
		ArrayList<OrderVO> orderVOList = new ArrayList<>();
		
		for(OrderPO po : orderPOList){
			orderVOList.add(po.getOrderVO());
		}
		
		return orderVOList;
	}
	@Override
	public void payOrder(ArrayList<OrderVO> payOrderList) throws RemoteException {
		ArrayList<ShortOrderPO> shortOrderList = new ArrayList<>();
		for(OrderVO order : payOrderList){
				orderDataManager.deleteOrderFromBranchList(OrderDataService.UNPAY, localInstitutionId, order.getOrderID());
				shortOrderList.add(new ShortOrderPO(order.getOrderID(),
						order.getTransportationCost(), order.getCourierid(), order.getCouriername()));

		}
		receiptCreater.addCourierIncomeReceiptPO(new CourierIncomeReceiptPO
				(shortOrderList, TimeUtil.getCurrentDate(), localInstitutionId));
	}
	@Override
	public ArrayList<OrderVO> getCourierUndispatchedList(String courierid) throws RemoteException {
		ArrayList<OrderPO> orderPOList = null;
		orderPOList = orderDataManager.getCourierOrderList(courierid, localInstitutionId);
		
		ArrayList<OrderVO> orderVOList = convertPOListToVOList(orderPOList);
		return orderVOList;
	}
	@Override
	public ArrayList<OrderVO> getStayOrderList() throws RemoteException {
		ArrayList<OrderPO> orderPOList = null;
		orderPOList = orderDataManager.getStayOrderList(localInstitutionId);
		
		ArrayList<OrderVO> orderVOList = convertPOListToVOList(orderPOList);
		return orderVOList;
	}
	
	@Override
	public void distributeOrderToCourier(ArrayList<OrderVO> selectedOrderList,
			String courierid) throws RemoteException {
		ArrayList<String> selectedOrderIDList = new ArrayList<>();
		for(OrderVO order : selectedOrderList){
			String id = order.getOrderID();
			
			selectedOrderIDList.add(id);
			
			orderDataManager.addOrderHistoryTrace(id, TimeUtil.getCurrentTime() + "-" + "快递员（编号为：" + courierid +"）正在派件！");
			orderDataManager.changeOrderStatus(id, OrderStatus.dispatching);
		}
		orderDataManager.distributeOrderToCourier(selectedOrderIDList,courierid,localInstitutionId);
		
		String description = "将数量为" +selectedOrderList.size()+"的订单分配给了编号为" + courierid + "的快递员";
		
		logInserter.insert(new LogVO(TimeUtil.getCurrentTime(), courierid, description));
	}

}
