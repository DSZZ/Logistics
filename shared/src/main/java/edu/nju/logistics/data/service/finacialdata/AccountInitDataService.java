package edu.nju.logistics.data.service.finacialdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import edu.nju.logistics.po.financial.AccountPO;

public interface AccountInitDataService extends Remote{
	/**
	 * 向服务器传递一个AccountPO
	 * @param payment
	 */
     public void insert(AccountPO account)throws RemoteException;
     /**
      * 向服务器写入许多AccountPO
      * @param list
      */
     public void writeAll(ArrayList<AccountPO> list)throws RemoteException;
     /**
      * 从服务器读取全部的AccountPO 
      * @return
      */
     public  ArrayList<AccountPO> getAll()throws RemoteException;
     
    
     
}
