package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.financial.SystemCostPO;
import edu.nju.logistics.vo.financial.SystemCostVO;

public interface CostCalculatorBLService {

	/**
	 * 根据起止日期获得系统成本清单对象
	 * @return
	 * @throws RemoteException 
	 */
	public SystemCostVO getSystemCostVO(String startDate , String endDate) throws RemoteException;
    /**
     * PO 和 VO 的转化
     * @param vo
     * @return
     */
	public SystemCostPO SystemCostVOToPO(SystemCostVO vo);
	 /**
     * PO 和 VO 的转化
     * @param vo
     * @return
     */
	public SystemCostVO SystemCostPOtoVO(SystemCostPO po);
}
