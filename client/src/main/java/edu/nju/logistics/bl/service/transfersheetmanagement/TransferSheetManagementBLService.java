package edu.nju.logistics.bl.service.transfersheetmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.vo.BufferVO;
import edu.nju.logistics.vo.ReceiptState;
import edu.nju.logistics.vo.TransferSheetVO;
import edu.nju.logistics.vo.Transportation;
import edu.nju.logistics.vo.ordermanagement.OrderVO;
/**
 * 
 * @author HanzZou
 *
 */
public interface TransferSheetManagementBLService {
	
	/**
	 * 显示指定机构需要接收的中转单
	 * @param centerID 机构编号
	 * @return 机构对应的中转单编号
	 */
	public ArrayList<TransferSheetVO> show(String centerID) throws RemoteException;
	
	/**
	 * 创建中转单
	 * @param vo
	 * @param staffID 职员ID
	 */
	public void createTransferSheet(TransferSheetVO vo, String staffID) throws RemoteException;
	
	/**
	 * 接收货物
	 * @param id 中转单ID
	 * @param staffID 职员ID
	 */
	public void receiveItems(String id, String staffID, ReceiptState s) throws RemoteException;
	
	/**
	 * 获取中转单ID
	 * @return
	 */
	public String getTransferSheetID() throws RemoteException;
	
	/**
	 * 获取下一步目的地机构ID
	 * @param centerID
	 * @return
	 */
	public String[] getDestinations(String centerID) throws RemoteException;

	/**
	 * 获取职员所在机构ID
	 * @param id
	 * @return
	 */
	public String getInstitutionIDByUser(String id) throws RemoteException;

	/**
	 * 获取运费
	 * @param object 
	 * @param string 
	 * @param tran 
	 * @param voList 
	 * @return
	 */
	public double getFee(ArrayList<OrderVO> voList, Transportation tran, String centerID1, String centerID2) throws RemoteException;
	
	/**
	 * 获得待发货列表
	 * @param centerID
	 * @return
	 */
	public ArrayList<BufferVO> getToSend(String centerID) throws RemoteException;

	/**
	 * 根据order的id获得vo
	 * @param id
	 * @return
	 */
	public OrderVO getOrderVO(String id) throws RemoteException;
	
	/**
	 * 根据机构id获得机构名
	 * @param id
	 * @return
	 */
	public String getInstituionNameByID(String id) throws RemoteException;

	/**
	 * 根据名字获得机构ID
	 * @param selectedItem
	 * @return
	 */
	public String getInstitutionIDByName(String instituionName) throws RemoteException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<OrderVO> getOrderListInTransferSheet(String id) throws RemoteException;
	
}
