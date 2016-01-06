package edu.nju.logistics.data.service.storagedata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.BufferPO;
import edu.nju.logistics.po.StoragePO;
import edu.nju.logistics.po.StorageRecordPO;
import edu.nju.logistics.po.StorageState;

public interface StorageDataService extends Remote {
	
	/**
	 * 获得指定中转中心的指定时间段内的库存列表
	 * @return 库存列表
	 * @throws RemoteException
	 */
	public ArrayList<StoragePO> find () throws RemoteException;
	
	/**
	 * 插入库存信息
	 * @param po-单个库存
	 * @return 是否成功
	 * @throws RemoteException
	 */
	public Boolean insert (StoragePO po) throws RemoteException;
	
	/**
	 * 更新库存状态
	 * @param id
	 * @param s
	 * @throws RemoteException
	 */
	public void updateState(String id, StorageState s) throws RemoteException;
	
	/**
	 * 删除库存信息
	 * @param id-单个快件编号
	 * @return 是否成功
	 * @throws RemoteException
	 */
	public Boolean deleteByID(String id) throws RemoteException;
	
	/**
	 * 
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 */
	public Boolean deleteByCenterID(String centerID) throws RemoteException;
	
	/**
	 * 更新库存信息
	 * @param po-
	 * @return
	 * @throws RemoteException
	 */
	public Boolean update (String id) throws RemoteException;
	
	/**
	 * 获得待入库或出库列表
	 * @param centerID
	 * @param isImport 入库或出库
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<BufferPO> getBuffer(String centerID, boolean isImport) throws RemoteException;
	
	/**
	 * 写入缓存列表
	 * @param BufferPO 缓存对象 
	 * @return 是否成功
	 * @throws RemoteException
	 */
	public Boolean setToBuffer(BufferPO po) throws RemoteException;
	
	/**
	 * 删除缓存
	 * @param id 需要删除的快递编号
	 * @throws RemoteException
	 */
	public void deleteBuffer(String id) throws RemoteException;
	
	/**
	 * 写入出入库记录
	 * @param centerID-中转中心ID
	 * @return
	 * @throws RemoteException
	 */
	public Boolean setStorageRecord(StorageRecordPO po) throws RemoteException;
	
	/**
	 * 获取出入库记录
	 * @param centerID-中转中心ID
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<StorageRecordPO> getStorageRecord(String centerID) throws RemoteException;
	
	/**
	 * 设置警戒值
	 * @param centerID 
	 * @param alarm
	 * @throws RemoteException
	 */
	public void setAlarm(String centerID, double alarm) throws RemoteException;
	
	/**
	 * 获取警戒值
	 * @param centerID
	 * @return
	 * @throws RemoteException
	 */
	public String getAlarm(String centerID) throws RemoteException;
	
	/**
	 * 获得批次时间
	 * @param centerID
	 * @return
	 * @throws RemoteException 
	 */
	public String getPatchTime(String centerID) throws RemoteException;
	
	/**
	 * 设置批次时间
	 * @param time
	 * @param centerID
	 * @throws RemoteException 
	 */
	public void setPatchTime(String time, String centerID) throws RemoteException;
	
	/**
	 * 设置仓库规模
	 * @param centerID
	 * @param area
	 * @param lineNum
	 * @param shelfNum
	 * @param positionNum
	 * @throws RemoteException
	 */
	public void setScale(String centerID, String area, int lineNum, int shelfNum, int positionNum) throws RemoteException;
	
	/**
	 * 获取仓库规模
	 * @param centerID
	 * @param area
	 * @return
	 * @throws RemoteException
	 */
	public int[] getScale(String centerID, String area) throws RemoteException;

}
