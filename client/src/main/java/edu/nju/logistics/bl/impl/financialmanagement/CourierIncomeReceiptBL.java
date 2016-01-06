package edu.nju.logistics.bl.impl.financialmanagement;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.nju.logistics.bl.service.finacialmanagement.CourierIncomeReceiptBLService;
import edu.nju.logistics.client.init.RMI;
import edu.nju.logistics.data.service.finacialdata.CourierIncomeReceiptDataService;
import edu.nju.logistics.po.courierIncomeReceipt.CourierIncomeReceiptPO;

public class CourierIncomeReceiptBL implements CourierIncomeReceiptBLService{
	 private CourierIncomeReceiptDataService  dataService;
	 
	 public CourierIncomeReceiptBL() throws MalformedURLException, RemoteException, NotBoundException{

		 this.dataService = (CourierIncomeReceiptDataService) Naming.lookup("rmi://"+RMI.getIP() + ":2014/"+"CourierIncomeReceiptDSImpl");

	 }
	@Override
	public void addCourierIncomeReceiptPO(CourierIncomeReceiptPO courierIncomeReceiptPO) throws RemoteException {
		// TODO Auto-generated method stub

		dataService.insert(courierIncomeReceiptPO);

	}
        
}
