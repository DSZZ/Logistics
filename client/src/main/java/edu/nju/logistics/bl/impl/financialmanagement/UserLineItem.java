package edu.nju.logistics.bl.impl.financialmanagement;


import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.po.UserPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.UserInfoVO;

/**
 * 计算系统成本的辅助类
 * @author 侍硕
 *
 */
public class UserLineItem {
	private String CurrentMonth;
	private EnvironmentGetter Getter ;
	public UserLineItem(EnvironmentGetter getter){
		// TODO Auto-generated constructor stub
		CurrentMonth=TimeUtil.getCurrentDate().substring(5, 7);
        Getter = getter;
		System.out.println(CurrentMonth);
	}

	
	public UserInfoVO toUserInfo (UserPO userpo,String startDate ,String endDate) throws RemoteException{
		String UserID ="";
		String InstiID="";
		String name = "";
		String  role="";
		double salary=0;
		double reward=0;
		
		UserID = userpo.getId();
		InstiID= userpo.getInstitution().getId();
		name = userpo.getName();
		role = userpo.getRole();
		//计算实际应付薪水
		salary=getSalary(userpo,startDate,endDate);
		reward=computeReward(userpo);
		System.out.println("create a userinfoVO : "+"ID: "+UserID+"InsitID: "+InstiID+
				" name: "+name +" role: "+role+" salary: "+salary+" reward : "+reward);
		return new UserInfoVO(UserID,InstiID,name,role, salary, reward);
	}
	
	public double getSalary(UserPO userpo,String startDate ,String endDate) throws RemoteException{
		   //计算本月薪水
		   if(startDate.substring(0,7).equals(endDate.substring(0,7))){
			      return computeSalary(userpo);
		   }else{
			   //计算时间段内薪水 
			   return  computeSalary(userpo, startDate, endDate);
		   }
	}
	
	public double computeSalary(UserPO userpo) throws RemoteException{
		
		double salarySt=Getter.getSalaryStrategy(userpo.getRole());
		System.out.println(userpo.getRole()+"月薪: "+salarySt);
		double salaryTopay=0;
		  if(NeedToPay(userpo)){
			    salaryTopay=salarySt;
		  }
		  return salaryTopay;
	}
	
	public double computeSalary(UserPO userpo,String startDate ,String endDate) throws RemoteException{
		
		double salarySt=Getter.getSalaryStrategy(userpo.getRole());
		System.out.println(userpo.getRole()+"月薪: "+salarySt);
		double salaryToPay=0;
		 int startYear=0;  int startMonth=0; int endYear=0; int endMonth=0;
		 startYear=Integer.parseInt(startDate.substring(0,4));
		 endYear=Integer.parseInt(endDate.substring(0,4));
		 startMonth=Integer.parseInt(startDate.substring(5,7));
		 endMonth=Integer.parseInt(endDate.substring(5,7));
		 int scale=12*(endYear-startYear)-startMonth+endMonth;
		 salaryToPay=salarySt*scale;
		  return salaryToPay;
	}
	
	public double computeReward(UserPO userpo) throws RemoteException{
		
		if(!NeedReward(userpo)){
			  return 0;
		}else{
			double rewardSt=Getter.getSalaryStrategy(userpo.getRole()+"提成");
			System.out.println("快递员单次提成："+rewardSt);
			double rewardToPay = rewardSt*userpo.getCount();
			return rewardToPay;
		}
	}
	
	public boolean NeedReward(UserPO po){
		if(po.getRole().equals("快递员")){
			return true;
		}else{
			return false;
		}
	}
	public boolean  NeedToPay(UserPO po){
		 if(po.getMonth().equals(CurrentMonth)){
			   return false;
		 }else {
			 return true;
		 }
	}
	
}
