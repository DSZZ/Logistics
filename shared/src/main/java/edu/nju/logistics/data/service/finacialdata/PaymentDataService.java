package edu.nju.logistics.data.service.finacialdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.financial.PaymentPO;


/**
 * 
 * @author 侍硕
 *
 */
public interface PaymentDataService extends Remote{
	/**
	 * 向服务器传递一个PaymentPO
	 * @param payment
	 */
     public void insert(PaymentPO payment)throws RemoteException;
     /**
      * 向服务器写入许多PaymentPO
      * @param list
      */
     public void writeAll(ArrayList<PaymentPO> list)throws RemoteException;
     /**
      * 从服务器读取全部的付款单PO 
      * @return
      */
     public  ArrayList<PaymentPO> getAll()throws RemoteException;
     
    
     
}
