package edu.nju.logistics.vo.financial;

/**
 * 
 * @author 侍硕  
 * 成本收益表
 *
 */
public class BalanceChartVO{
	/**
	 * 总收入，即快递员的总订单费
	 */
	   private double generalIncome;
	/**
	 * 总支出，即各营业厅和中转中心的租金，各机构的人员工资和所有运费
	 */
	   private double  totalExpense;
	/**
	 * 总利润=总收入-总支出
	 */
	   private double  grossProfit;
	   /**
	    * 总租金
	    */
	   private double  totalRent;
	   /**
	    * 总运费
	    */
	   private double totalFreight;
	   /**
	    * 总工资
	    */
	   private double totalSalary;
	   
	   public BalanceChartVO(double  generalIncome,double totalExpense,double grossProfit
			  , double totalRent, double totalFreight, double totalSalary){
		   this.setGeneralIncome(generalIncome);
		   this.setTotalExpense(totalExpense);
		   this.setGrossProfit(grossProfit);
		   this.setTotalRent(totalRent);
		   this.setTotalFreight(totalFreight);
		   this.setTotalSalary(totalSalary);
	   }

	public double  getGeneralIncome() {
		return generalIncome;
	}

	public void setGeneralIncome(double  generalIncome) {
		this.generalIncome = generalIncome;
	}

	public double  getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(double  totalExpense) {
		this.totalExpense = totalExpense;
	}

	public double  getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(double  grossProfit) {
		this.grossProfit = grossProfit;
	}

	public double getTotalRent() {
		return totalRent;
	}

	public void setTotalRent(double totalRent) {
		this.totalRent = totalRent;
	}

	public double getTotalFreight() {
		return totalFreight;
	}

	public void setTotalFreight(double totalFreight) {
		this.totalFreight = totalFreight;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
}
