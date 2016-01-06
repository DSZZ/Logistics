package edu.nju.logistics.bl.service.checkorder;

import edu.nju.logistics.vo.checkOrder.CheckOrderVO;

public interface CheckOrderService {

	/**
	 * @param id 订单编号
	 * @return 返回订单信息的VO，里面包含了订单的各类信息，也包括订单状态和历史轨迹
	 */
	public CheckOrderVO CheckOrder(String id);
	
}
