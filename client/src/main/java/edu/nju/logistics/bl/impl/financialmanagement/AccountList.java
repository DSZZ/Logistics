package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.branchmanagement.CarAndDriverGetterImpl;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.impl.storehousemanagement.StorehouseManagementController;
import edu.nju.logistics.bl.service.branchmanagement.CarAndDriverGetter;
import edu.nju.logistics.bl.service.finacialmanagement.AccountListService;
import edu.nju.logistics.bl.service.finacialmanagement.BankAccountGetter;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.bl.service.storehousemanagement.StorehouseManagementBLFinanceService;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.po.branchdata.CarPO;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.po.financial.BankAccountPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.AccountVO;
import edu.nju.logistics.vo.financial.BankAccountVO;
import edu.nju.logistics.vo.financial.CarSM;
import edu.nju.logistics.vo.financial.DriverSM;
import edu.nju.logistics.vo.financial.InstiSM;
import edu.nju.logistics.vo.financial.StorageSM;
import edu.nju.logistics.vo.financial.UserSM;

public class AccountList implements AccountListService{
	/**
	 * 获取机构和人员的PO
	 */
	  EnvironmentGetter envGetter;
	  CarAndDriverGetter  cdGetter;
	  BankAccountGetter baGetter;
	  StorehouseManagementBLFinanceService  shGetter;
	
	 ArrayList<InstitutionPO> instiPOList;
	 ArrayList<UserPO> userPOList;
	 ArrayList<DriverPO> driverPOList;
	 ArrayList<CarPO> carPOList;
	 ArrayList<BankAccountPO> bankaccountPOList;
	
	 ArrayList<InstiSM> instiSMList;
	 ArrayList<UserSM> userSMList;
	 ArrayList<DriverSM> driverSMList;
	 ArrayList<CarSM> carSMList;
	 ArrayList<BankAccountVO> bankaccountVOList;
	 ArrayList<StorageSM> storageSMList;
		
	public AccountList() throws RemoteException{
		this.envGetter =   new OperationManagementController();
		this.cdGetter =  new CarAndDriverGetterImpl() ;
		this.baGetter = new BankAccountGetterImpl() ;
		this.shGetter = new StorehouseManagementController();
		
		instiPOList = new ArrayList<>();userPOList  = new ArrayList<>();
		driverPOList = new ArrayList<>();  carPOList = new ArrayList<>();
		bankaccountPOList = new ArrayList<>();
		
		instiSMList = new ArrayList<>();  userSMList = new ArrayList<>();
		driverSMList = new ArrayList<>(); carSMList  = new ArrayList<>();
		bankaccountVOList = new ArrayList<>();
		storageSMList = new ArrayList<>();
		this.findInstiPOList();
		this.findUserPOList();
		this.findDriverPOList();
		this.findCarPOList();
		this.findBankAccountPOList();
		
		this.ConvertToUserSMList();
		this.ConvertToDriverSMList();
		this.ConvertToCarSMList();
		this.ConvertToBankAccountVOList();
		this.ConvertToInstiSMList();
		this.computeStorage();
	
	}
	
 	@Override
	public void findInstiPOList() throws RemoteException {
		
		 this.instiPOList = envGetter .getAllInstitution();
	}

	@Override
	public void findUserPOList() throws RemoteException {
	
		this.userPOList = envGetter.getAllUser();
	}

	@Override
	public void findDriverPOList() throws RemoteException {
	
		ArrayList<DriverPO> temp  = new ArrayList<>();
		ArrayList<DriverPO>  result= new ArrayList<>();
		for(int i=0;i<this.instiPOList.size();i++){
			String instiID = this.instiPOList.get(i).getId();
			if(instiID.length()==8){
				temp = cdGetter.getInstitutionDriverList(instiID);
				for(int j=0;j<temp.size();j++){
					System.out.print("司机信息： ");
					System.out.println(temp.get(j).getId());
					result.add(temp.get(j));
				}
			}
			
		}
		this.driverPOList=result;
		
	}

	@Override
	public void findCarPOList() throws RemoteException {
	
		ArrayList<CarPO> temp  = new ArrayList<>();
		ArrayList<CarPO>  result= new ArrayList<>();
		for(int i=0;i<this.instiPOList.size();i++){
			String instiID = this.instiPOList.get(i).getId();
			if(instiID.length()==8){
				temp = cdGetter.getInstitutionCarList(instiID);
				for(int j=0;j<temp.size();j++){
					result.add(temp.get(j));
				}
			}
			
		}
		this.carPOList=result;
	}

	@Override
	public void findBankAccountPOList() throws RemoteException {
		
		this.bankaccountPOList = baGetter.getAllBankAccount();

	}

	

	@Override
	public UserSM ToUserShortMessage(UserPO userPO) {
		String instiID;
		
	    if(needInstiID(userPO.getRole())){
	          instiID=userPO.getInstitution().getId();
	    }else{
	    	instiID="";
	    }
	    UserSM   sm = new UserSM(userPO.getId(),instiID, userPO.getName(), userPO.getRole());
	    return sm;
	}
	
