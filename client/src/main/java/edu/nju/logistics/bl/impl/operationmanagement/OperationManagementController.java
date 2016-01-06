package edu.nju.logistics.bl.impl.operationmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import edu.nju.logistics.bl.service.operationmanagement.OperationManagementBLService;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.vo.DistanceVO;
import edu.nju.logistics.vo.InstitutionVO;
import edu.nju.logistics.vo.LogVO;
import edu.nju.logistics.vo.PriceVO;
import edu.nju.logistics.vo.SheetVO;
import edu.nju.logistics.vo.UserVO;
import edu.nju.logistics.vo.courierIncomeReceipt.ReceiptInfo;
import edu.nju.logistics.vo.financial.BalanceChartVO;
import edu.nju.logistics.vo.financial.PaymentInfo;
/**
 * 总经理经营管理控制器
 * @author 董轶波
 *
 */
public class OperationManagementController implements OperationManagementBLService{
	
	private OperationManagementBL operationManagementBL=null;
	
	public OperationManagementController() throws RemoteException {
		this.operationManagementBL=new OperationManagementBL();
	}

	@Override
	public ArrayList<LogVO> checkSystemLog(String date) throws RemoteException {
		return this.operationManagementBL.checkSystemLog(date);
	}

	@Override
	public ArrayList<UserVO> getCourierList(String id) throws RemoteException {
		return this.operationManagementBL.getCourierList(id);
	}

	@Override
	public double getDistance(String city1,String city2) throws RemoteException{
		return this.operationManagementBL.getDistance(city1, city2);
	}

	@Override
	public double getServicePrice(String name) throws RemoteException {
		return this.operationManagementBL.getServicePrice(name);
	}

	@Override
	public double getSalaryStrategy(String name) throws RemoteException {
		return this.operationManagementBL.getSalaryStrategy(name);
	}

	@Override
	public void makeSalaryStrategy(HashMap<String,Double> map) throws RemoteException {
		this.operationManagementBL.makeSalaryStrategy(map);
		
	}

	@Override
	public HashMap<String,Double> salaryShow() throws RemoteException {
		return this.operationManagementBL.salaryShow();
	}

	@Override
	public void makeDistance(DistanceVO vo) throws RemoteException {
		this.operationManagementBL.makeDistance(vo);
		
	}

	@Override
	public ArrayList<DistanceVO> distanceShow() throws RemoteException {
		return this.operationManagementBL.distanceShow();
	}

	@Override
	public void makePrice(PriceVO vo) throws RemoteException {
		this.operationManagementBL.makePrice(vo);
		
	}

	@Override
	public PriceVO priceShow() throws RemoteException {
		return this.operationManagementBL.priceShow();
	}

	@Override
	public void setApproval(SheetVO vo) throws RemoteException  {
		this.operationManagementBL.setApproval(vo);
	}

	@Override
	public void setMassApproval(String type) throws RemoteException {
		this.operationManagementBL.setMassApproval(type);
	}

	@Override
	public ArrayList<SheetVO> sheetShow(String type) throws RemoteException  {
		return this.operationManagementBL.sheetShow(type);
	}

	@Override
	public ArrayList<InstitutionVO> institutionShow() throws RemoteException {
		return this.operationManagementBL.institutionShow();
	}

	@Override
	public ArrayList<UserVO> employeeShow(InstitutionVO vo) throws RemoteException {
		return this.operationManagementBL.employeeShow(vo);
		
	}

	@Override
	public boolean addInstitution(InstitutionVO vo) throws RemoteException {
		return this.operationManagementBL.addInstitution(vo);
	}

	@Override
	public boolean deleteInstitution(String id) throws RemoteException {
		return this.operationManagementBL.deleteInstitution(id);
	}

	@Override
	public InstitutionVO checkInstitution(String id) throws RemoteException {
		return this.operationManagementBL.checkInstitution(id);
	}

	@Override
	public boolean updateInstitution(String oldID,InstitutionVO vo) throws RemoteException {
		return this.operationManagementBL.updateInstitution(oldID,vo);
	}

	@Override
	public boolean addEmployee(UserVO vo) throws RemoteException {
		return this.operationManagementBL.addEmployee(vo);
	}

	@Override
	public boolean deleteEmployee(String id) throws RemoteException {
		return this.operationManagementBL.deleteEmployee(id);
	}
	
	@Override
	public UserVO checkEmployeeByID(String ID) throws RemoteException {
		return this.operationManagementBL.checkEmployeeByID(ID);
	}

	@Override
	public ArrayList<UserVO> checkEmployeeByRole(String role, String InstitutionID) throws RemoteException {
		return this.operationManagementBL.checkEmployeeByRole(role, InstitutionID);
	}

	@Override
	public boolean updateEmployee(String oldID,UserVO vo) throws RemoteException {
		return this.operationManagementBL.updateEmployee(oldID,vo);
	}

	@Override
	public BalanceChartVO getBalanceChar() throws RemoteException {
		return this.operationManagementBL.getBalanceChar();
	}

	@Override
	public ArrayList<PaymentInfo> showPaymentList(String startTime, String endTime)throws RemoteException {
		return this.operationManagementBL.showPaymentList(startTime, endTime);
	}
	
	@Override
	public ArrayList<ReceiptInfo> showReceiptList(String startTime, String endTime)throws RemoteException{
		return this.operationManagementBL.showReceiptList(startTime, endTime);
	}
	@Override
	public ArrayList<UserPO> getAllUser() throws RemoteException {
		return this.operationManagementBL.getAllUser();
	}

	@Override
	public ArrayList<String> getAllCities() throws RemoteException {
		return this.operationManagementBL.getAllCities();
	}

	@Override
	public ArrayList<String> getCentersID(String city) throws RemoteException {
		return this.operationManagementBL.getCentersID(city);
	}

	@Override
	public String getCenterName(String ID) throws RemoteException {
		return this.operationManagementBL.getCenterName(ID);
	}

	@Override
	public String getBranchName(String ID) throws RemoteException {
		return this.operationManagementBL.getBranchName(ID);
	}

	@Override
	public ArrayList<String> getBranchesID(String ID) throws RemoteException {
		return this.operationManagementBL.getBranchesID(ID);
	}
	@Override
	public ArrayList<String> getListToShow(String centerID) throws RemoteException{
		return this.operationManagementBL.getListToShow(centerID);
	}
	@Override
	public String getUserInstitutionID(String ID) throws RemoteException{
		return this.operationManagementBL.getUserInstitutionID(ID);
	}

	@Override
	public String getUserInstitutionCity(String ID) throws RemoteException {
		return this.operationManagementBL.getUserInstitutionCity(ID);
	}

	@Override
	public ArrayList<InstitutionPO> getAllInstitution() throws RemoteException {
		return this.operationManagementBL.getAllInstitution();
	}

	@Override
	public void setLastPaymentMonth(String userID, String month) throws RemoteException {
		this.operationManagementBL.setLastPaymentMonth(userID, month);
	}

	@Override
	public void setLastPaymentYear(String insID, String year) throws RemoteException {
		this.operationManagementBL.setLastPaymentYear(insID, year);
	}

	@Override
	public String getInstitutionID(String name) throws RemoteException {
		return this.operationManagementBL.getInstitutionID(name);
	}

	@Override
	public void setNoApproval(String id) throws RemoteException {
		this.operationManagementBL.setNoApproval(id);
		
	}
}
