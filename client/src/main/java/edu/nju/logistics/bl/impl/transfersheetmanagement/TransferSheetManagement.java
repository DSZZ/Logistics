package edu.nju.logistics.bl.impl.transfersheetmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.impl.financialmanagement.FreightRecordBL;
import edu.nju.logistics.bl.impl.log.LogBL;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.CenterServiceProvider;
import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.GetOrderPOImpl;
import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.finacialmanagement.FreightRecordBLService;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.ordermanagement.GetOrderPO;
import edu.nju.logistics.bl.service.ordermanagement.OrderManagementBLCenterService;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLCenterService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.transfersheetdata.TransferSheetDataService;
import edu.nju.logistics.po.ReceiveItemSheetPO;
import edu.nju.logistics.po.SendItemSheetPO;
import edu.nju.logistics.po.TransferSheetPO;
import edu.nju.logistics.po.financial.FreightRecordPO;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.po.orderdata.OrderStatus;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.LogVO;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class TransferSheetManagement {

	private TransferSheetDataService transferSheetData = null;

	private LogBL logBL = null;

	private OrderManagementBLCenterService orderManagement = null;

	private ExamineBL examine = null;

	private EnvironmentGetter getter = null;

	private StorehouseManagementBLCenterService storehouseManagement = null;

	private GetOrderPO getOrder = null;

	private FreightRecordBLService freight = null;

	public TransferSheetManagement() throws RemoteException {
		logBL = new LogBL();
		examine = new ExamineBL();
		getter = new OperationManagementController();
		storehouseManagement = new StorehouseManagementController();
		getOrder = new GetOrderPOImpl();
		orderManagement = new CenterServiceProvider();
		freight = new FreightRecordBL();

		try {
			transferSheetData = (TransferSheetDataService) Naming.lookup("rmi://"+ RMI.getIP() + ":2014/TransferSheetData");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public void sendItems(TransferSheetVO vo, String staffID) throws RemoteException {
		examine.insertSheet(new SendItemSheetPO(examine.getSheetID(), staffID, "发货单", TimeUtil.getCurrentDate(), "提交",
				"发送编号为" + vo.getID() + "的中转单货物。 ",
				new TransferSheetPO(vo.getOriginID(), vo.getDestinationID(), vo.getTime(), vo.getItems(), vo.getID(),
						vo.getTransportation(), vo.getTransportationID(), vo.getObserver(), vo.getSupercargo(),
						vo.getFee())));
		System.out.println(123442);
		System.out.println(vo.getDestinationID());
	}

	public void executeReceiveItem(ReceiveItemSheetPO po) throws RemoteException {
		TransferSheetPO sheet = po.getPO();
		System.out.println(sheet.getDestinationID());
		LogVO vo = new LogVO(TimeUtil.getCurrentTime(),
				po.getEmployeeId(),
				"接收中转单号为" + sheet.getID() + "的中转货物");
		logBL.insert(vo);
		OrderStatus os = null;
		if (sheet.getDestinationID().length() == 8) {
			os = OrderStatus.receiverBranch;
		} else if (sheet.getDestinationID().length() == 6 && sheet.getOriginID().length() == 8) {
			os = OrderStatus.senderCenter;
		} else {
			os = OrderStatus.receiverCenter;
		}
		for (OrderVO order : sheet.getItems()) {
			orderManagement.changeOrderStatus(order.getOrderID(), os);
			orderManagement.addOrderHistoryTrace(order.getOrderID(),
					TimeUtil.getCurrentTime() + "-" + "已到达" + (sheet.getDestinationID().length() == 8 ? getter.getBranchName(sheet.getDestinationID())
							: getter.getCenterName(sheet.getDestinationID()))
							+ (sheet.getDestinationID().length() == 6 ? "中转中心" : "营业厅"));
			orderManagement.receiveSuccess(order.getOrderID(), sheet.getOriginID(), sheet.getDestinationID());
			if (sheet.getDestinationID().length() == 6) {
				storehouseManagement.setToImport(new BufferVO(sheet.getDestinationID(), order.getOrderID(),
						order.getReceiverAddress(), "import"));
			}
		}
		transferSheetData.delete(sheet.getID());
	}

	public void receiveItems(String id, String staffID, ReceiptState s) throws RemoteException {
		TransferSheetPO transferSheet = this.getTransferSheetPOByID(id);
		examine.insertSheet(new ReceiveItemSheetPO(examine.getSheetID(), staffID, "收货单", TimeUtil.getCurrentDate(),
				"提交", "接收编号为" + id + "的中转单货物，状态为" + s.toString() + "。",
				transferSheet));
	}

	public void executeSendItem(SendItemSheetPO po) throws RemoteException {
		TransferSheetPO sheet = po.getPo();
		logBL.insert(new LogVO(TimeUtil.getCurrentTime(), po.getEmployeeId(), "发送中转单号为" + sheet.getID() + "的中转货物"));
		transferSheetData.insert(sheet);
		for (OrderVO order : sheet.getItems()) {
			orderManagement.addOrderHistoryTrace(order.getOrderID(),
					TimeUtil.getCurrentTime() + "-" + "已从" + getter.getCenterName(sheet.getOriginID())
							+ (sheet.getOriginID().length() == 6 ? "中转中心" : "营业厅") + "发往"
							+ getter.getBranchName(sheet.getDestinationID())
							+ (sheet.getDestinationID().length() == 6 ? "中转中心" : "营业厅"));
			orderManagement.loadSuccess(order.getOrderID(), sheet.getOriginID(), sheet.getDestinationID());
			if (sheet.getOriginID().length() == 6) {
				storehouseManagement.deleteBuffer(order.getOrderID());
			}
		}
		freight.addFreightRecordPO(new FreightRecordPO(sheet.getOriginID(), sheet.getFee(), TimeUtil.getCurrentDate()));
	}

	public ArrayList<TransferSheetVO> getTransferSheetList(String centerID) throws RemoteException {
		ArrayList<TransferSheetVO> vo_list = new ArrayList<TransferSheetVO>();
		ArrayList<TransferSheetPO> po_list = transferSheetData.find(centerID);
		for (TransferSheetPO po : po_list) {
			TransferSheetVO vo = new TransferSheetVO(po.getOriginID(), po.getDestinationID(), po.getTime(),
					po.getItems(), po.getID(), po.getTransportation(), po.getTransportationID(), po.getObserver(),
					po.getSupercargo(), po.getFee());
			vo_list.add(vo);
		}
		return vo_list;
	}

	public String getID() throws RemoteException {
		String id = transferSheetData.getTransferSheetID();
		return id;
	}

	public double calculateFee(ArrayList<OrderVO> voList, Transportation tran, String centerID1, String centerID2)
			throws RemoteException {
		if (centerID2.length() == 8) {
			return 0.0;
		}
		double weight = 0.0;
		double price = 0.0;
		for (OrderVO vo : voList) {
			weight += Double.parseDouble(vo.getRealWeight()) / 1000;
		}
		price = weight * getter.getDistance(centerID1, centerID2) * getter.getServicePrice(tran.toString());
		return price;
	}

	public String[] getDestinations(String centerID) throws RemoteException {
		ArrayList<String> temp = getter.getListToShow(centerID);
		String[] destinations = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			destinations[i] = temp.get(i);
		}
		return destinations;
	}

	public String getInstitutionByUser(String id) throws RemoteException {
		return getter.getUserInstitutionID(id);
	}

	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException {
		return storehouseManagement.getToSend(centerID);
	}

	public OrderVO getOrderVO(String id) throws RemoteException {
		OrderPO po = getOrder.getOrderPO(id);
		return po.getOrderVO();
	}

	public String getInstituionIDByName(String name) throws RemoteException {
		String[] temp = name.split(" ");
		String actualName = temp[0].equals("下属") ? temp[1].substring(0, temp[1].length() - 3)
				: temp[1].substring(0, temp[1].length() - 4);
		return getter.getInstitutionID(actualName);
	}

	public String getInstituionNameByID(String id) throws RemoteException {
		return id.length() == 6 ? getter.getCenterName(id) : getter.getBranchName(id);
	}

	private TransferSheetPO getTransferSheetPOByID(String id) throws RemoteException {
		TransferSheetPO po = transferSheetData.getTransferSheetByID(id);
		return po;
	}
	
	public ArrayList<OrderVO> getOrderListInTransferSheet(String id) throws RemoteException {
		TransferSheetPO po = this.getTransferSheetPOByID(id);
		return po.getItems();
	}

	public static void main(String[] args) {
		TransferSheetManagement t = null;
		try {
			t = new TransferSheetManagement();

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			System.out.println(t.getOrderVO("0001115555").getOrderID());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
