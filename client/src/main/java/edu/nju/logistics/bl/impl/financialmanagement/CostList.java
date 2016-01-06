package edu.nju.logistics.bl.impl.financialmanagement;


import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.impl.branchmanagement.DriverControllerImpl;
import edu.nju.logistics.bl.impl.operationmanagement.OperationManagementController;
import edu.nju.logistics.bl.service.branchmanagement.DriverController;
import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.po.financial.FreightRecordPO;
import edu.nju.logistics.po.financial.InstiInfoPO;
import edu.nju.logistics.po.financial.SystemCostPO;
import edu.nju.logistics.po.financial.UserInfoPO;
import edu.nju.logistics.vo.financial.InstiInfoVO;
import edu.nju.logistics.vo.financial.SystemCostVO;
import edu.nju.logistics.vo.financial.UserInfoVO;

/**
 * CostCalculator辅助类
 * @author 侍硕
 *
 */
public class CostList {
	/**
	 * 获取机构和人员的PO
	 */
	private EnvironmentGetter getter;
	/**
	 * 获取司机的PO
	 */
	private DriverController driverGetter;
	/**
	 * 获取运费的PO
	 */
	private FreightRecordBL freightGetter;
	
	/**
	 * 机构逻辑处理的辅助类
	 */
	private InstiLineItem InstiHandler;
	/**
	 * 人员逻辑处理的辅助类
	 */
	private UserLineItem UserHandler;
	/**
	 * 司机逻辑处理的辅助类
	 */
	private DriverLineItem DriverHandler;

   /**
    * 0-4依次记录了总运费、总租金、总工资、总奖金、总成本
    */
   double totalFee[]={0,0,0,0,0};
   /**
    * 0-1依次记录了本次系统成本的起止时间
    */
	String date[] = new String [2];
	/*
	 * 机构的持久化对象的列表
	 */
	ArrayList<InstitutionPO> InstitutionPOList ;
	/*
	 * 筛选后的运费的持久化对象的列表
	 */
	ArrayList<FreightRecordPO> FreightPOList;
	/**
	 * 筛选后的员工的持久化对象的列表
	 * 只给快递员、营业厅业务员、中转中心业务员、中转中心库存管理员发薪水
	 */
	ArrayList<UserPO> UserPOList;
	/**
	 * 司机持久化对象
	 */
	ArrayList<DriverPO> DriverPOList;
	/**
	 * 经计算后用于显示的机构信息对象列表
	 */
	ArrayList<InstiInfoVO> InstiInfoVOList;
	/**
	 * 经计算后用于显示的员工(包括司机)信息对象列表
	 */
	ArrayList<UserInfoVO> UserInfoVOList;
	
	public CostList() throws RemoteException{
			getter= new OperationManagementController();
	    driverGetter = new DriverControllerImpl();
		freightGetter = new FreightRecordBL();
		
		InstiHandler = new InstiLineItem();
		UserHandler =  new UserLineItem(getter);
		DriverHandler = new DriverLineItem(getter);
	}
	
	public void  findInstiPOList () throws RemoteException{
		InstitutionPOList = new ArrayList<InstitutionPO>();
		InstitutionPOList=getter.getAllInstitution();   
	}
	
	//首先得根据时间进行筛选。
	public void  findFreightRecordPOList (String startDate,String endDate){
		 FreightPOList = new ArrayList<>();
		 ArrayList<FreightRecordPO> tempList;
		 tempList=freightGetter.getAllFreightRecordPO();
		 if(tempList==null){
			 System.out.println("Cost List   -  findFreightReordPOList:服务器上没有运费记录");
		 }else{
			 for (int i=0;i<tempList.size();i++){
				 //如果运费记录的日期处于开始日期和结束日期之间
				   if(tempList.get(i).getDate().compareTo(startDate)>=0
						   &&tempList.get(i).getDate().compareTo(endDate)<=0){
					      FreightPOList.add(tempList.get(i));
				   }
			 }
			 tempList=null;
		 }
	
	}
	
