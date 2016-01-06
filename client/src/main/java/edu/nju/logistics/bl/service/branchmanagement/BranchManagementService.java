package edu.nju.logistics.bl.service.branchmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.branchdata.*;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.ordermanagement.OrderVO;

public interface BranchManagementService extends InstitutionGetter{
	
	/**
	 * 删除车辆
	 * @param id 车辆id
	 * @throws RemoteException 
	 */
	public void deleteCar(String carid) throws RemoteException;
	
	/**
	 * 删除司机
	 * @param id 司机id
	 * @throws RemoteException 
	 */
	public void deleteDriver(String driverid) throws RemoteException;
	
	/**
	 * 增加司机信息
	 * @param driverVO 增加的司机信息vo
	 * @throws RemoteException 
	 */
	public void addDriver (DriverPO driverVO) throws RemoteException;
	
	/**
	 * 获得司机的完全列表
	 * @return 司机列表
	 * @throws RemoteException 
	 */
	public ArrayList<DriverPO> getAllDriverList() throws RemoteException;
	
	/**
	 * 获得车辆的完全列表
	 * @return 车辆
	 * @throws RemoteException 
	 */
	public ArrayList<CarPO> getAllCarList() throws RemoteException;
	
	/**
	 * 获得司机的空闲列表
	 * @return 司机列表
	 * @throws RemoteException 
	 */
	public ArrayList<DriverPO> getFreeDriverList() throws RemoteException;
	
	/**
	 * 获得车辆的完全列表
	 * @return 车辆
	 * @throws RemoteException 
	 */
	public ArrayList<CarPO> getFreeCarList() throws RemoteException;

	/**
	 * 增加车辆信息
	 * @param id 车牌号
	 * @param time 初始服役时间
	 * @throws RemoteException 
	 */
	public void addCar(CarPO carPO) throws RemoteException;

	/**
	 * 获得下一个车辆的id
	 * @return
	 * @throws RemoteException 
	 */
	public String getNextCarID() throws RemoteException;
	
	/**
	 * 获得下一个司机的id
	 * @return
	 * @throws RemoteException 
	 */
	public String getNextDriverID() throws RemoteException;
	
	/**
	 * 根据订单的数量，重量等获取中转运费
	 * @param orderList
	 * @return
	 * @throws RemoteException 
	 */
	public double getTransferFee(ArrayList<OrderVO> orderList) throws RemoteException;

	/**
	 * 获得上级中转中心的id
	 * @return
	 */
	public String getUpperCenterID();
	
	/**
	 * 获得上级中转中心的名字
	 * @return
	 * @throws RemoteException 
	 */
	public String getUpperCenterName() throws RemoteException;

	/**
	 * 装车
	 * @param selectedOrderList
	 * @param destinationid
	 * @param carid
	 * @param driverid
	 * @param observer
	 * @param supercargo
	 * @param fee
	 * @throws RemoteException 
	 */
	public void load(ArrayList<OrderVO> selectedOrderList,
			String destinationid, String carid, String driverid,
			String observer, String supercargo, double fee, String staffid) throws RemoteException;
	
	/**
	 * @return 返回已到达，未接收的中转单
	 * @throws RemoteException 
	 */
	public ArrayList<TransferSheetVO> getLocalTransferSheetList() throws RemoteException;

	/**
	 * 接收中转单
	 * @param id 确认接收的中转单id
	 * @throws RemoteException 
	 */
	public void receiveTransferSheet(String id, String staffid, ReceiptState status) throws RemoteException;

	/**
	 * @return 返回该营业厅的快递员列表
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> getCourierList() throws RemoteException;

}
