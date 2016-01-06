package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.BalanceChartBLService;
import edu.nju.logistics.vo.financial.BalanceChartVO;

public class BalanceChartController implements BalanceChartBLService{
     private BalanceChartBL bl;
     public BalanceChartController (BalanceChartBL bl) throws RemoteException{
    	 this.bl =  bl;
     }
	@Override
	public BalanceChartVO showNewChart() {
		// TODO Auto-generated method stub
		return bl.showNewChart();
	}

	@Override
	public void OutputBalanceChart() {
		// TODO Auto-generated method stub
		bl.OutputBalanceChart();
	}

}
