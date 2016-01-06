package edu.nju.logistics.bl.impl.storehousemanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLCenterService;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLFinanceService;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLService;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementExecutionService;
import edu.nju.logistics.po.ExportItemSheetPO;
import edu.nju.logistics.po.ImportItemSheetPO;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.StorehouseRecordVO;
import edu.nju.logistics.vo.StorehouseVO;

public class StorehouseManagementController implements StorehouseManagementBLService, StorehouseManagementExecutionService,
	StorehouseManagementBLCenterService, StorehouseManagementBLFinanceService {

	private StorehouseManagement storehouseManagement = null;
	
	public StorehouseManagementController() throws RemoteException {
		this.storehouseManagement = new StorehouseManagement();
	}
	
	@Override
	public ArrayList<StorehouseVO> show(String centerID, String area) throws RemoteException {
		return storehouseManagement.show(centerID, area);
	}

	@Override
	public void exportItem(String id, String staffID) throws RemoteException {
		storehouseManagement.createExportItemSheet(id, staffID);
	}

	@Override
	public void importItem(StorehouseVO vo, String staffID) throws RemoteException {
		storehouseManagement.createImportItemSheet(vo, staffID);
	}

	@Override
	public ArrayList<StorehouseRecordVO> takeStock(String centerID, String startTime, String endTime) throws RemoteException {
		return storehouseManagement.getRecord(centerID, startTime, endTime);
	}

	@Override
	public void adjust(String id, String staffID) throws RemoteException {
		storehouseManagement.adjust(id, staffID);
	}

	@Override
	public void setAlarm(String centerID, double alarm) throws RemoteException {
		storehouseManagement.setAlarm(centerID, alarm);	
	}
	
	@Override
	public double getAlarm(String centerID) throws RemoteException {
		return storehouseManagement.getAlarm(centerID);
	}

	@Override
	public void executeImportItem(ImportItemSheetPO po) throws RemoteException {
		storehouseManagement.executeImportItem(po);		
	}

	@Override
	public void executeExportItem(ExportItemSheetPO po) throws RemoteException {
		storehouseManagement.executeExportItem(po);		
	}

	@Override
	public ArrayList<StorehouseVO> check(String centerID) throws RemoteException {
		return storehouseManagement.check(centerID);
	}

	@Override
	public double getPercentage(String id, String area) throws RemoteException {
		return storehouseManagement.getPercentage(id, area);
	}

	@Override
	public void setScale(String centerID, String area, int lineNum, int shelfNum, int positionNum) throws RemoteException {
		storehouseManagement.setScale(centerID, area, lineNum, shelfNum, positionNum);		
	}

	@Override
	public void clear(String id) throws RemoteException {
		storehouseManagement.clear(id);	
	}

	@Override
	public int[] getPlace(String centerID, String area) throws RemoteException {
		return storehouseManagement.getPlace(centerID, area);
	}

	@Override
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException {
		return storehouseManagement.getToSend(centerID);		
	}

	@Override
	public void addToStorage(StorehouseVO vo) throws RemoteException {
		storehouseManagement.addToStorage(vo);
	}

	@Override
	public int getMoney(String id) throws RemoteException {
		return storehouseManagement.getMoney(id);
	}

	@Override
	public String getInstitutionIDByUser(String id) throws RemoteException {
		return storehouseManagement.getInstitutionByUser(id);
	}

	@Override
	public ArrayList<BufferVO> getToImport(String centerID) throws RemoteException {
		return storehouseManagement.getToImport(centerID);
	}

	@Override
	public String getPatchTime(String centerID) throws RemoteException {
		return storehouseManagement.getPatchTime(centerID);
	}

	@Override
	public String[] getDestinations(String centerID) throws RemoteException {
		return storehouseManagement.getDestinations(centerID);
	}

	@Override
	public void setToImport(BufferVO vo) throws RemoteException {
		storehouseManagement.setToSend(vo);
	}

	@Override
	public void deleteBuffer(String id) throws RemoteException {
		storehouseManagement.deleteBuffer(id);
	}
	
	@Override
	public boolean isOrderExist(String id) throws RemoteException {
		return storehouseManagement.isOrderExist(id);
	}

	@Override
	public int getStorageCount(String centerID, String area) throws RemoteException {
		return storehouseManagement.getStorageCount(centerID, area);
	}

	@Override
	public int getScaleNumber(String centerID, String area) throws RemoteException {
		return storehouseManagement.getScaleNumber(centerID, area);
	}
	
	@Override
	public String getOrderAddress(String id) throws RemoteException {
		return storehouseManagement.getOrderAddress(id);
	}

}
