package edu.nju.logistics.bl.service.finacialmanagement;

import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.financial.BankAccountPO;
/**
 * 期初建账在从服务器读取所有银行账户信息时所依赖的接口
 * @author 侍硕
 *
 */
public interface BankAccountGetter {
	   /*
	    * 返回所有银行账户的持久化对象
	    */
       public ArrayList <BankAccountPO> getAllBankAccount() throws RemoteException;
}
