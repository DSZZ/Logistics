package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.impl.examinebl.ExamineBL;
import edu.nju.logistics.bl.service.finacialmanagement.PaymentListService;
import edu.nju.logistics.ui.util.TimeUtil;
import edu.nju.logistics.vo.financial.PaymentVO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class PaymentList implements PaymentListService {
      private CostCalculatorBL costbl ;
      private ExamineBL examinebl;
      public PaymentList (CostCalculatorBL costbl,ExamineBL examinebl){
    	  this.costbl=costbl;
    	  this.examinebl =examinebl;
      }
	@Override
	public SystemCostVO computeSystemCost() throws RemoteException {
		// TODO Auto-generated method stub
		
		//默认为得到本月的成本
		String currentDate = TimeUtil .getCurrentDate();
		String startDate = currentDate.substring(0, 8)+"01";
		return  costbl.getSystemCostVO(startDate , currentDate);
	}

	@Override
	public PaymentVO createNewPayment() throws RemoteException {
		// TODO Auto-generated method stub
		SystemCostVO  costVO  = computeSystemCost();
		if(costVO==null){
			System.out.println("payList-create : failed");
		}
		String ID = examinebl.getSheetID();
		System.out.println("PaymentList--examinebl.getSheetID():"+ID);
		PaymentVO  vo =  new PaymentVO(ID, "", "付款单", TimeUtil.getCurrentDate() , "草稿", "", costVO,"");
		return vo;
	}
    
}
