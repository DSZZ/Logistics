package edu.nju.logistics.bl.impl.storehousemanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.impl.log.LogBL;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.impl.ordermanagement.serviceProvider.GetOrderPOImpl;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.ordermanagement.GetOrderPO;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.storagedata.StorageDataService;
import edu.nju.logistics.po.BufferPO;
import edu.nju.logistics.po.ExportItemSheetPO;
import edu.nju.logistics.po.ImportItemSheetPO;
import edu.nju.logistics.po.SheetPO;
import edu.nju.logistics.po.StoragePO;
import edu.nju.logistics.po.StorageRecordPO;
import edu.nju.logistics.po.StorageState;
import edu.nju.logistics.po.orderdata.OrderPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.LogVO;
import edu.nju.logistics.vo.StorehouseRecordVO;
import edu.nju.logistics.vo.StorehouseVO;

/**
 * 仓库管理控制
 * 
 * @author HanzZou
 * @see StorageDataService
 * @see LogBL
 * @see ExamineBL
 *
 */
public class StorehouseManagement {

	private StorageDataService storageData = null;

	private LogBL logBL = null;

	private ExamineBL examine = null;

	private EnvironmentGetter getter = null;

	private GetOrderPO order = null;

	private ArrayList<StoragePO> storagePOList = null;

	private ArrayList<StorehouseVO> storehouseVOList = null;

