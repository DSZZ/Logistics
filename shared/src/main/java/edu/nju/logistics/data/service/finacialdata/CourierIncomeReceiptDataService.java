package edu.nju.logistics.data.service.finacialdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;



public interface CourierIncomeReceiptDataService extends Remote{
       public void insert(CourierIncomeReceiptPO courierIncomeReceiptpo)
    		   throws RemoteException;
       
       public  ArrayList<CourierIncomeReceiptPO>  getAll() throws RemoteException;
}
