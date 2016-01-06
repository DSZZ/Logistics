package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.bl.service.finacialmanagement.BalanceChartListService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.CourierIncomeReceiptDataService;
import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.BalanceChartVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class BalanceChartList implements BalanceChartListService{
	  //系统成本的VO对象
       private SystemCostVO  costVO;
       //收款单PO 的列表
       private ArrayList<CourierIncomeReceiptPO> receiptPOList;
       //计算系统成本的逻辑对象
       private CostCalculatorBL costbl ;
       //收款单的DataService
       private CourierIncomeReceiptDataService receiptDS;
       
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
       
       public BalanceChartList () throws RemoteException{
               
             	 receiptPOList = new ArrayList<>();
             	 
            	 costbl = new CostCalculatorBL();
     	     try{
     		        this.receiptDS = (CourierIncomeReceiptDataService)
     		        		Naming.lookup("rmi://"+RMI.getIP() + ":2014/"+"CourierIncomeReceiptDSImpl");
     	            System.out.println("BalanceChartList--ReceiptDataService awake!");
     	     } catch (Exception e) {
     		        System.out.println("BalanceChartList--ReceiptDataService failed");
   			    e.printStackTrace();
   	    }
     	     //创建BalanceChartList就会从服务器读取收款单
     	     findReceiptPOList();
     	     //也会计算系统成本
     	     computeSystemCost() ;
       }
       
  	
	@Override
	public  void  findReceiptPOList() throws RemoteException {
		// TODO Auto-generated method stub
	
			
		    	receiptPOList =receiptDS.getAll();
			   System.out.println("The receiptID on server is :");
			   for(int i=0;i<receiptPOList.size();i++){
			          System.out.println(receiptPOList.get(i).getReceiptID());
			   }

		    
	}
 
	
	/**
	 * 这里默认计算的是系统建立最初日期到当前程序运行日期，这段时间内的系统成本
	 * 最初的日期设为一个过去的日期
	 */
	@Override
	public void computeSystemCost() throws RemoteException {
		// TODO Auto-generated method stub
		  this.costVO = costbl.getSystemCostVO("2014/01/01", TimeUtil.getCurrentDate());
	}

	@Override
	public void computeCost() {
		// TODO Auto-generated method stub
		this.totalExpense = this.costVO.getTotalCost();
		this.totalRent=this.costVO.getTotalRent();
		this.totalFreight=this.costVO.getTotalFreight();
		this.totalSalary = this.costVO.getTotalSalary();
	}

	@Override
	public void computeIncome() {
		// TODO Auto-generated method stub
		this.generalIncome=0;
		for(int i=0;i<this.receiptPOList.size();i++){
			this.generalIncome+=this.receiptPOList.get(i).getTotalFee();
		}
	}

	@Override
	public void computeBalnce() {
		// TODO Auto-generated method stub
		this.grossProfit = this.generalIncome-this.totalExpense;
	}

	@Override
	public BalanceChartVO createNewPayment() {
		// TODO Auto-generated method stub
		this.computeCost();
		this.computeIncome();
		this.computeBalnce();
		BalanceChartVO vo = new BalanceChartVO(generalIncome, totalExpense,grossProfit
				,totalRent,totalFreight,totalSalary);
		return vo;
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
