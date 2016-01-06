package edu.nju.logistics.bl.impl.financialmanagement;
import java.util.ArrayList;

import edu.nju.logistics.po.InstitutionPO;
import edu.nju.logistics.po.financial.FreightRecordPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.InstiInfoVO;

/**
 * 计算系统成本的辅助类
 * @author 侍硕
 *
 */
public class InstiLineItem {
	private String CurrentYear;
	public InstiLineItem() {
		// TODO Auto-generated constructor stub
		CurrentYear=TimeUtil.getCurrentDate().substring(0, 4);
		System.out.println(CurrentYear);
		
	}
	
	public InstiInfoVO  toInstiInfo(InstitutionPO  instiPO, 
			ArrayList<FreightRecordPO> freightPOList,String startDate ,String endDate){
		    String InstiID="";
		    double rent=0;
	        double totalFreight=0;
	        
	        InstiID=instiPO.getId();
	        rent = getRent(instiPO,startDate,endDate);
	        totalFreight=computeFreight(InstiID, freightPOList);
	        
	        System.out.println("create a instiInfoVO :"+"ID: "+InstiID+"  rent: "+rent+"  freight:  "+totalFreight);
            return  new InstiInfoVO(InstiID, rent, totalFreight);
	}
	
	public double getRent(InstitutionPO  instiPO,String startDate ,String endDate){
		//计算本月租金
		   if(startDate.substring(0,7).equals(endDate.substring(0,7))){
			      return computeRent(instiPO);
		   }else{
			   //计算时间段内租金
			   return  computeRent(instiPO, startDate, endDate);
		   }
	}
	
    public double computeRent(InstitutionPO  instiPO){
    	double rent =0 ;
    	if(NeedToPay(instiPO)){
        	rent = instiPO.getRental();
        }
    	return rent;
	}
    
	public double computeRent(InstitutionPO  instiPO,String startDate ,String endDate){
		double rent=0;
		int firstYear = Integer.parseInt(startDate.substring(0,4));
		int lastYear = Integer.parseInt(endDate.substring(0,4));
		int scale=lastYear-firstYear+1;
		rent = instiPO.getRental()*scale;
		return rent;
	}
	
	public double computeFreight(String InstiID,ArrayList<FreightRecordPO> freightPOList){
		double totalFreight =0;
		for(int i=0;i<freightPOList.size();i++){
     	   if(freightPOList.get(i).getInstitutionID().equals(InstiID)){
     		  totalFreight+=freightPOList.get(i).getExpense();
     	   }
        }
		return totalFreight;
	}
	
	public boolean  NeedToPay(InstitutionPO po){
		 if(po.getYear().equals(CurrentYear)){
			   return false;
		 }else {
			 return true;
		 }
	}
}
