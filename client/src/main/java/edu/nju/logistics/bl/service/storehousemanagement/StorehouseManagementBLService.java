package edu.nju.logistics.bl.service.storehousemanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.StorehouseRecordVO;
import edu.nju.logistics.vo.StorehouseVO;
/**
 * 
 * @author HanzZou
 *
 */
public interface StorehouseManagementBLService {
	/**
	 * 获得要求的中转中心仓库的指定的区域库存列表
	 * @param centerID-仓库所在中转中心ID
	 * @param area-仓库的区域
	 * @return 库存的列表
	 */
	public ArrayList<StorehouseVO> show (String centerID, String area) throws RemoteException;
	/**
	 * 将某个快件出库
	 * @param vo-单个库存
	 * @return 是否成功
	 */
	public void exportItem (String id, String staffID) throws RemoteException;
	/**
	 * 将某个快件入库
	 * @param vo-单个库存
	 * @return 是否成功
	 */
	public void importItem (StorehouseVO vo, String staffID) throws RemoteException;
	/**
	 * 查看指定的中转中心的指定时间段的出入库记录
	 * @param centerID-中转中心ID
	 * @param startTime-开始时间
	 * @param endTime-结束时间
	 * @return 库存列表
	 */
	public ArrayList<StorehouseRecordVO> takeStock (String centerID, String startTime, String endTime) throws RemoteException;
	/**
	 * 库存盘点
	 * @param centerID
	 * @return
	 */
	public ArrayList<StorehouseVO> check(String centerID) throws RemoteException;
	/**
	 * 将某个库存调整区域
	 * @param vo-单个需要调整的快件
	 * @return 是否成功
	 */
	public void adjust (String id, String staffID) throws RemoteException;
	/**
	 * 设置警戒值
	 * @param userID 
	 * @param alarm-警戒值
	 */
	public void setAlarm (String userID, double alarm) throws RemoteException;
	/**
	 * 获取警戒值
	 * @param centerID
	 * @return
	 */
	public double getAlarm(String centerID) throws RemoteException;
	/**
	 * 获取即时警戒值
	 * @param id
	 * @param area
	 * @return
	 */
	public double getPercentage(String id, String area) throws RemoteException;
	
	/**
	 * 设置仓库规模
	 * @param centerID
	 * @param area
	 * @param lineNum
	 * @param shelfNum
	 * @param positionNum
	 */
	public void setScale(String centerID, String area, int lineNum, int shelfNum, int positionNum) throws RemoteException;
	
	/**
	 * 清空仓库
	 * @param id
	 */
	public void clear(String id) throws RemoteException;
	
	/**
	 * 获取位置
	 * @param centerID
	 * @param area
	 * @return
	 */
	public int[] getPlace(String centerID, String area) throws RemoteException;
	
	/**
	 * 添加到库存 
	 * @param vo
	 */
	public void addToStorage(StorehouseVO vo) throws RemoteException;
	
	/**
	 * 获得待发货列表
	 * @param centerID
	 * @return 
	 */
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException;
	
	/**
	 * 获得待入库列表
	 * @param centerID
	 * @return
	 */
	public ArrayList<BufferVO> getToImport(String centerID) throws RemoteException;
	
	/**
	 * 获取快递价格
	 * @param id
	 * @return
	 */
	public int getMoney(String id) throws RemoteException;
	
	/**
	 * 获取职员ID
	 * @param id
	 * @return
	 */
	public String getInstitutionIDByUser(String id) throws RemoteException;
	
	/**
	 * 获取批次时间
	 * @param centerID
	 * @return
	 */
	public String getPatchTime(String centerID) throws RemoteException;
	
	/**
	 * 获取目的地列表
	 * @param centerID
	 * @return
	 */
	public String[] getDestinations(String centerID) throws RemoteException;
	
	/**
	 * 查看id的快递是否存在
	 * @param id
	 * @return
	 */
	public boolean isOrderExist(String id) throws RemoteException;
	
	/**
	 * 根据快件号获得目的地地址
	 * @param id
	 * @return 订单对应详细地址
	 * @throws RemoteException
	 */
	public String getOrderAddress(String id) throws RemoteException;

}