	private boolean needInstiID(String role){
		    if(role.equals("营业厅业务员")||role.equals("中转中心业务员")||
		    		role.equals("快递员")||role.equals("中转中心库存管理员")){
		    	return true;
		    }
		    return false;
		    		
	}
	
	@Override
	public DriverSM ToDriverShortMessage(DriverPO driverPO) {
		
		DriverSM sm = new DriverSM(driverPO.getId(), driverPO.getName(), driverPO.getInstitutionid());
		return sm;
	}
    
	//该方法要在将用户和司机的SMList构造好之后调用
	@Override
	public InstiSM ToInstiShortMessage(InstitutionPO instiPO) {
		
		int num=0;
		String instiID = instiPO.getId();
		//遍历用户表
		for(int i=0;  i<this.userSMList.size(); i++){
			if(this.userSMList.get(i).getInsitID().equals(instiID)){
				    num++;
			}
		}
		
		for(int j=0; j<this.driverSMList.size();j++){
			if(this.driverSMList.get(j).getInstitutionID().equals(instiID)){
				num++;
			}
		}
		InstiSM sm = new InstiSM(instiPO.getId(), instiPO.getLocation(), num);
		return sm;
	}
	
	@Override
	public CarSM ToCarShortMessage(CarPO carPO) {
		
		CarSM sm = new CarSM(carPO.getId(), carPO.getPlateNum(),
				carPO.getStartWorkTime());
		return sm;
	}

	@Override
	public BankAccountVO ToBankAccountVO(BankAccountPO baPO) {
		
		BankAccountVO vo  = new BankAccountVO(baPO.getName(), baPO.getBalance());
		return vo;
	}
	
	@Override
	public void computeStorage() throws RemoteException {
		// TODO Auto-generated method stub
		  String id;
          int  shipCapacity;  int shipOccupancy;
          int trailCapacity;   int trailOccupancy;
          int carCapacity;     int carOccupancy;
          int mobileCapacity; int mobileOccupancy;
          for(int i=0;i<this.instiSMList.size();i++){
        	    if(this.instiSMList.get(i).getInstiID().length()==6){
        	    	  id = this.instiSMList.get(i).getInstiID();
        	    	  shipCapacity = shGetter.getScaleNumber(id, "航运区");
        	    	  shipOccupancy = shGetter.getStorageCount(id, "航运区");
        	    	  trailCapacity =  shGetter.getStorageCount(id, "铁运区");
        	    	  trailOccupancy = shGetter.getStorageCount(id, "铁运区");
        	    	  carCapacity =  shGetter.getStorageCount(id, "汽运区");
        	    	  carOccupancy = shGetter.getStorageCount(id, "汽运区");
        	    	  mobileCapacity =  shGetter.getStorageCount(id, "机动区");
        	    	  mobileOccupancy = shGetter.getStorageCount(id, "机动区");
      
        	    	  StorageSM sm = new StorageSM(id, shipCapacity, shipOccupancy, trailCapacity, trailOccupancy, 
        	    			 carCapacity, carOccupancy, mobileCapacity, mobileOccupancy);
        	    	
        	    	  this.storageSMList.add(sm);
        	    }
          }
         
	}
	
	public void ConvertToUserSMList(){
		if(this.userPOList!=null){
			for(int i=0; i<userPOList.size();i++){
				this.userSMList.add(ToUserShortMessage(this.userPOList.get(i)));
			}
		}
	}
	
	public void ConvertToDriverSMList(){
		if(this.driverPOList!=null){
			for(int i=0; i<driverPOList.size();i++){
				this.driverSMList.add(ToDriverShortMessage(this.driverPOList.get(i)));
			}
		}
	}
	
	public void ConvertToInstiSMList(){
		if(this.instiPOList!=null){
			for(int i=0; i<instiPOList.size();i++){
				this.instiSMList.add(ToInstiShortMessage(this.instiPOList.get(i)));
			}
		}
	}
	
	
	public void ConvertToCarSMList(){
		if(this.carPOList!=null){
			for(int i=0; i<carPOList.size();i++){
				this.carSMList.add(ToCarShortMessage(this.carPOList.get(i)));
			}
		}
	}
	
	public void ConvertToBankAccountVOList(){
		if(this.bankaccountPOList!=null){
			for(int i=0; i<bankaccountPOList.size();i++){
				this.bankaccountVOList.add(ToBankAccountVO(this.bankaccountPOList.get(i)));
			}
		}
	}

	
	@Override
	public AccountVO createNewAccount() {
		AccountVO  accountVO  = new AccountVO( TimeUtil.getCurrentDate()
				, this.instiSMList, this.userSMList,this.driverSMList, 
				this.carSMList,this.bankaccountVOList,this.storageSMList);
		return accountVO;
	}



	

}