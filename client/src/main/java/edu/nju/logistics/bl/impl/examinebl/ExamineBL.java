package edu.nju.logistics.bl.impl.examinebl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.financialmanagement.PaymentExecutionController;
import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.DispatcherAndCreaterImpl;
import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.impl.transfersheetmanagement.TransferSheetManagementController;
import edu.nju.logistics.bl.service.finacialmanagement.PaymentExecutionService;
import edu.nju.logistics.bl.service.ordermanagement.DispatcherAndCreater;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementExecutionService;
import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementExecutionService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.bufferdata.BufferDataService;
import edu.nju.logistics.po.ExportItemSheetPO;
import edu.nju.logistics.po.ImportItemSheetPO;
import edu.nju.logistics.po.ReceiveItemSheetPO;
import edu.nju.logistics.po.SendItemSheetPO;
import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.po.financial.PaymentPO;
import edu.nju.logistics.po.orderdata.DispatchOrderSheetPO;
import edu.nju.logistics.po.orderdata.SendOrderSheetPO;
import edu.nju.logistics.ui.util.TimeUtil;


/**
 * 单据方法封装
 * 
 * @author 董轶波
 *
 */
public class ExamineBL {
	/**
	 * 单据数据
	 */
	private BufferDataService bufferDataService = null;

	private ArrayList<SheetPO> po = null;

	public ExamineBL() throws RemoteException{
				try {
					this.bufferDataService = (BufferDataService) Naming.lookup("rmi://"+RMI.getIP()+":2014/BufferData");
				} catch (MalformedURLException | NotBoundException e) {
					e.printStackTrace();
				}
	}

	/**
	 * 生成单据的ID
	 * 
	 * @return
	 * @throws RemoteException 
	 */
	public String getSheetID() throws RemoteException {
		int IDs[];
		String newID = "";
			this.po = this.bufferDataService.getAll();

			IDs = new int[this.po.size()];
			int index = 0;
			for (int i = 0; i < this.po.size(); i++) {
				SheetPO temp = this.po.get(i);
				IDs[i] = Integer.parseInt(temp.getId());
				// System.out.println(IDs[i]);
			}

			for (int i = 1; i < IDs.length; i++) {
				if (IDs[i] > IDs[index]) {
					index = i;
				}
			}

			if (IDs.length == 0) {
				newID = "0";
			} else {
				newID = IDs[index] + 1 + "";
			}
			int length = newID.length();
			for (int i = 0; i < 8 - length; i++) {
				newID = "0" + newID;
			}
		return newID;
	}

	/**
	 * 获得指定类型单据
	 * 
	 * @param type
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<SheetPO> getSheet(String type) throws RemoteException {
		ArrayList<SheetPO> temp = new ArrayList<SheetPO>();
			this.po = this.bufferDataService.getAll();
			
//			//应对文件为空的状态
//			if(this.po==null){
//				this.po=new ArrayList<SheetPO>();
//			}
//			
//			for (int i = 0; i < this.po.size(); i++) {
//				SheetPO sheet = this.po.get(i);
//				if (sheet.getType().equals(type)) {
//					temp.add(sheet);
//				}
//			}
			
			for (int i = this.po.size()-1; i >=0; i--) {
				SheetPO sheet = this.po.get(i);
				if (sheet.getType().equals(type)) {
					temp.add(sheet);
				}
			
			}
			
		return temp;
	}

	/**
	 * 通过某项单据并记录于文本
	 * 
	 * @param po
	 * @throws RemoteException 
	 */
	public void setApproval(SheetPO sheetPO) throws RemoteException {
			this.po = this.bufferDataService.getAll();
			for (int i = 0; i < this.po.size(); i++) {
				SheetPO temp = this.po.get(i);
				if (temp.getId().equals(sheetPO.getId())) {
					temp.setState("已审批");
				}
			}
			// 写入文本
			this.bufferDataService.writeAll(this.po);

			this.executeApproval(sheetPO);

	}

	/**
	 * 批量通过所有单据并记录于文本
	 * 
	 * @param po
	 * @throws RemoteException 
	 */
	public void setMassApproval(String type) throws RemoteException {
		ArrayList<SheetPO> noApproval = new ArrayList<SheetPO>();
			this.po = this.bufferDataService.getAll();
			for (int i = 0; i < this.po.size(); i++) {
				SheetPO temp = this.po.get(i);
				if ((temp.getType().equals(type)) && (temp.getState().equals("提交"))) {
					temp.setState("已审批");
					noApproval.add(temp);
				}
			}
			this.bufferDataService.writeAll(this.po);

			//  调用单据通过后的方法 noApproval
			for (int i = 0; i < noApproval.size(); i++) {
				SheetPO temp = noApproval.get(i);
				this.executeApproval(temp);
			}

	}

