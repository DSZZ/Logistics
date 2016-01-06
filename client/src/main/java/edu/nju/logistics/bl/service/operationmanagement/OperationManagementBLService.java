package edu.nju.logistics.bl.service.operationmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.nju.logistics.vo.DistanceVO;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.PriceVO;
import edu.nju.logistics.vo.SheetVO;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.financial.BalanceChartVO;
import edu.nju.logistics.vo.financial.PaymentInfo;

/**
 * 总经理的职能
 * @author 董轶波
 *
 */
public interface OperationManagementBLService extends LogCheck,EnvironmentGetter {
	/**
	 * 制定薪水
	 * @param id
	 * @throws RemoteException 
	 */
	public void makeSalaryStrategy(HashMap<String,Double> map) throws RemoteException;
	/**
	 * 制定薪水策略界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public HashMap<String,Double> salaryShow() throws RemoteException;
	/**
	 * 制定城市机构之间距离
	 * @param vo
	 * @throws RemoteException 
	 */
	public void makeDistance(DistanceVO vo) throws RemoteException;
	/**
	 * 制定距离界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<DistanceVO> distanceShow() throws RemoteException;
	/**
	 * 制定价格
	 * @param vo
	 * @throws RemoteException 
	 */
	public void makePrice(PriceVO vo) throws RemoteException;
	/**
	 * 制定价格界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public PriceVO priceShow() throws RemoteException;
	/**
	 * 对某一项单据进行审批
	 * @param vo
	 * @throws RemoteException 
	 */
	public void setApproval(SheetVO vo) throws RemoteException ;
	/**
	 * 批量审批单据
	 * @param vo
	 * @throws RemoteException 
	 */
	public void setMassApproval(String type) throws RemoteException ;
	/**
	 * 选择相关单据进行显示
	 * @param type
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<SheetVO> sheetShow(String type) throws RemoteException ;
	/**
	 * 机构管理界面初始化
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<InstitutionVO> institutionShow() throws RemoteException;
	/**
	 * 选择某机构的员工查看
	 * @param po
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> employeeShow(InstitutionVO vo) throws RemoteException;
	/**
	 * 增加机构
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean addInstitution(InstitutionVO vo) throws RemoteException;
	/**
	 * 删除机构
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public boolean deleteInstitution(String id) throws RemoteException;
	/**
	 * 查看机构信息
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public InstitutionVO checkInstitution(String id) throws RemoteException;
	/**
	 * 修改机构信息
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean updateInstitution(String oldID,InstitutionVO vo) throws RemoteException;
	/**
	 * 增加职员
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean addEmployee(UserVO vo) throws RemoteException;
	/**
	 * 删除职员
	 * @param id
	 * @return
	 * @throws RemoteException 
	 */
	public boolean deleteEmployee(String id) throws RemoteException;
	/**
	 * 根据ID查看职员信息
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public UserVO checkEmployeeByID(String ID) throws RemoteException;
	/**
	 * 根据职位查询职员信息
	 * @param role
	 * @param InstitutionID
	 * @return
	 * @throws RemoteException 
	 */
	public ArrayList<UserVO> checkEmployeeByRole(String role,String InstitutionID) throws RemoteException;
	/**
	 * 修改职员信息
	 * @param oldID 
	 * @param vo
	 * @return
	 * @throws RemoteException 
	 */
	public boolean updateEmployee(String oldID, UserVO vo) throws RemoteException;
	/**
	 * 获得成本收益表
	 * @return
	 * @throws RemoteException 
	 */
	public BalanceChartVO getBalanceChar() throws RemoteException;
	/**
	 * 设置审批未通过
	 * @param type 
	 * @param iD
	 * @throws RemoteException 
	 */
	public void setNoApproval(String id) throws RemoteException ;
	/**
	 * 获得经营情况表
	 * 付款单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ArrayList<PaymentInfo> showPaymentList(String startTime, String endTime)throws RemoteException;
	/**
	 * 获得经营情况表
	 * 收款单
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ArrayList<ReceiptInfo> showReceiptList(String startTime, String endTime)throws RemoteException ;
}
