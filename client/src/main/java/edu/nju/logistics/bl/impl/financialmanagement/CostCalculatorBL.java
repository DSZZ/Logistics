package edu.nju.logistics.bl.impl.financialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.CostCalculatorBLService;
import edu.nju.logistics.po.financial.SystemCostPO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public class  CostCalculatorBL  implements  CostCalculatorBLService {
    private CostList costlist;
    public CostCalculatorBL() throws RemoteException{
    	costlist =  new CostList();
    }
	@Override
	public SystemCostVO getSystemCostVO(String startDate , String endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return costlist.createCostList(startDate, endDate);
	}
	@Override
	public SystemCostPO SystemCostVOToPO(SystemCostVO vo) {
		// TODO Auto-generated method stub
	     
		return costlist.SystemCostVOtoPO(vo);
	}
	@Override
	public SystemCostVO SystemCostPOtoVO(SystemCostPO po) {
		// TODO Auto-generated method stub
		return costlist.SystemCostPOtoVO(po);
	}
    
}
