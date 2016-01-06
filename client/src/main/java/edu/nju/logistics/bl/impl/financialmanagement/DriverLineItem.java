package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.operationmanagement.EnvironmentGetter;
import edu.nju.logistics.po.branchdata.DriverPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.UserInfoVO;

/**
 * 计算系统成本的辅助类
 * @author 侍硕
 *
 */
public class DriverLineItem {
	    private String CurrentMonth;
	    private EnvironmentGetter Getter ;
        public DriverLineItem(EnvironmentGetter getter){
        	  CurrentMonth=TimeUtil.getCurrentDate().substring(0, 4);
        	  this.Getter=getter;
        }
        public UserInfoVO toUserInfo (DriverPO driverpo,String startDate , String endDate) throws RemoteException{
        	String UserID="";
        	String InstiID="";
        	String name="";
        	String role = "";
        	double salary=0;
        	double reward=0;
        	UserID= driverpo.getId();
        	InstiID = driverpo.getInstitutionid();
        	name=driverpo.getName();
        	role="司机";
        	salary=getSalary(driverpo, startDate, endDate);
        	reward=computeReward(driverpo);
        	return new UserInfoVO(UserID,InstiID,name, role, salary, reward);
        }
        
        
        public double getSalary(DriverPO driverpo,String startDate , String endDate) throws RemoteException{
        	  //计算本月薪水
 		   if(startDate.substring(0,7).equals(endDate.substring(0,7))){
 			      return computeSalary(driverpo);
 		   }else{
 			   //计算时间段内薪水 
 			   return  computeSalary(driverpo, startDate, endDate);
 		   }
        }
        
        public double computeSalary(DriverPO driverpo) throws RemoteException{
        	
    		//得到该角色的默认月薪
    		double salarySt=Getter.getSalaryStrategy("司机");
    		System.out.println("司机的标准月薪："+salarySt);
    		double salaryTopay=0;
    		  if(NeedToPay(driverpo)){
    			    salaryTopay=salarySt;
    		  }
    		  return salaryTopay;
        }
        
        
       public double computeSalary(DriverPO driverpo,String startDate , String endDate) throws RemoteException{
    	 //得到该角色的默认月薪
    	    double salarySt=Getter.getSalaryStrategy("司机");
    	    System.out.println("司机的标准月薪："+salarySt);
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
        
        public double computeReward(DriverPO driverpo) throws RemoteException{
        	double rewardSt=Getter.getSalaryStrategy("司机"+"提成");
			System.out.println("司机单次提成："+rewardSt);
			double rewardToPay = rewardSt*driverpo.getCount();
			return rewardToPay;
        }
        
        public boolean NeedToPay(DriverPO po){
        	   if (po.getMonth().equals(CurrentMonth)){
        		   return false;
        	   }else{
        		   return true;
        	   }
        }
}
