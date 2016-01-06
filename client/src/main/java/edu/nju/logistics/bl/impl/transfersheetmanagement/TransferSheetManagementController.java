package edu.nju.logistics.bl.impl.transfersheetmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementBLService;
import edu.nju.logistics.bl.service.transfersheetmanagement.TransferSheetManagementExecutionService;
import edu.nju.logistics.po.ReceiveItemSheetPO;
import edu.nju.logistics.po.SendItemSheetPO;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public class TransferSheetManagementController implements TransferSheetManagementBLService, TransferSheetManagementExecutionService {
	
	TransferSheetManagement transferSheetManagement = null;
	
	public TransferSheetManagementController() throws RemoteException {
		transferSheetManagement = new TransferSheetManagement();
	}

	@Override
	public ArrayList<TransferSheetVO> show(String centerID) throws RemoteException {
		return transferSheetManagement.getTransferSheetList(centerID);
	}

	@Override
	public void executeSendItem(SendItemSheetPO po) throws RemoteException {
		transferSheetManagement.executeSendItem(po);
		
	}

	@Override
	public void executeReceiveItem(ReceiveItemSheetPO po) throws RemoteException {
		transferSheetManagement.executeReceiveItem(po);
		
	}

	@Override
	public void createTransferSheet(TransferSheetVO vo, String staffID) throws RemoteException {
		transferSheetManagement.sendItems(vo, staffID);
	}

	@Override
	public void receiveItems(String transferSheetID, String staffID, ReceiptState s) throws RemoteException {
		transferSheetManagement.receiveItems(transferSheetID, staffID, s);	
	}

	@Override
	public String getTransferSheetID() throws RemoteException {
		return transferSheetManagement.getID();
	}
	
	@Override
	public String[] getDestinations(String centerID) throws RemoteException {
		return transferSheetManagement.getDestinations(centerID);
	}
	
	@Override
	public String getInstitutionIDByUser(String id) throws RemoteException {
		return transferSheetManagement.getInstitutionByUser(id);
	}

	@Override
	public double getFee(ArrayList<OrderVO> voList, Transportation tran, String centerID1, String centerID2) throws RemoteException {
		return transferSheetManagement.calculateFee(voList, tran, centerID1, centerID2);
	}

	@Override
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException {
		return transferSheetManagement.getToSend(centerID);
	}

	@Override
	public OrderVO getOrderVO(String id) throws RemoteException {
		return transferSheetManagement.getOrderVO(id);
	}
	
	public String getInstitutionIDByName(String name) throws RemoteException {
		return transferSheetManagement.getInstituionIDByName(name);
	}

	@Override
	public String getInstituionNameByID(String id) throws RemoteException {
		return transferSheetManagement.getInstituionNameByID(id);
	}

	@Override
	public ArrayList<OrderVO> getOrderListInTransferSheet(String id) throws RemoteException {
		return transferSheetManagement.getOrderListInTransferSheet(id);
	}
	

}