	//根据职务进行筛选
	public void  findUserPOList () throws RemoteException{
		UserPOList = new ArrayList<UserPO>();
		ArrayList<UserPO> tempList ;
		tempList=getter.getAllUser();
		if(tempList == null){
			System.out.println("服务器上没有员工持久化对象");
		}else{
		           for(int i=0;i<tempList.size();i++){
			               if(isRightUser(tempList.get(i))){
			    	       UserPOList.add(tempList.get(i));
			              }
		          }
		}
		tempList = null;
	}
	
	public void findDriverPOList() throws RemoteException{
		DriverPOList= new ArrayList<DriverPO>();
		DriverPOList=driverGetter.getAllDriverList();
	}
	
	
	//计算某时间段的机构成本信息，若起止时间跨度为一个月则会计算本月成本，否则会计算时间段内的成本
	public void createInstiInfoList(String startDate ,String endDate){
		
		InstiInfoVOList = new ArrayList<>();
		
		for(int i=0;i<InstitutionPOList.size();i++){
			   InstiInfoVO instiInfoVO  ;
			   instiInfoVO = InstiHandler.toInstiInfo(InstitutionPOList.get(i), FreightPOList,startDate,endDate);
			   InstiInfoVOList.add(instiInfoVO);
			   totalFee[0]+=instiInfoVO.getRent();
			   totalFee[1]+=instiInfoVO.getTotalFreight();
		}
	}
	 

	
    //计算某时间段的员工成本信息,若起止时间跨度为一个月则会计算本月成本，否则会计算时间段内的成本
    public void createUserInfoList(String startDate ,String endDate) throws RemoteException{
    	
    	UserInfoVOList = new ArrayList<>();
 	   
 	   for(int i=0;i<UserPOList.size();i++){
 		   UserInfoVO userInfoVO;
 		   userInfoVO= UserHandler.toUserInfo(UserPOList.get(i),startDate,endDate);
 		   UserInfoVOList.add(userInfoVO);
 		   totalFee[2]+=userInfoVO.getSalary();
 		   totalFee[3]+=userInfoVO.getReward();
 	   }
 	   
 	   //司机部分DS还未完成
// 	  for(int i=0;i<DriverPOList.size();i++){
// 		   UserInfoVO userInfoVO = new UserInfoVO("userid","role",0,0);
//		   userInfoVO= DriverHandler.toUserInfo(DriverPOList.get(i),startDate,endDate);
//		   UserInfoVOList.add(userInfoVO);
//		   totalFee[2]+=userInfoVO.getSalary();
// 		   totalFee[3]+=userInfoVO.getReward();
//	   }
 	   
	}
    
    
	public ArrayList<InstiInfoVO> getInstiInfoList (){
		  return  InstiInfoVOList;
	}
	
	public ArrayList<UserInfoVO> getUserInfoList (){
		  return  UserInfoVOList;
	}
	

	/**
	 * 
	 * @param startDate 成本计算的开始日期 e.g: 2015/10/03
	 * @param endDate  成本计算的结束日期
	 * @return
	 * @throws RemoteException 
	 */
	public SystemCostVO createCostList(String startDate ,String endDate) throws RemoteException{
		 this.date[0]=startDate;
		 this.date[1]=endDate;
		  this.findInstiPOList();
		  this.findUserPOList();
		  this.findDriverPOList();
		  this.findFreightRecordPOList(startDate, endDate);
		  this.createInstiInfoList(startDate, endDate);
		  this.createUserInfoList(startDate, endDate);
		  this.totalFee[4]=computeSum();
		  System.out.println("create syscost  complete  get UserInfoVOList size: "+UserInfoVOList.size()+" get InstiInfoList size: "+InstiInfoVOList.size());
		  SystemCostVO  systemCost= new SystemCostVO(UserInfoVOList, InstiInfoVOList,totalFee,date);		 
		  System.out.println("create a new systemcostvo :"+"instiInfoList.size: "+systemCost.getInstiInfoList().size()+
				  " userinfoList.size "+systemCost.getUserInfoList().size());
		  return  systemCost;
	}
	
