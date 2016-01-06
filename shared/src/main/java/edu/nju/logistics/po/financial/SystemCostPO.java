package edu.nju.logistics.po.financial;

import java.io.Serializable;
import java.util.ArrayList;

import edu.nju.logistics.vo.financial.InstiInfoVO;
import edu.nju.logistics.vo.financial.UserInfoVO;
/**
 * 财务人员使用的系统成本记录
 * 包括所有机构的租金、运费，所有工作人员的工资和奖金
 * @author 侍硕
 *
 */
public class SystemCostPO  implements Serializable {
			
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		/**
		 * 所有营业厅、中转中心的租金总计，每年付一次
		 */
		private double TotalRent;
		
		/**
		 * 所有营业厅中转运费总计
		 */
		private double  TotalFreight;
		
		/**
		 * 所有员工的月薪总计，每月付一次
		 */
		private double TotalSalary;
		
		/**
		 * 所有员工的奖金总计，每月付一次
		 */
		private double TotalReward;
		
		/**
		 * 一段时间内系统的总成本
		 */
		private double TotalCost;
		/**
		 * 租金年份(单月付款)
		 */
		private String year;
		
		/**
		 * 工资月份(单月付款)
		 */
		private String month;
		
		/**
		 * 跨度成本计算
		 */
		private String startDate;
		/**
		 * 跨度成本计算
		 */
		private String endDate;
		/**
		 * 用于显示的员工工资、奖金信息列表
		 */
	     private ArrayList<UserInfoPO>  userInfoList;
	      /**
	       * 用于显示的机构运费、租金信息列表
	       */
	     private ArrayList<InstiInfoPO> InstiInfoList;
	      
	     public   SystemCostPO(ArrayList<UserInfoPO>  userInfoList,ArrayList<InstiInfoPO> InstiInfoList,double [] totalFee,String []date  ){
	             userInfoList=new ArrayList<UserInfoPO>();
	        	 InstiInfoList= new ArrayList<InstiInfoPO>();
	        	 this.setUserInfoList(userInfoList);
	        	 this.setInstiInfoList(InstiInfoList);
	        	 this.setTotalFreight(totalFee[0]);
	        	 this.setTotalRent(totalFee[1]);
	        	 this.setTotalSalary(totalFee[2]);
	        	 this.setTotalReward(totalFee[3]);
	        	 this.setTotalCost(totalFee[4]);
	        	 this.setDate(date);
	        }
	     
	        public void setDate(String [] date){
	        	  /*由于在costList中创建SystemCostVO时传入的是开始日期和结束日期
	        	      而SystemCostVO显示的是成本计算的月份和年份*/
	        	if(date[0].substring(0, 7).equals(date[1].substring(0, 7))){
	        		//同一个月
	        		this.year=date[0].substring(0, 4);
	        		this.month=date[0].substring(5, 7);
	        	}else{
	        		this.year="";
	        		this.month="";
	        	}
	     		this.startDate=date[0];
        		this.endDate =date[1];
	        }
	     
	
			
			public double getTotalRent() {
				return TotalRent;
			}
			public void setTotalRent(double totalFee) {
				TotalRent = totalFee;
			}
			
			public double getTotalFreight() {
				return TotalFreight;
			}
			
			public void setTotalFreight(double totalFee) {
				TotalFreight = totalFee;
			}
			
			public double  getTotalSalary() {
				return TotalSalary;
			}
			
			public void setTotalSalary(double totalFee) {
				TotalSalary = totalFee;
			}
			
			public double  getTotalReward() {
				return TotalReward;
			}
			
			public void setTotalReward(double totalFee) {
				TotalReward = totalFee;
			}
			
			public String getYear() {
				return year;
			}
			
			public void setYear(String year) {
				this.year = year;
			}
			
			public String getMonth() {
				return month;
			}
			
			public void setMonth(String month) {
				this.month = month;
			}

			public double getTotalCost() {
				return TotalCost;
			}

			public void setTotalCost(double totalFee) {
				TotalCost = totalFee;
			}

			public String getStartDate() {
				return startDate;
			}

			public void setStartDate(String startDate) {
				this.startDate = startDate;
			}

			public String getEndDate() {
				return endDate;
			}

			public void setEndDate(String endDate) {
				this.endDate = endDate;
			}

			public ArrayList<UserInfoPO> getUserInfoList() {
				return userInfoList;
			}

			public void setUserInfoList(ArrayList<UserInfoPO> userInfoList) {
				this.userInfoList = userInfoList;
			}

			public ArrayList<InstiInfoPO> getInstiInfoList() {
				return InstiInfoList;
			}

			public void setInstiInfoList(ArrayList<InstiInfoPO> instiInfoList) {
				InstiInfoList = instiInfoList;
			}

}