	public StorehouseManagement() throws RemoteException {
		logBL = new LogBL();
		examine = new ExamineBL();
		getter = new OperationManagementController();
		order = new GetOrderPOImpl();
		try {
			storageData = (StorageDataService) Naming.lookup("rmi://" + RMI.getIP() + ":2014/StorageData");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示指定中转中心仓库内指定分区列表
	 * 
	 * @param centerID
	 *            中转中心ID
	 * @param area
	 *            指定分区
	 * @return
	 */
	public ArrayList<StorehouseVO> show(String centerID, String area) throws RemoteException {
		storehouseVOList = new ArrayList<StorehouseVO>();
		storagePOList = storageData.find();
		for (StoragePO po : storagePOList) {
			if (po.getCenterID().equals(centerID) && po.getArea().equals(area) && po.getState() == StorageState.IN) {
				storehouseVOList
						.add(new StorehouseVO(po.getCenterID(), po.getTime(), po.getArea(), po.getLine(), po.getShelf(),
								po.getPosition(), po.getNumber(), po.getDestination(), po.getMoney(), po.getState()));
			}
		}
		return storehouseVOList;
	}

	/**
	 * 提供给总经理执行出库相关数据改动
	 * 
	 * @param vo
	 *            出库单
	 * @return
	 */
	public void executeExportItem(ExportItemSheetPO po) throws RemoteException {
		logBL.insert(new LogVO(TimeUtil.getCurrentTime(), po.getEmployeeId(), "将编号" + po.getNumber() + "的快递出库。"));
		storageData.deleteByID(po.getNumber());
		storageData.setToBuffer(new BufferPO(po.getCenterID(), po.getNumber(), po.getDestination(), "export"));
		storageData.setStorageRecord(new StorageRecordPO(po.getCenterID(), po.getArea(), po.getLine(), po.getShelf(),
				po.getPosition(), po.getNumber(), "export", po.getMoney(), TimeUtil.getCurrentTime()));
	}

	/**
	 * 提供给仓库管理员创建出库单据
	 * 
	 * @param vo
	 *            界面返回的库存列表
	 */
	public void createExportItemSheet(String id, String staffID) throws RemoteException {
		StoragePO spo = null;
		storagePOList = storageData.find();
		for(StoragePO po : storagePOList) {
			if(po.getNumber().equals(id)) {
				spo = po;
				break;
			}
		}
		SheetPO po = new ExportItemSheetPO(examine.getSheetID(), staffID, "出库单", TimeUtil.getCurrentDate(), "提交",
				"将编号为" + spo.getNumber() + "的快件出库", spo.getCenterID(), spo.getArea(), spo.getLine(), spo.getShelf(),
				spo.getPosition(), spo.getNumber(), spo.getMoney(), spo.getDestination());
		examine.insertSheet(po);
		storageData.updateState(id, StorageState.EXPORT);
	}

	/**
	 * 查询出入库记录
	 * 
	 * @param centerID
	 *            中转中心ID
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 出入库记录列表
	 */
	public ArrayList<StorehouseRecordVO> getRecord(String centerID, String startTime, String endTime) {
		ArrayList<StorehouseRecordVO> storehouseRecordList = new ArrayList<StorehouseRecordVO>();
		ArrayList<StorageRecordPO> storageRecordList = null;
		try {
			storageRecordList = storageData.getStorageRecord(centerID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < storageRecordList.size(); i++) {
			StorageRecordPO po = storageRecordList.get(i);
			if (!TimeUtil.isFore(po.getTime(), startTime) && TimeUtil.isFore(po.getTime(), endTime)) {
				storehouseRecordList.add(new StorehouseRecordVO(po.getCenterID(), po.getArea(), po.getLine(),
						po.getShelf(), po.getPosition(), po.getNumber(), po.getState(), po.getMoney(), po.getTime()));
			}
		}
		return storehouseRecordList;

	}

	/**
	 * 提供给总经理执行入库操作
	 * 
	 * @param po
	 *            入库单
	 */
	public void executeImportItem(ImportItemSheetPO po) throws RemoteException {
		logBL.insert(new LogVO(TimeUtil.getCurrentTime(), po.getEmployeeId(), "将编号为" + po.getNumber() + "的快件入库。"));
		storageData.updateState(po.getNumber(), StorageState.IN);
		storageData.setStorageRecord(new StorageRecordPO(po.getCenterID(), po.getArea(), po.getLine(), po.getShelf(),
				po.getPosition(), po.getNumber(), "import", po.getMoney(), TimeUtil.getCurrentTime()));
	}

	/**
	 * 提供给仓库管理员创建入库单据
	 * 
	 * @param vo
	 *            界面返回的入库数据
	 * @param staffID
	 *            操作人员ID
	 */
	public void createImportItemSheet(StorehouseVO vo, String staffID) throws RemoteException {
		SheetPO po = new ImportItemSheetPO(examine.getSheetID(), staffID, "入库单", TimeUtil.getCurrentDate(), "提交",
				"将编号为" + vo.getNumber() + "的快件入库。", vo.getCenterID(), vo.getArea(), vo.getLine(), vo.getShelf(),
				vo.getPosition(), vo.getNumber(), vo.getMoney(), vo.getDestination());
		examine.insertSheet(po);
		StoragePO spo = new StoragePO(vo.getCenterID(), vo.getTime(), vo.getArea(), vo.getLine(), vo.getShelf(),
				vo.getPosition(), vo.getNumber(), vo.getDestination(), vo.getMoney(), StorageState.IMPORT);
		storageData.insert(spo);
		storageData.deleteBuffer(vo.getNumber());
	}

	/**
	 * 提供给仓库管理团进行库存调整
	 * 
	 * @param id
	 *            库存货物id
	 * @param staffID
	 *            员工ID
	 */
	public void adjust(String id, String staffID) throws RemoteException {
		logBL.insert(new LogVO(TimeUtil.getCurrentTime(), staffID, "将编号为" + id + "的货物调整至机动区。"));
		storageData.update(id);
	}

	/**
	 * 提供给仓库管理员设置警戒值
	 * 
	 * @param alarm
	 *            警戒值
	 */
	public void setAlarm(String centerID, double alarm) throws RemoteException {
		storageData.setAlarm(centerID, alarm);
	}

	/**
	 * 提供给仓库管理员获得批次时间
	 * 
	 * @param centerID
	 * @return
	 */
	public String getPatchTime(String centerID) throws RemoteException {
		String patchTime = storageData.getPatchTime(centerID);
		return patchTime;
	}

	/**
	 * 提供给仓库管理员设置批次时间
	 * 
	 * @param time
	 * @param centerID
	 */
	private void setPatchTime(String time, String centerID) throws RemoteException {
		try {
			storageData.setPatchTime(time, centerID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 库存盘点
	 * 
	 * @param centerID
	 * @return
	 */
	public ArrayList<StorehouseVO> check(String centerID) throws RemoteException {
		storehouseVOList = new ArrayList<StorehouseVO>();
		String time = this.getPatchTime(centerID);
		storagePOList = storageData.find();
		for (StoragePO po : storagePOList) {
			if (po.getCenterID().equals(centerID) && TimeUtil.isFore(time, po.getTime()) && po.getState().equals(StorageState.IN))
				storehouseVOList
						.add(new StorehouseVO(po.getCenterID(), po.getTime(), po.getArea(), po.getLine(), po.getShelf(),
								po.getPosition(), po.getNumber(), po.getDestination(), po.getMoney(), StorageState.IN));
		}
		this.setPatchTime(TimeUtil.getCurrentTime(), centerID);
		return storehouseVOList;
	}

	/**
	 * 获得指定中转中心指定区域的警戒值
	 * 
	 * @param id
	 * @param area
	 * @return
	 */
	public double getPercentage(String id, String area) throws RemoteException {
		int count = 0;
		storagePOList = storageData.find();
		for (StoragePO po : storagePOList) {
			if (po.getCenterID().equals(id) && po.getArea().equals(area))
				count++;
		}
		int lineNum, shelfNum, positionNum = 0;
		int[] scale = new int[3];
		scale = storageData.getScale(id, area);
		lineNum = scale[0];
		shelfNum = scale[1];
		positionNum = scale[2];
		return (double) count / (lineNum * shelfNum * positionNum);
	}

	/**
	 * 设置仓库规模
	 * 
	 * @param centerID
	 * @param area
	 * @param lineNum
	 * @param shelfNum
	 * @param positionNum
	 */
	public void setScale(String centerID, String area, int lineNum, int shelfNum, int positionNum)
			throws RemoteException {
		storageData.setScale(centerID, area, lineNum, shelfNum, positionNum);

	}

	/**
	 * 清空指定仓库
	 * 
	 * @param id
	 */
	public void clear(String id) throws RemoteException {
		storageData.deleteByCenterID(id);
	}

	/**
	 * 获得空位
	 * 
	 * @param centerID
	 * @param area
	 * @return
	 * @throws RemoteException
	 */
	public int[] getPlace(String centerID, String area) throws RemoteException {
		int[] position = new int[3];
		int[] place = new int[3];
		storagePOList = storageData.find();
		position = storageData.getScale(centerID, area);
		ArrayList<StoragePO> list = new ArrayList<StoragePO>();
		if (storagePOList.size() != 0) {
			for (StoragePO po : storagePOList) {
				if (po.getCenterID().equals(centerID) && po.getArea().equals(area))
					list.add(po);
			}
		}
		for (int i = 1; i <= position[0]; i++) {
			for (int j = 1; j <= position[1]; j++) {
				for (int p = 1; p <= position[2]; p++) {
					boolean exist = false;
					if (list.size() != 0) {
						for (StoragePO po : storagePOList) {
							if (po.getLine() == i && po.getShelf() == j && po.getPosition() == p) {
								exist = true;
								break;
							}
						}
					}
					if (!exist) {// 判断是否存在
						place[0] = i;
						place[1] = j;
						place[2] = p;
						return place;
					}
				}
			}
		}
		return place;
	}

	/**
	 * 添加到库存中（初始化库存）
	 * 
	 * @param vo
	 * @throws RemoteException
	 */
	public void addToStorage(StorehouseVO vo) throws RemoteException {
		StoragePO po = new StoragePO(vo.getCenterID(), vo.getTime(), vo.getArea(), vo.getLine(), vo.getShelf(),
				vo.getPosition(), vo.getNumber(), vo.getDestination(), vo.getMoney(), StorageState.IN);
		storageData.insert(po);
		StorageRecordPO record = new StorageRecordPO(vo.getCenterID(), vo.getArea(), vo.getLine(), vo.getShelf(),
				vo.getPosition(), vo.getNumber(), "import", vo.getMoney(), TimeUtil.getCurrentTime());
		storageData.setStorageRecord(record);
	}

	/**
	 * 获得待发送列表
	 * 
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException {
		ArrayList<BufferPO> poTemp = new ArrayList<BufferPO>();
		ArrayList<BufferVO> voTemp = new ArrayList<BufferVO>();
		poTemp = storageData.getBuffer(centerID, false);
		for (BufferPO po : poTemp) {
			voTemp.add(new BufferVO(po.getCenterID(), po.getId(), po.getDestination(), po.getStatus()));
		}
		return voTemp;
	}

	/**
	 * 获得金额
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public int getMoney(String id) throws RemoteException {
		OrderPO po = null;
		po = order.getOrderPO(id);
		return po.getOrderVO().getTotalfee();
	}

	/**
	 * 获得职员所在机构id
	 * 
	 * @param id
	 * @return
	 */
	public String getInstitutionByUser(String id) throws RemoteException {
		return getter.getUserInstitutionID(id);
	}

	/**
	 * 获得待入库列表
	 * 
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<BufferVO> getToImport(String centerID) throws RemoteException {
		ArrayList<BufferPO> poTemp = null;
		ArrayList<BufferVO> voTemp = new ArrayList<BufferVO>();
		poTemp = storageData.getBuffer(centerID, true);
		if (poTemp == null) {
			return voTemp;
		}
		for (BufferPO po : poTemp) {
			voTemp.add(new BufferVO(po.getCenterID(), po.getId(), po.getDestination(), po.getStatus()));
		}
		return voTemp;
	}

	/**
	 * 获得警戒值
	 * 
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 * @throws NumberFormatException
	 */
	public double getAlarm(String centerID) throws RemoteException {
		double alarm = 0;
		alarm = Double.parseDouble(storageData.getAlarm(centerID));
		return alarm;
	}

	/**
	 * 获得特定中转中心下一目的地列表
	 * 
	 * @param centerID
	 * @return
	 */
	public String[] getDestinations(String centerID) throws RemoteException {
		ArrayList<String> temp = getter.getListToShow(centerID);
		String[] destinations = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			destinations[i] = temp.get(i);
		}
		return destinations;
	}

	/**
	 * 添加到待发货列表中
	 * 
	 * @param vo
	 * @throws RemoteException
	 */
	public void setToSend(BufferVO vo) throws RemoteException {
		BufferPO po = new BufferPO(vo.getCenterID(), vo.getId(), vo.getDestination(), vo.getStatus());
		storageData.setToBuffer(po);
	}

	/**
	 * 从缓存中删除
	 * 
	 * @param id
	 * @throws RemoteException
	 */
	public void deleteBuffer(String id) throws RemoteException {
		storageData.deleteBuffer(id);
	}

	/**
	 * 检查id的快递是否存在
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean isOrderExist(String id) throws RemoteException {
		order.getOrderPO(id);
		return true;

	}
	
	/**
	 * 
	 * @param centerID
	 * @param area
	 * @return
	 * @throws RemoteException
	 */
	public int getScaleNumber(String centerID, String area) throws RemoteException {
		int[] temp = storageData.getScale(centerID, area);
		return temp[0] * temp[1] * temp[2];
	}
	
	public int getStorageCount(String centerID, String area) throws RemoteException {
		return this.show(centerID, area).size();
	}
	
	public String getOrderAddress(String id) throws RemoteException {
		OrderPO po = null;
		po = order.getOrderPO(id);
		return po.getOrderVO().getReceiverAddress();
	}

	public static void main(String[] args) {
//		StorehouseManagement s = null;
//		try {
//			s = new StorehouseManagement();
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
	}

}