	/**
	 * 设置审批不通过
	 * 
	 * @param temp
	 * @throws RemoteException 
	 */
	public void setNoApproval(String id) throws RemoteException {
			this.po = this.bufferDataService.getAll();
			for (int i = 0; i < this.po.size(); i++) {
				SheetPO temp = this.po.get(i);
				if (temp.getId().equals(id)) {
					temp.setState("未通过");
				}
			}
			this.bufferDataService.writeAll(this.po);

	}

	/**
	 * 审批单据细节
	 * 
	 * @param temp
	 * @throws NotBoundException 
	 * @throws IOException 
	 * @throws RemoteException 
	 */
	private void executeApproval(SheetPO sheetPO) throws RemoteException {
		//  根据单据类别调用 单据通过的方法
			if (sheetPO.getType().equals("入库单")) {
				System.out.println("入库单被审批！");
				StorehouseManagementExecutionService execute = new StorehouseManagementController();
				execute.executeImportItem((ImportItemSheetPO) sheetPO);
			} else if (sheetPO.getType().equals("出库单")) {
				StorehouseManagementExecutionService execute = new StorehouseManagementController();
				execute.executeExportItem((ExportItemSheetPO) sheetPO);
			} else if (sheetPO.getType().equals("发货单")) {
				TransferSheetManagementExecutionService execute = new TransferSheetManagementController();
				execute.executeSendItem((SendItemSheetPO) sheetPO);
			} else if (sheetPO.getType().equals("收货单")) {
				TransferSheetManagementExecutionService execute = new TransferSheetManagementController();
				execute.executeReceiveItem((ReceiveItemSheetPO) sheetPO);
			} else if (sheetPO.getType().equals("付款单")) {
				PaymentExecutionService execute = new PaymentExecutionController();
				execute.executePaymentItem((PaymentPO) sheetPO);
			} else if (sheetPO.getType().equals("寄件单")) {			
					DispatcherAndCreater execute=new DispatcherAndCreaterImpl();
					execute.sendOrder((SendOrderSheetPO)sheetPO);				
			}else if (sheetPO.getType().equals("派件单")) {
					DispatcherAndCreater execute=new DispatcherAndCreaterImpl();
					execute.dispatchOrder((DispatchOrderSheetPO)sheetPO);
			}
	}

	/**
	 * 插入单据到数据文件； 单据的状态为三种 提交 已审批 未通过； 插入单据的时候 状态 填写为 提交
	 * 
	 * @param po
	 */
	public void insertSheet(SheetPO sheetPO) {
		try {
			this.po = this.bufferDataService.getAll();
			this.po.add(sheetPO);
			this.bufferDataService.writeAll(this.po);
		} catch (Exception e) {
			this.po = new ArrayList<SheetPO>();
			this.po.add(sheetPO);
			try {
				this.bufferDataService.writeAll(this.po);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws RemoteException {
//		{
//		ExamineBL a = new ExamineBL();
//		GetOrderPO b=new GetOrderPOImpl();
//		OrderVO vo=b.getOrderPO("0001115555").getOrderVO();
//		SendOrderSheetPO po=new SendOrderSheetPO(a.getSheetID(), "1111111", "寄件单", TimeUtil.getCurrentTime(), "提交", "xxxxxxxxx", vo, "10000000", "仙林");
//       a.insertSheet(po);
//       System.out.println("success");
					//ExamineBL a = new ExamineBL();
		
			ExamineBL b = new ExamineBL();
			for (int i = 0; i < 2; i++) {
				ImportItemSheetPO sheet = new ImportItemSheetPO(b.getSheetID(), "0000111", "入库单",
						TimeUtil.getCurrentTime(), "提交", "xxxxxxxxx", "001000", "", 0, 0, 0, "", 0, "");
				b.insertSheet(sheet);
				System.out.println(b.getSheetID());
			}
		
		
//				ImportItemSheetPO sheet1 = new ImportItemSheetPO(b.getSheetID(), "0000111", "入库单",
//						TimeUtil.getCurrentTime(), "提交", "xxxxxxxxx", "001000", "", 0, 0, 0, "", 0, "");
//				b.insertSheet(sheet1);
//				System.out.println(b.getSheetID());
//			}
//		}

		// ArrayList<SheetPO> po=new ArrayList<>();
		// po.add(sheet1);
		// try {
		// BufferDataService buffer= (BufferDataService)
		// Naming.lookup("rmi://" + RMI.getIP() + ":2014/BufferData");
		// buffer.writeAll(po);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}
}
