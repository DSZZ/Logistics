package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.BalanceChartBLService;
import edu.nju.logistics.vo.financial.BalanceChartVO;
/**
 * 查看与导出成本收益表的逻辑类
 * @author 侍硕
 *
 */
public class BalanceChartBL  implements BalanceChartBLService{
	//创建成本收益表的逻辑对象
    private BalanceChartList list;
    
    public BalanceChartBL() throws RemoteException{
    	this.list = new BalanceChartList();
    }
    
	@Override
	public BalanceChartVO showNewChart() {
		// TODO Auto-generated method stub
		return list.createNewPayment();
	}

	@Override
	public void OutputBalanceChart() {
		// TODO Auto-generated method stub
		
	}

}
