package edu.nju.logistics.bl.service.checkorder;

import java.rmi.RemoteException;

import edu.nju.logistics.po.orderdata.OrderPO;
/**
 * 寄件人查询订单的逻辑接口
 * @author 侍硕
 *
 */
public interface CheckOrderBLService {
	    /**
	      * 判断客户输入的订单号是否合法（格式正确且存在这样的订单）
	      * @return
	      */
           boolean  isValidInput(String orderID);
           /**
            * 根据订单号返回订单信息
            * @param ID
            * @return
         * @throws RemoteException 
            */
           OrderPO getOrderPO(String ID) throws RemoteException;
}