	private double  computeSum(){
		   int sum=0;
		   for(int i=0;i<=4;i++){
			   sum+=totalFee[i];
		   }
		   return sum;
	}
	
	private boolean isRightUser(UserPO  userpo){
		  if (userpo.getRole().equals("快递员")||userpo.getRole().equals("营业厅业务员")
				  ||userpo.getRole().equals("中转中心业务员")||userpo.getRole().equals("中转中心库存管理员")	 ){
			   return true;
		  }
		  return false;
	}
	
	public SystemCostPO SystemCostVOtoPO(SystemCostVO vo){
		
		ArrayList<UserInfoPO>  userInfoPOList = new ArrayList<>();
		ArrayList<InstiInfoPO>  instiInfoPOList = new ArrayList<>();
		for(int i=0;i<vo.getUserInfoList().size();i++){
			userInfoPOList.add(UserInfoVOtoPO(vo.getUserInfoList().get(i)));
		}
		for(int i=0;i<vo.getInstiInfoList().size();i++){
			instiInfoPOList.add(InstiInfoVOtoPO(vo.getInstiInfoList().get(i)));
		}
		double []  totalFee =  new double[5];
		totalFee[0]=vo.getTotalRent(); totalFee[1]=vo.getTotalFreight();
		totalFee[2]=vo.getTotalSalary(); totalFee[3]=vo.getTotalReward();
		totalFee[4]=vo.getTotalCost();
		String [] date =new String [2];
		date[0]=vo.getStartDate();
		date[1]=vo.getEndDate();
		SystemCostPO po = new SystemCostPO(userInfoPOList, instiInfoPOList, totalFee, date);
		return po;
	}
	
	
	public SystemCostVO SystemCostPOtoVO(SystemCostPO po){
		ArrayList<UserInfoVO>  userInfoVOList = new ArrayList<>();
		ArrayList<InstiInfoVO>  instiInfoVOList = new ArrayList<>();
		for(int i=0;i<po.getUserInfoList().size();i++){
			userInfoVOList.add(UserInfoPOtoVO(po.getUserInfoList().get(i)));
		}
		for(int i=0;i<po.getInstiInfoList().size();i++){
			instiInfoVOList.add(InstiInfoPOtoVO(po.getInstiInfoList().get(i)));
		}
		double []  totalFee =  new double[5];
		totalFee[0]=po.getTotalRent(); totalFee[1]=po.getTotalFreight();
		totalFee[2]=po.getTotalSalary(); totalFee[3]=po.getTotalReward();
		totalFee[4]=po.getTotalCost();
		String [] date =new String [2];
		date[0]=po.getStartDate();
		date[1]=po.getEndDate();
		SystemCostVO vo = new SystemCostVO(userInfoVOList, instiInfoVOList, totalFee, date);
		return vo;
	}
	
	private UserInfoPO UserInfoVOtoPO(UserInfoVO userInfoVo){
		UserInfoPO po = new UserInfoPO(userInfoVo.getUserID(),userInfoVo.getInstiID(), userInfoVo.getName(),userInfoVo.getRole(), userInfoVo.getSalary(), userInfoVo.getReward());
		return po;
	}
	
	private UserInfoVO UserInfoPOtoVO(UserInfoPO userInfoPo){
		UserInfoVO vo = new UserInfoVO(userInfoPo.getUserID(), userInfoPo.getInstiID(),userInfoPo.getName(),userInfoPo.getRole(), userInfoPo.getSalary(), userInfoPo.getReward());
		return vo;
	}
	
	private InstiInfoPO InstiInfoVOtoPO(InstiInfoVO instiInfoVo){
		InstiInfoPO  po = new InstiInfoPO(instiInfoVo.getInstiID()	, instiInfoVo.getRent()	, instiInfoVo.getTotalFreight());
		return po;
	}
	
	private InstiInfoVO InstiInfoPOtoVO(InstiInfoPO instiInfoPo){
		InstiInfoVO vo = new InstiInfoVO(instiInfoPo.getInstiID(),instiInfoPo.getRent(),instiInfoPo.getTotalFreight());
		return vo;
	}
}
