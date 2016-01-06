package edu.nju.logistics.bl.service.finacialmanagement;

import edu.nju.logistics.vo.financial.BalanceChartVO;

/**
 * 
 * @author 侍硕
 * 查看与导出成本收益表
 */
public interface BalanceChartBLService {
	/**
	 * 返回成本收益表的VO
	 * @return
	 */
	public BalanceChartVO showNewChart ();
	/**
	 * 导出当前界面显示的成本收益表
	 */
	public void OutputBalanceChart();
	
}
