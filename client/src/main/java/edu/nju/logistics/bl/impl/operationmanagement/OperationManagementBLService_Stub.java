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
public class OperationManagementBLService_Stub implements OperationManagementBLService{

	@Override
	public ArrayList<LogVO> checkSystemLog(String date) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<UserVO> getCourierList(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserPO> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDistance(String city1, String city2) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public ArrayList<String> getAllCities() {
		// TODO Auto-generated method stub
		return null;
	}



	

	@Override
	public void makeSalaryStrategy(HashMap<String,Double> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String,Double> salaryShow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makeDistance(DistanceVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DistanceVO> distanceShow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void makePrice(PriceVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PriceVO priceShow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApproval(SheetVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMassApproval(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<SheetVO> sheetShow(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<InstitutionVO> institutionShow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserVO> employeeShow(InstitutionVO vo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addInstitution(InstitutionVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteInstitution(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InstitutionVO checkInstitution(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateInstitution(String oldID, InstitutionVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEmployee(UserVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteEmployee(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserVO checkEmployeeByID(String ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<UserVO> checkEmployeeByRole(String role, String InstitutionID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateEmployee(String oldID, UserVO vo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BalanceChartVO getBalanceChar() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getServicePrice(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSalaryStrategy(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<String> getCentersID(String city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCenterName(String ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBranchName(String ID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getBranchesID(String ID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> getListToShow(String centerID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUserInstitutionID(String ID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUserInstitutionCity(String ID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<InstitutionPO> getAllInstitution() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastPaymentMonth(String usrID, String month) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastPaymentYear(String insID, String year) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getInstitutionID(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNoApproval(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<PaymentInfo> showPaymentList(String startTime, String endTime) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ReceiptInfo> showReceiptList(String startTime, String endTime) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}


	
}
