package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;

import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;


/**
 * 这个是营业厅业务员创建了收款单后，
 * 向服务器添加一个CourierIncomeReceiptPO时所依赖的接口
 * @author 侍硕
 *
 */
public interface CourierIncomeReceiptBLService {
    /**
     * 委托BL向服务器插入一个持久化的付款单对象
     * @param courierIncomeReceiptPO
     * @throws RemoteException 
     */
	public void addCourierIncomeReceiptPO(CourierIncomeReceiptPO courierIncomeReceiptPO) throws RemoteException;
    
}
