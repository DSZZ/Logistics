package edu.nju.logistics.data.service.finacialdata;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import edu.nju.logistics.po.financial.BankAccountPO;

public interface BankAccountDataService extends Remote {
	public ArrayList<BankAccountPO> getAll() throws RemoteException;
	public void writeAll(ArrayList<BankAccountPO> list) throws RemoteException;
	public void Update(String name,double cost)throws RemoteException;
	public void insert(BankAccountPO po) throws RemoteException;
}
